package ru.systempla.weatherapp.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.systempla.weatherapp.mvp.App;
import ru.systempla.weatherapp.mvp.model.final_groups.FinalGroups;
import ru.systempla.weatherapp.mvp.model.location.ILocationGetter;
import ru.systempla.weatherapp.mvp.model.repo.IWeatherRepo;
import ru.systempla.weatherapp.mvp.model.settings.ISettings;
import ru.systempla.weatherapp.mvp.view.MainView;
import ru.systempla.weatherapp.navigation.Screens;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    ILocationGetter locationGetter;

    @Inject
    Router router;

    @SuppressLint("CheckResult")
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().checkGeolocationPermission();
    }

    public void stopGPSUpdate(){
        locationGetter.stopUpdatingLocation();
    }

    public void startGPSUpdate(){
        locationGetter.startUpdatingLocation();
    }

    public void navigateToWeatherData() {
        router.navigateTo(new Screens.WeatherDataScreen());
    }


    public void navigateToForecast() {
        router.navigateTo(new Screens.WeekForcastScreen());
    }
}
