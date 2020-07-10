package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.SerializedName

class WeatherRequestRestModel {
    @SerializedName("coord")
    var coordinates: CoordRestModel? = null

    @SerializedName("weather")
    var weather: Array<WeatherRestModel>? = null

    @SerializedName("base")
    var base: String? = null

    @SerializedName("main")
    var main: MainRestModel? = null

    @SerializedName("visibility")
    var visibility = 0

    @SerializedName("wind")
    var wind: WindRestModel? = null

    @SerializedName("clouds")
    var clouds: CloudsRestModel? = null

    @SerializedName("dt")
    var dt: Long = 0

    @SerializedName("sys")
    var sys: SysRestModel? = null

    @SerializedName("id")
    var id: Long = 0

    @SerializedName("timezone")
    var timezone = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("cod")
    var cod = 0
}