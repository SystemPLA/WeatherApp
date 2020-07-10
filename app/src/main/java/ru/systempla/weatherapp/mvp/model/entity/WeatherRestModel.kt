package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherRestModel {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("main")
    @Expose
    var main: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("icon")
    @Expose
    var icon: String? = null
}
