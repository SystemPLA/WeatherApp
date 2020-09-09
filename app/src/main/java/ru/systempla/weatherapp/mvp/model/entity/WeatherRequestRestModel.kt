package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherRequestRestModel {
    @SerializedName("coord")
    @Expose
    var coordinates: CoordRestModel? = null

    @Expose
    var weather: Array<WeatherRestModel>? = null

    @Expose
    var main: MainRestModel? = null

    @Expose
    var wind: WindRestModel? = null

    @Expose
    var sys: SysRestModel? = null

    @Expose
    var id: Long = 0

    @Expose
    var name: String? = null
}