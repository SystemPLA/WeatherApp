package ru.systempla.weatherapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;

import ru.systempla.weatherapp.ui.com.SMFragment;
import ru.systempla.weatherapp.ui.dev.DeveloperInfoFragment;
import ru.systempla.weatherapp.ui.main_weather.WeatherInfoFragment;
import ru.systempla.weatherapp.ui.parcel.Parcel;
import ru.systempla.weatherapp.ui.parcel.SettingsParcel;
import ru.systempla.weatherapp.ui.settings.SettingsChangeListener;
import ru.systempla.weatherapp.ui.settings.SettingsFragment;

public class MainActivity_old extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SettingsChangeListener {

    private final static String LOG_TAG_MAIN = "Main activity";
    private final static String MSG_NO_DATA = "No data";
    private final String PARCEL_KEY = "PARCEL";
    private final String externalFileName = "systempla_weatherapp_parcel_file.lect";

    private final String DEFAULT_CITY = "Реутов";

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Fragment fragmentSettings;
    private Fragment developerInfoFragment;
    private Fragment sendMessageFragment;
    private Fragment weatherFragment;
    private TextView temperatureSensorText;
    private TextView humiditySensorText;
    private Sensor sensorTemperature;
    private Sensor sensorHumidity;
    private SensorManager sensorManager;
    private View fragmentContainer;

    private String last_city = DEFAULT_CITY;

    private SettingsParcel settingsParcel = new SettingsParcel(true, true, true);
    private Parcel currentParcel = new Parcel(last_city, settingsParcel);

    private LocationManager mLocManager = null;
    private LocListener mLocListener = null;

//    OnNavigationItemSelectedListener method

    @Override
    public void onSettingsChange(SettingsParcel settingsParcel) {
        this.settingsParcel = settingsParcel;
        currentParcel = new Parcel(last_city, settingsParcel);
        weatherFragment = WeatherInfoFragment.create(currentParcel);
        replaceFragment(weatherFragment);
    }

//    Activity method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);

