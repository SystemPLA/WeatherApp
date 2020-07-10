package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ForecastEntityRestModel {
    @SerializedName("dt")
    @Expose
    var dt: Int? = null

    @SerializedName("main")
    @Expose
    var main: MainRestModel? = null

    @SerializedName("weather")
    @Expose
    var weather: List<WeatherRestModel>? = null

    @SerializedName("clouds")
    @Expose
    var clouds: CloudsRestModel? = null

    @SerializedName("wind")
    @Expose
    var wind: WindRestModel? = null

    @SerializedName("snow")
    @Expose
    var snow: SnowRestModel? = null

    @SerializedName("sys")
    @Expose
    var sys: SysForecastRestModel? = null

    @SerializedName("dt_txt")
    @Expose
    var dtTxt: String? = null
}