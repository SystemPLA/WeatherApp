package ru.systempla.weatherapp.mvp.presenter;

import android.annotation.SuppressLint;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.systempla.weatherapp.mvp.model.location.ILocationGetter;
import ru.systempla.weatherapp.mvp.model.repo.IWeatherRepo;
import ru.systempla.weatherapp.mvp.model.settings.ISettings;
import ru.systempla.weatherapp.mvp.view.WeatherDataView;
import timber.log.Timber;

@SuppressWarnings({"CheckResult","unused"})
@InjectViewState
public class WeatherDataPresenter extends MvpPresenter<WeatherDataView> {

    private static final String OPEN_WEATHER_API_KEY = "bf47d8733b57a7fad0801641fe3dc5cc";
    private static final String METRIC_UNITS = "metric";

    private Scheduler mainThreadScheduler;
    private Scheduler ioThreadScheduler;
    private String language;

    @Inject
    IWeatherRepo weatherRepo;

    @Inject
    ILocationGetter locationGetter;

    @Inject
    ISettings settings;

    public WeatherDataPresenter(Scheduler mainThreadScheduler, Scheduler ioThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
        this.ioThreadScheduler = ioThreadScheduler;
    }

    public void loadAccordingToSettings(){
        Disposable disposable = settings.getSetting().subscribe(res ->{
                    if (res.equals("gps")) {
                        Disposable disposable1Sup = locationGetter.getCity().subscribe(this::loadData);
                    } else {
                        loadData(res);
                    }
                }
        );
    }

    public void checkSettings(){
        Disposable disposable = settings.getSetting().subscribe(res->{},t->settings.resetSetting());
    }

    @SuppressLint("CheckResult")
    private void loadData(String city) {
        getViewState().showLoading();
        Disposable disposable = weatherRepo.loadWeather(city, OPEN_WEATHER_API_KEY, METRIC_UNITS, "ru")
                .subscribeOn(ioThreadScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe(model -> {
                            getViewState().setCityName(model.name);
                            getViewState().setCurrentTemperature(model.main.temp);
                            getViewState().setHumidity(model.main.humidity);
                            getViewState().setPressure(model.main.pressure);
                            getViewState().setWeatherDescription(model.weather[0].description);
                            getViewState().setWeatherIcon(model.weather[0].id,
                                    model.sys.sunrise * 1000,
                                    model.sys.sunset * 1000);
                            getViewState().setWindSpeed(model.wind.speed);
                            Disposable disposableSup = weatherRepo.loadUVI(OPEN_WEATHER_API_KEY, model.coordinates.lat, model.coordinates.lon)
                                    .subscribeOn(ioThreadScheduler)
                                    .observeOn(mainThreadScheduler)
                                    .subscribe(uviRequestRestModel -> {
                                        getViewState().setUVIndex(uviRequestRestModel.uviValue);
                                        getViewState().hideLoading();
                                    }, t -> {
                                        getViewState().showMessage("ошибка получения UV индекса");
                                        getViewState().setUVIndex(0);
                                        getViewState().hideLoading();
                                    } );
                        }, t -> {
                    getViewState().showMessage("Место не найдено");
                    settings.resetSetting();
                    getViewState().hideLoading();
                });
    }

    public void setSetting (String setting) {
        settings.saveSetting(setting);
    }
}
