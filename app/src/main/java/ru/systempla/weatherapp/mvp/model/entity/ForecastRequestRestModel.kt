package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ForecastRequestRestModel {
    @SerializedName("cod")
    @Expose
    var cod: String? = null

    @SerializedName("message")
    @Expose
    var message: Int? = null

    @SerializedName("cnt")
    @Expose
    var cnt: Int? = null

    @SerializedName("list")
    @Expose
    var list: List<ForecastEntityRestModel>? = null

    @SerializedName("city")
    @Expose
    var city: CityRestModel? = null
}