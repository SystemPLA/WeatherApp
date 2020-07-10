package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.SerializedName

class SysRestModel {
    @SerializedName("type")
    var type = 0

    @SerializedName("id")
    var id = 0

    @SerializedName("message")
    var message = 0f

    @SerializedName("country")
    var country: String? = null

    @SerializedName("sunrise")
    var sunrise: Long = 0

    @SerializedName("sunset")
    var sunset: Long = 0
}