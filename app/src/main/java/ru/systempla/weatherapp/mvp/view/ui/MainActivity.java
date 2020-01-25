package ru.systempla.weatherapp.mvp.view.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import javax.inject.Inject;

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

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter presenter;

    @Inject
    Router router;

    @Inject
    NavigatorHolder navigatorHolder;

    Navigator navigator = new SupportAppNavigator(this, R.id.content);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getAppComponent().inject(this);
        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);

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