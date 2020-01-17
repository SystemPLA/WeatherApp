package ru.systempla.weatherapp.mvp.presenter;

import android.annotation.SuppressLint;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.systempla.weatherapp.mvp.model.location.ILocationGetter;
import ru.systempla.weatherapp.mvp.model.repo.IWeatherRepo;
import ru.systempla.weatherapp.mvp.view.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final String OPEN_WEATHER_API_KEY = "bf47d8733b57a7fad0801641fe3dc5cc";
    private static final String METRIC_UNITS = "metric";

    @Inject
    ILocationGetter locationGetter;

    @Inject
    IWeatherRepo weatherRepo;

    @SuppressLint("CheckResult")
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        locationGetter.getCity().subscribe(this::loadData);
    }

    public void loadData(String city) {
//        getViewState().showLoading();
        weatherRepo.loadWeather(city, OPEN_WEATHER_API_KEY, METRIC_UNITS)
                .subscribeOn(/*задавать извне*/Schedulers.io())
                .observeOn(/*задавать извне*/AndroidSchedulers.mainThread())
                .subscribe(/*weatherRequestRestModel -> {getViewState().загрузка данных во вью
                getViewState().hideLoading();
                }*/);
    }
}
