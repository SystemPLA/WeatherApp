package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ForecastEntityRestModel {
    @Expose
    var dt: Long? = null

    @Expose
    var main: MainRestModel? = null

    @Expose
    var weather: List<WeatherRestModel>? = null

    @Expose
    var clouds: CloudsRestModel? = null

    @Expose
    var wind: WindRestModel? = null

    @Expose
    var snow: SnowRestModel? = null

    @Expose
    var sys: SysForecastRestModel? = null

    @Expose
    var dtTxt: String? = null
}