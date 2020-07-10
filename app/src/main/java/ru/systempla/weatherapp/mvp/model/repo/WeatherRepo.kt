package ru.systempla.weatherapp.mvp.model.repo

import io.reactivex.Single
import ru.systempla.weatherapp.mvp.model.api.IDataSource
import ru.systempla.weatherapp.mvp.model.entity.ForecastRequestRestModel
import ru.systempla.weatherapp.mvp.model.entity.UVIRequestRestModel
import ru.systempla.weatherapp.mvp.model.entity.WeatherRequestRestModel

class WeatherRepo(private val api: IDataSource) : IWeatherRepo {
    override fun loadWeather(city: String, keyApi: String,
                             units: String, language: String): Single<WeatherRequestRestModel> =
            api.loadWeather(city, keyApi, units, language)

    override fun loadUVI(keyApi: String, latitude: Double, longitude: Double): Single<UVIRequestRestModel> =
            api.loadUVI(keyApi, latitude, longitude)

    override fun loadForecast(city: String, keyApi: String,
                              units: String, language: String): Single<ForecastRequestRestModel> =
            api.loadForecast(city, keyApi, units, language)
}