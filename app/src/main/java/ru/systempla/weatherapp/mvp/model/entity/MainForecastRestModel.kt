package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MainForecastRestModel {
    @Expose
    var temp: Double? = null

    @Expose
    var feelsLike: Double? = null

    @Expose
    var tempMin: Double? = null

    @Expose
    var tempMax: Double? = null

    @Expose
    var pressure: Int? = null

    @Expose
    var seaLevel: Int? = null

    @Expose
    var grndLevel: Int? = null

    @Expose
    var humidity: Int? = null

    @Expose
    var tempKf: Double? = null
}