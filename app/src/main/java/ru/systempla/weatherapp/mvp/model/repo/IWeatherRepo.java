package ru.systempla.weatherapp.mvp.model.repo;

import io.reactivex.Single;
import ru.systempla.weatherapp.mvp.model.entity.WeatherRequestRestModel;


public interface IWeatherRepo {
    Single<WeatherRequestRestModel> loadWeather(String city, String keyApi,
                                                String units);
}
