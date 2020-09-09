package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ForecastRequestRestModel {
    @Expose
    var cod: String? = null

    @Expose
    var message: Int? = null

    @Expose
    var cnt: Int? = null

    @Expose
    var list: List<ForecastEntityRestModel>? = null

    @Expose
    var city: CityRestModel? = null
}