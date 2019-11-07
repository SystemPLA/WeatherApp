package ru.systempla.weatherapp;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ru.systempla.weatherapp.service.BoundService;
import ru.systempla.weatherapp.ui.com.SMFragment;
import ru.systempla.weatherapp.ui.dev.DeveloperInfoFragment;
import ru.systempla.weatherapp.ui.main_weather.WeatherInfoFragment;
import ru.systempla.weatherapp.ui.parcel.Parcel;
import ru.systempla.weatherapp.ui.parcel.SettingsParcel;
import ru.systempla.weatherapp.ui.settings.SettingsChangeListener;
import ru.systempla.weatherapp.ui.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SettingsChangeListener {

    private final String PARCEL_KEY = "PARCEL";
    private final String externalFileName = "systempla_weatherapp_parcel_file.lect";

    private boolean isBind = false;
    private BoundService.ServiceBinder mService = null;
    private MyServiceConnection mConnection = null;

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

    private SettingsParcel settingsParcel = new SettingsParcel(true,true,true);
    private Parcel currentParcel = new Parcel(last_city, settingsParcel, mService);

//    OnNavigationItemSelectedListener method

    @Override
    public void onSettingsChange(SettingsParcel settingsParcel) {
        this.settingsParcel = settingsParcel;
        currentParcel = new Parcel(last_city, settingsParcel, mService);
        weatherFragment = WeatherInfoFragment.create(currentParcel);
        replaceFragment(weatherFragment);
    }

//    Activity method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initSideMenu();
        getSensors();

        fragmentSettings = new SettingsFragment();
        developerInfoFragment = new DeveloperInfoFragment();
        sendMessageFragment = new SMFragment();
        weatherFragment = new WeatherInfoFragment();

        mConnection = new MyServiceConnection();

        if(savedInstanceState != null) {
            currentParcel = (Parcel) savedInstanceState.getSerializable(PARCEL_KEY);
            last_city = currentParcel.getCityName();
            settingsParcel = currentParcel.getSettingsParcel();
            if (fragmentContainer.getVisibility()==View.GONE) fragmentContainer.setVisibility(View.VISIBLE);
        } else {
            String path = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + externalFileName;
            readFromFile(path);
            weatherFragment = WeatherInfoFragment.create(currentParcel);
            replaceFragment(weatherFragment);
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        temperatureSensorText = findViewById(R.id.textTemperatureSensor);
        humiditySensorText = findViewById(R.id.textHumiditySensor);
        fragmentContainer = findViewById(R.id.fragment_container);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isBind) {
            Intent intent = new Intent(getApplicationContext(), BoundService.class);
            bindService(intent, mConnection, BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        if (isBind) {
            this.unbindService(mConnection);
            isBind = false;
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Регистрируем слушатель датчика освещенности
        sensorManager.registerListener(listenerTemperature, sensorTemperature,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listenerHumidity, sensorHumidity,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerTemperature, sensorTemperature);
        sensorManager.unregisterListener(listenerHumidity, sensorHumidity);
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
        if(savedInstanceState != null) {
            currentParcel = (Parcel) savedInstanceState.getSerializable(PARCEL_KEY);
        }
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
                currentParcel = new Parcel(last_city, settingsParcel, mService);
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
            currentParcel = new Parcel(last_city, settingsParcel, mService);
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
        // Менеджер датчиков
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Датчик освещенности (он есть на многих моделях)
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

    // Слушатель датчика освещенности
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

//    Service

    private class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            /* Get service object (binder) */
            mService = (BoundService.ServiceBinder) service;
            isBind = mService != null;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
            mService = null;
        }

    }

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

