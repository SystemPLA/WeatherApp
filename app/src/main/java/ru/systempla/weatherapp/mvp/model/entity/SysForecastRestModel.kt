package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SysForecastRestModel {
    @SerializedName("pod")
    @Expose
    var pod: String? = null
}