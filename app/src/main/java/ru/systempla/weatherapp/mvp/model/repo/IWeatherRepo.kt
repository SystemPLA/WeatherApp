package ru.systempla.weatherapp.mvp.model.repo

import io.reactivex.Single
import ru.systempla.weatherapp.mvp.model.entity.ForecastRequestRestModel
import ru.systempla.weatherapp.mvp.model.entity.UVIRequestRestModel
import ru.systempla.weatherapp.mvp.model.entity.WeatherRequestRestModel

interface IWeatherRepo {
    fun loadWeather(city: String, keyApi: String,
                    units: String, language: String): Single<WeatherRequestRestModel>

    fun loadUVI(keyApi: String, latitude: Double, longitude: Double): Single<UVIRequestRestModel>

    fun loadForecast(city: String, keyApi: String,
                     units: String, language: String): Single<ForecastRequestRestModel>
}