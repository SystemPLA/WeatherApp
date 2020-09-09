package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.SerializedName

class WeatherRequestRestModel {
    @SerializedName("coord")
    var coordinates: CoordRestModel? = null

    var weather: Array<WeatherRestModel>? = null

    var base: String? = null

    var main: MainRestModel? = null

    var visibility = 0

    var wind: WindRestModel? = null

    var clouds: CloudsRestModel? = null

    var dt: Long = 0

    var sys: SysRestModel? = null

    var id: Long = 0

    var timezone = 0

    var name: String? = null

    var cod = 0
}