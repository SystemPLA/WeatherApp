package ru.systempla.weatherapp.mvp.model.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.systempla.weatherapp.mvp.model.entity.ForecastRequestRestModel
import ru.systempla.weatherapp.mvp.model.entity.UVIRequestRestModel
import ru.systempla.weatherapp.mvp.model.entity.WeatherRequestRestModel

interface IDataSource {
    @GET("data/2.5/weather")
    fun loadWeather(@Query("q") city: String,
                    @Query("appid") keyApi: String,
                    @Query("units") units: String,
                    @Query("lang") language: String): Single<WeatherRequestRestModel>

    @GET("data/2.5/uvi")
    fun loadUVI(@Query("appid") keyApi: String,
                @Query("lat") latitude: Double,
                @Query("lon") longitude: Double): Single<UVIRequestRestModel>

    @GET("data/2.5/forecast")
    fun loadForecast(@Query("q") city: String,
                     @Query("appid") keyApi: String,
                     @Query("units") units: String,
                     @Query("lang") language: String): Single<ForecastRequestRestModel>
}