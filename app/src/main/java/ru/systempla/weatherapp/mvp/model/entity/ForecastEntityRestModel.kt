package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose

class ForecastEntityRestModel {
    @Expose
    var dt: Long? = null

    @Expose
    var main: MainRestModel? = null

    @Expose
    var weather: List<WeatherRestModel>? = null
}