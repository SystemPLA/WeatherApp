package ru.systempla.weatherapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import ru.systempla.weatherapp.ui.com.SMFragment;
import ru.systempla.weatherapp.ui.dev.DeveloperInfoFragment;
import ru.systempla.weatherapp.ui.main_weather.WeatherInfoFragment;
import ru.systempla.weatherapp.ui.parcel.Parcel;
import ru.systempla.weatherapp.ui.parcel.SettingsParcel;
import ru.systempla.weatherapp.ui.settings.SettingsChangeListener;
import ru.systempla.weatherapp.ui.settings.SettingsFragment;

import static ru.systempla.weatherapp.ui.settings.SettingsFragment.create;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SettingsChangeListener {

    private Toolbar toolbar;
    private EditText searchEditText;
    private DrawerLayout drawer;
    private Fragment fragmentSettings;
    private Fragment developerInfoFragment;
    private Fragment sendMessageFragment;
    private Fragment weatherFragment;
    private Parcel currentParcel;

    public String last_city = "";

    private SettingsParcel settingsParcel = new SettingsParcel(true,true,true);

    @Override
    public void onSettingsChange(SettingsParcel settingsParcel) {
        this.settingsParcel = settingsParcel;
        currentParcel = new Parcel(last_city, settingsParcel);
        weatherFragment = WeatherInfoFragment.create(currentParcel);
        replaceFragment(weatherFragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initSideMenu();
        currentParcel = new Parcel(last_city, settingsParcel);

        fragmentSettings = new SettingsFragment();
        developerInfoFragment = new DeveloperInfoFragment();
        sendMessageFragment = new SMFragment();
        weatherFragment = new WeatherInfoFragment();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchEditText = findViewById(R.id.searchEditText);
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
                if(searchEditText.getVisibility() == View.VISIBLE) {
                    searchEditText.setVisibility(View.GONE);
                    Objects.requireNonNull(getSupportActionBar())
                            .setTitle(getString(R.string.app_name));
                } else {
                    Objects.requireNonNull(getSupportActionBar()).setTitle("");
                    searchEditText.setVisibility(View.VISIBLE);
                    searchEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            last_city = s.toString();
                            currentParcel = new Parcel(last_city, settingsParcel);
                            weatherFragment = WeatherInfoFragment.create(currentParcel);
                            Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_SHORT).show();
                            replaceFragment(weatherFragment);
                        }
                    });
                }
                break;
            }
            default: {
                Toast.makeText(getApplicationContext(), getString(R.string.action_not_found),
                        Toast.LENGTH_SHORT).show();
            }
        }
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, target);
        fragmentTransaction.commit();
    }

}
