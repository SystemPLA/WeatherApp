package ru.systempla.weatherapp.mvp.model.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.systempla.weatherapp.rest.entities.WeatherRequestRestModel;

public interface IDataSource {
    @GET("data/2.5/weather")
    Single<WeatherRequestRestModel> loadWeather(@Query("q") String city,
                                                @Query("appid") String keyApi,
                                                @Query("units") String units);
}