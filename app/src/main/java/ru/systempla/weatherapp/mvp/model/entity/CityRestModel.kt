package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CityRestModel {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("coord")
    @Expose
    var coord: CoordRestModel? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("population")
    @Expose
    var population: Int? = null

    @SerializedName("timezone")
    @Expose
    var timezone: Int? = null

    @SerializedName("sunrise")
    @Expose
    var sunrise: Int? = null

    @SerializedName("sunset")
    @Expose
    var sunset: Int? = null
}