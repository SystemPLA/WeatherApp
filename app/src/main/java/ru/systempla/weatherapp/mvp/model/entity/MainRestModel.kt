package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.SerializedName

class MainRestModel {
    @SerializedName("temp")
    var temp = 0f

    @SerializedName("pressure")
    var pressure = 0f

    @SerializedName("humidity")
    var humidity = 0f

    @SerializedName("temp_min")
    var tempMin = 0f

    @SerializedName("temp_max")
    var tempMax = 0f
}