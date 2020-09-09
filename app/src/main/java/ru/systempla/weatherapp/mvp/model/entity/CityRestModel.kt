package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CityRestModel {
    @Expose
    var id: Int? = null

    @Expose
    var name: String? = null

    @Expose
    var coord: CoordRestModel? = null

    @Expose
    var country: String? = null

    @Expose
    var population: Int? = null

    @Expose
    var timezone: Int? = null

    @Expose
    var sunrise: Int? = null

    @Expose
    var sunset: Int? = null
}