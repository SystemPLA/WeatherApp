package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose

class WeatherRestModel {
    @Expose
    var id: Int = 0

    @Expose
    lateinit var description: String

    @Expose
    lateinit var icon: String
}
