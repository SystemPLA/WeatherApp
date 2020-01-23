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
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final String OPEN_WEATHER_API_KEY = "bf47d8733b57a7fad0801641fe3dc5cc";
    private static final String METRIC_UNITS = "metric";

    private Scheduler mainThreadScheduler;
    private Scheduler ioThreadScheduler;

    @Inject
    ILocationGetter locationGetter;

    @Inject
    IWeatherRepo weatherRepo;

    @Inject
    ISettings settings;

    public MainPresenter(Scheduler mainThreadScheduler,Scheduler ioThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
        this.ioThreadScheduler = ioThreadScheduler;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().checkGeolocationPermission();
        checkSettings();
        loadAccordingToSettings();
    }

    @SuppressLint("CheckResult")
    public void loadAccordingToSettings(){
        settings.getSetting().subscribe(res ->{
            if (res.equals("gps")) {
                locationGetter.getCity().subscribe(this::loadData);
            } else {
                loadData(res);
            }
        });
    }

    @SuppressLint("CheckResult")
    private void loadData(String city) {
        getViewState().showLoading();
        weatherRepo.loadWeather(city, OPEN_WEATHER_API_KEY, METRIC_UNITS)
                .subscribeOn(ioThreadScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe(model -> {
                    if (model == null) {
                        getViewState().showMessage("Место не найдено");
                    } else {
                        getViewState().setCityName(model.name);
                        getViewState().setCurrentTemperature(model.main.temp);
                        getViewState().setHumidity(model.main.humidity);
                        getViewState().setPressure(model.main.pressure);
                        getViewState().setWeatherDescription(model.weather[0].description);
                        getViewState().setWeatherIcon(model.weather[0].id,
                                model.sys.sunrise * 1000,
                                model.sys.sunset * 1000);
                        getViewState().setWindSpeed(model.wind.speed);
                        weatherRepo.loadUVI(OPEN_WEATHER_API_KEY, model.coordinates.lat, model.coordinates.lon)
                                .subscribeOn(ioThreadScheduler)
                                .observeOn(mainThreadScheduler)
                                .subscribe(uviRequestRestModel -> {
                                    getViewState().setUVIndex(uviRequestRestModel.uviValue);
                                    getViewState().hideLoading();
                                });
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void checkSettings(){
        settings.getSetting().subscribe(res->{
            if (res==null) {
                settings.resetSetting();
            }
        });
    }

    public void setSetting (String setting) {
        settings.saveSetting(setting);
    }

    public void stopGPSUpdate(){
        locationGetter.stopUpdatingLocation();
    }

    public void startGPSUpdate(){
        locationGetter.startUpdatingLocation();
    }
}
