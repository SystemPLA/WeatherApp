package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherRestModel {
    @Expose
    var id: Int? = null

    @Expose
    var main: String? = null

    @Expose
    var description: String? = null

    @Expose
    var icon: String? = null
}
