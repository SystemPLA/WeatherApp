package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose

class WeatherRestModel {
    @Expose
    var id: Int? = null

    @Expose
    var description: String? = null

    @Expose
    var icon: String? = null
}
