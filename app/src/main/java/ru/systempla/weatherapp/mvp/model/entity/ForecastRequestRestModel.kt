package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose

class ForecastRequestRestModel {
    @Expose
    var list: List<ForecastEntityRestModel>? = null

    @Expose
    var city: CityRestModel? = null
}