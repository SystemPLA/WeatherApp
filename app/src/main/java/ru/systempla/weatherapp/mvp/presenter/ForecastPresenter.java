package ru.systempla.weatherapp.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.systempla.weatherapp.mvp.model.entity.ForecastEntityRestModel;
import ru.systempla.weatherapp.mvp.model.location.ILocationGetter;
import ru.systempla.weatherapp.mvp.model.repo.IWeatherRepo;
import ru.systempla.weatherapp.mvp.model.settings.ISettings;
import ru.systempla.weatherapp.mvp.view.ForecastView;
import ru.systempla.weatherapp.mvp.view.list.ForecastItemView;

@SuppressWarnings({"CheckResult","unused"})
@InjectViewState
public class ForecastPresenter extends MvpPresenter<ForecastView> {

    class ForecastListPresenter implements IForecastListPresenter {

        PublishSubject<ForecastItemView> clickSubject = PublishSubject.create();
        List<ForecastEntityRestModel> forecastBlocks = new ArrayList<>();

        @Override
        public void bind(ForecastItemView view) {

            view.setDateTime(forecastBlocks.get(view.getPos()).dt);
            view.setTemperature(forecastBlocks.get(view.getPos()).main.temp);
            view.setWeatherDescription(forecastBlocks.get(view.getPos()).weather.get(0).description);
            view.setWeatherIcon(forecastBlocks.get(view.getPos()).weather.get(0).id,
                    forecastBlocks.get(view.getPos()).weather.get(0).icon);
        }

        @Override
        public int getCount() {
            return forecastBlocks.size();
        }

        @Override
        public PublishSubject<ForecastItemView> getClickSubject() {
            return clickSubject;
        }
    }

    private Scheduler ioThreadScheduler;
    private Scheduler mainThreadScheduler;
    private ForecastListPresenter forecastListPresenter;

    public ForecastPresenter(Scheduler mainThreadScheduler, Scheduler ioThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
        this.ioThreadScheduler = ioThreadScheduler;
        forecastListPresenter = new ForecastListPresenter();
    }

    public IForecastListPresenter getForecastListPresenter() {
        return forecastListPresenter;
    }

    private static final String OPEN_WEATHER_API_KEY = "bf47d8733b57a7fad0801641fe3dc5cc";
    private static final String METRIC_UNITS = "metric";

    @Inject
    IWeatherRepo weatherRepo;

    @Inject
    ILocationGetter locationGetter;

    @Inject
    ISettings settings;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
    }

    public void loadAccordingToSettings(){
        Disposable disposable = settings.getSetting().subscribe(res ->{
                    if (res.equals("gps")) {
                        Disposable disposableSup = locationGetter.getCity().subscribe(this::loadData);
                    } else {
                        loadData(res);
                    }
                }
        );
    }

    public void checkSettings(){
        Disposable disposable = settings.getSetting().subscribe(res->{}, t->settings.resetSetting());
    }

    private void loadData(String city) {
        getViewState().showLoading();
        Disposable disposable = weatherRepo.loadForecast(city, OPEN_WEATHER_API_KEY, METRIC_UNITS)
                .subscribeOn(ioThreadScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe(model -> {
                    getViewState().setCity(model.city.name);
                    forecastListPresenter.forecastBlocks.clear();
                    forecastListPresenter.forecastBlocks.addAll(model.list);
                    getViewState().updateList();
                    getViewState().hideLoading();
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
