package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherRequestRestModel {
    @SerializedName("coord")
    @Expose
    lateinit var coordinates: CoordRestModel

    @Expose
    lateinit var weather: Array<WeatherRestModel>

    @Expose
    lateinit var main: MainRestModel

    @Expose
    lateinit var wind: WindRestModel

    @Expose
    lateinit var sys: SysRestModel

    @Expose
    var id: Long = 0

    @Expose
    lateinit var name: String
}