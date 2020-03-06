package ru.systempla.weatherapp.mvp.model.repo;

import io.reactivex.Single;
import ru.systempla.weatherapp.mvp.model.api.IDataSource;
import ru.systempla.weatherapp.mvp.model.entity.ForecastRequestRestModel;
import ru.systempla.weatherapp.mvp.model.entity.UVIRequestRestModel;
import ru.systempla.weatherapp.mvp.model.entity.WeatherRequestRestModel;

public class WeatherRepo implements IWeatherRepo{

    private IDataSource api;

    public WeatherRepo(IDataSource api) {
        this.api = api;
    }

    @Override
    public Single<WeatherRequestRestModel> loadWeather(String city, String keyApi,
                                                       String units, String language) {
        return api.loadWeather(city, keyApi, units, language);
    }

    @Override
    public Single<UVIRequestRestModel> loadUVI(String keyApi, double latitude, double longitude) {

        return api.loadUVI(keyApi, latitude, longitude);
    }

    @Override
    public Single<ForecastRequestRestModel> loadForecast(String city, String keyApi,
                                                         String units, String language) {

        return api.loadForecast(city, keyApi, units, language);
    }
}
