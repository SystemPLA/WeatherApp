package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoordRestModel {
    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("lon")
    @Expose
    var lon: Double? = null
}