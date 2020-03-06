package ru.systempla.weatherapp.mvp.model.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.systempla.weatherapp.mvp.model.entity.ForecastRequestRestModel;
import ru.systempla.weatherapp.mvp.model.entity.UVIRequestRestModel;
import ru.systempla.weatherapp.mvp.model.entity.WeatherRequestRestModel;


public interface IDataSource {
    @GET("data/2.5/weather")
    Single<WeatherRequestRestModel> loadWeather(@Query("q") String city,
                                                @Query("appid") String keyApi,
                                                @Query("units") String units,
                                                @Query("lang") String language);

    @GET("data/2.5/uvi")
    Single<UVIRequestRestModel> loadUVI(@Query("appid") String keyApi,
                                        @Query("lat") double latitude,
                                        @Query("lon") double longitude);

    @GET("data/2.5/forecast")
    Single<ForecastRequestRestModel> loadForecast(@Query("q") String city,
                                                  @Query("appid") String keyApi,
                                                  @Query("units") String units,
                                                  @Query("lang") String language);
}