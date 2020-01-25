package ru.systempla.weatherapp.mvp.view.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.systempla.weatherapp.R;
import ru.systempla.weatherapp.mvp.App;
import ru.systempla.weatherapp.mvp.presenter.MainPresenter;
import ru.systempla.weatherapp.mvp.view.MainView;
import ru.systempla.weatherapp.navigation.Screens;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

public class MainActivity extends MvpAppCompatActivity implements MainView, NavigationView.OnNavigationItemSelectedListener {

    @InjectPresenter
    MainPresenter presenter;

    @Inject
    Router router;

    @Inject
    NavigatorHolder navigatorHolder;

    @BindView(R.id.main_drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    Navigator navigator = new SupportAppNavigator(this, R.id.content);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getAppComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initSideMenu();

        if(savedInstanceState == null){
            router.replaceScreen(new Screens.WeatherDataScreen());
        }
    }

    @ProvidePresenter
    public MainPresenter providePresenter(){
        MainPresenter presenter = new MainPresenter();
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.startGPSUpdate();
    }

    @Override
    protected void onStop() {
        presenter.stopGPSUpdate();
        super.onStop();
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    public void checkGeolocationPermission() {
        if (!(checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))) {
            getPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }

    public void openNavDrawer(){
        drawer.openDrawer(GravityCompat.START);
    }

    private void initSideMenu() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_weather_data) {
            presenter.navigateToWeatherData();
        } else if (id == R.id.nav_forecast) {
            presenter.navigateToForecast();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean checkPermission(String permission) {
        ActivityCompat.checkSelfPermission(App.getInstance(), permission);
        return (ActivityCompat.checkSelfPermission(App.getInstance(), permission)== PackageManager.PERMISSION_GRANTED);
    }

    public boolean checkPermission(String ... permissions){
        boolean flag = true;
        for (String permission : permissions ) {
            flag &= (ActivityCompat.checkSelfPermission(App.getInstance(), permission)== PackageManager.PERMISSION_GRANTED);
        }
        return flag;
    }

    public void getPermission(String permission) {
        ActivityCompat.requestPermissions(this, new String[]{permission}, 100);
    }

    public void getPermission(String ... permissions){
        ActivityCompat.requestPermissions(this, permissions,100);
    }

}