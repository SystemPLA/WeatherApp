package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose

class ForecastEntityRestModel {
    @Expose
    var dt: Long = 0

    @Expose
    lateinit var main: MainRestModel

    @Expose
    lateinit var weather: List<WeatherRestModel>
}