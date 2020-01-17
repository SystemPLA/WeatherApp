package ru.systempla.weatherapp.mvp.presenter;

import android.annotation.SuppressLint;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.systempla.weatherapp.mvp.model.final_groups.FinalGroups;
import ru.systempla.weatherapp.mvp.model.location.ILocationGetter;
import ru.systempla.weatherapp.mvp.model.permissions.IPermissionManager;
import ru.systempla.weatherapp.mvp.model.repo.IWeatherRepo;
import ru.systempla.weatherapp.mvp.view.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final String OPEN_WEATHER_API_KEY = "bf47d8733b57a7fad0801641fe3dc5cc";
    private static final String METRIC_UNITS = "metric";

    @Inject
    ILocationGetter locationGetter;

    @Inject
    IPermissionManager permissionManager;

    @Inject
    IWeatherRepo weatherRepo;

    @SuppressLint("CheckResult")
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (permissionManager.checkPermission(FinalGroups.permission.ACCESS_COARSE_LOCATION,
                FinalGroups.permission.ACCESS_COARSE_LOCATION)) {
            locationGetter.getCity().subscribe(this::loadData);
        } else {
        permissionManager.getPermission(getViewState().getActivity(),
                FinalGroups.permission.ACCESS_FINE_LOCATION,
                FinalGroups.permission.ACCESS_COARSE_LOCATION);
        }

    }

    @SuppressLint("CheckResult")
    public void loadData(String city) {
        getViewState().showLoading();
        weatherRepo.loadWeather(city, OPEN_WEATHER_API_KEY, METRIC_UNITS)
                .subscribeOn(/*задавать извне*/Schedulers.io())
                .observeOn(/*задавать извне*/AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    getViewState().setCityName(model.name, model.sys.country);
                    getViewState().setCurrentTemperature(model.main.temp);
                    getViewState().setHumidity(model.main.humidity);
                    getViewState().setPressure(model.main.pressure);
                    getViewState().setWeatherDescription(model.weather[0].description);
                    getViewState().setWeatherIcon(model.weather[0].id,
                            model.sys.sunrise * 1000,
                            model.sys.sunset * 1000);
                    getViewState().setWindSpeed(model.wind.speed);
//                    C UV индексом нужно повозиться и создать новый репо
                    getViewState().setUVIndex(0);
                    getViewState().hideLoading();
                });
    }


}
