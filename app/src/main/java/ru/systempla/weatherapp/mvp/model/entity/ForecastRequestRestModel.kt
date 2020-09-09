package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose

class ForecastRequestRestModel {
    @Expose
    lateinit var list: List<ForecastEntityRestModel>

    @Expose
    lateinit var city: CityRestModel
}