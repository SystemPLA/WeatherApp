package ru.systempla.weatherapp.mvp.presenter;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.systempla.weatherapp.mvp.location_getter.ILocationGetter;
import ru.systempla.weatherapp.mvp.model.repo.IWeatherRepo;
import ru.systempla.weatherapp.mvp.view.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    ILocationGetter locationGetter;

    @Inject
    IWeatherRepo weatherRepo;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData(locationGetter.getCity());
    }

    public void loadData(String city) {
//        getViewState().showLoading();
        weatherRepo.loadWeather()
    }
}
