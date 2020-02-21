package ru.systempla.weatherapp.mvp.model.repo;

import io.reactivex.Single;
import ru.systempla.weatherapp.mvp.model.entity.ForecastRequestRestModel;
import ru.systempla.weatherapp.mvp.model.entity.UVIRequestRestModel;
import ru.systempla.weatherapp.mvp.model.entity.WeatherRequestRestModel;


public interface IWeatherRepo {
    Single<WeatherRequestRestModel> loadWeather(String city, String keyApi,
                                                String units);

    Single<UVIRequestRestModel> loadUVI(String keyApi, double latitude, double longitude);

    Single<ForecastRequestRestModel> loadForecast(String city, String keyApi,
                                                  String units);
}
