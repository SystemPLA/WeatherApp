package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose

class CityRestModel {
    @Expose
    var id: Int? = null

    @Expose
    var name: String? = null
}