        initViews();
        initSideMenu();
        getSensors();
        initFragments();
        loadSavedData(savedInstanceState);
    }

    private Location getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
        return mLocManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
    }

    private String getAddressByLoc(Location loc) {
        if (loc == null) return MSG_NO_DATA;
        final Geocoder geo = new Geocoder(this);
        List<Address> list;
        try {
            list = geo.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
        if (list.isEmpty()) {
            return MSG_NO_DATA;
        } else {
            Address address = list.get(0);
            return address.getLocality();
        }
    }

    private void loadSavedData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentParcel = (Parcel) savedInstanceState.getSerializable(PARCEL_KEY);
            last_city = Objects.requireNonNull(currentParcel).getCityName();
            settingsParcel = currentParcel.getSettingsParcel();
            if (fragmentContainer.getVisibility() == View.GONE)
                fragmentContainer.setVisibility(View.VISIBLE);
        } else {
            String gps_location = getAddressByLoc(getLocation());
            if (gps_location.equals(MSG_NO_DATA)) {
                String path = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + externalFileName;
                readFromFile(path);
            } else {
                last_city = gps_location;
                currentParcel = new Parcel(last_city, settingsParcel);
            }
            weatherFragment = WeatherInfoFragment.create(currentParcel);
            replaceFragment(weatherFragment);
        }
    }

    private void initFragments() {
        fragmentSettings = new SettingsFragment();
        developerInfoFragment = new DeveloperInfoFragment();
        sendMessageFragment = new SMFragment();
        weatherFragment = new WeatherInfoFragment();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        temperatureSensorText = findViewById(R.id.textTemperatureSensor);
        humiditySensorText = findViewById(R.id.textHumiditySensor);
        fragmentContainer = findViewById(R.id.fragment_container);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listenerTemperature, sensorTemperature,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listenerHumidity, sensorHumidity,
                SensorManager.SENSOR_DELAY_NORMAL);
        if (mLocListener == null) mLocListener = new LocListener();
        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000L, 1.0F, mLocListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerTemperature, sensorTemperature);
        sensorManager.unregisterListener(listenerHumidity, sensorHumidity);
        if (mLocListener != null) mLocManager.removeUpdates(mLocListener);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String path = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + externalFileName;
        saveToFile(path);
        outState.putSerializable(PARCEL_KEY, currentParcel);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentParcel = (Parcel) savedInstanceState.getSerializable(PARCEL_KEY);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        handleMenuItemClick(item);
        return super.onOptionsItemSelected(item);
    }

    private void handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.change_settings: {
                fragmentSettings = SettingsFragment.create(currentParcel);
                replaceFragment(fragmentSettings);
                break;
            }
            case R.id.change_city: {
                showInputDialog();
                break;
            }
            default: {
                Toast.makeText(getApplicationContext(), getString(R.string.action_not_found),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.change_city);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                last_city = input.getText().toString();
                currentParcel = new Parcel(last_city, settingsParcel);
                weatherFragment = WeatherInfoFragment.create(currentParcel);
                replaceFragment(weatherFragment);
            }
        });
        builder.show();
    }

    private void initSideMenu() {
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {
            currentParcel = new Parcel(last_city, settingsParcel);
            weatherFragment = WeatherInfoFragment.create(currentParcel);
            replaceFragment(weatherFragment);
        } else if (id == R.id.nav_tools) {
            replaceFragment(developerInfoFragment);
        } else if (id == R.id.nav_send) {
            replaceFragment(sendMessageFragment);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment target) {
        if (fragmentContainer.getVisibility()==View.GONE) fragmentContainer.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, target);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

//    Sensors

    private void getSensors() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mLocManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
    }

    private void showTemperatureSensor(SensorEvent event){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Temperature Sensor value = ").append(event.values[0])
                .append("\n").append("=======================================").append("\n");
        temperatureSensorText.setText(stringBuilder);
    }

    private void showHumiditySensor(SensorEvent event){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Humidity Sensor value = ").append(event.values[0])
                .append("\n").append("=======================================").append("\n");
        humiditySensorText.setText(stringBuilder);
    }

    private final class LocListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            Log.d(LOG_TAG_MAIN, "onLocationChanged: " + location.toString());
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { /* Empty */ }
        @Override
        public void onProviderEnabled(String provider) { /* Empty */ }
        @Override
        public void onProviderDisabled(String provider) { /* Empty */ }
    }

    SensorEventListener listenerTemperature = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        @Override
        public void onSensorChanged(SensorEvent event) {
            showTemperatureSensor(event);
        }
    };

    SensorEventListener listenerHumidity = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        @Override
        public void onSensorChanged(SensorEvent event) {
            showHumiditySensor(event);
        }
    };

//    Preference

    private void saveToFile(String fileName) {
        File file;
        try {
            file = new File(fileName);

            FileOutputStream fileOutputStream;
            ObjectOutputStream objectOutputStream;

            if(!file.exists()) {
                file.createNewFile();
            }

            fileOutputStream  = new FileOutputStream(file, false);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(currentParcel);

            fileOutputStream.close();
            objectOutputStream.close();
            Log.d("Activity_File", "Save to file is finished. Last city is " + currentParcel.getCityName());
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void readFromFile(String fileName) {
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;

        try {
            fileInputStream = new FileInputStream(fileName);
            objectInputStream = new ObjectInputStream(fileInputStream);

            currentParcel = (Parcel)objectInputStream.readObject();
            last_city = currentParcel.getCityName();
            settingsParcel = currentParcel.getSettingsParcel();

            fileInputStream.close();
            objectInputStream.close();
            Log.d("Activity_File", "Read from file is finished. Last city is " + last_city);
        } catch(Exception exc) {
            exc.printStackTrace();
        }
    }

}

