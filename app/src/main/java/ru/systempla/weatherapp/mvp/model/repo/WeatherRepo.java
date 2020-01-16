package ru.systempla.weatherapp.mvp.model.repo;

import io.reactivex.Single;
import ru.systempla.weatherapp.mvp.model.api.IDataSource;
import ru.systempla.weatherapp.mvp.model.entity.WeatherRequestRestModel;

public class WeatherRepo implements IWeatherRepo{

    private IDataSource api;

    public WeatherRepo(IDataSource api) {
        this.api = api;
    }

    @Override
    public Single<WeatherRequestRestModel> loadWeather(String city, String keyApi,
                                                       String units) {
        return api.loadWeather(city, keyApi, units);
    }
}
