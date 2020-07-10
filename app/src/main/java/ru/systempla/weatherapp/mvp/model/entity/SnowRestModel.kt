package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SnowRestModel {
    @SerializedName("3h")
    @Expose
    var _3h: Double? = null
}