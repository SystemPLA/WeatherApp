package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WindRestModel {
    @SerializedName("speed")
    @Expose
    var speed: Double? = null

    @SerializedName("deg")
    @Expose
    var deg: Int? = null
}