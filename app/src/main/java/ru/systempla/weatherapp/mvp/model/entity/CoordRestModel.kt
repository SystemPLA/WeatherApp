package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoordRestModel {
    @Expose
    var lat: Double? = null

    @Expose
    var lon: Double? = null
}