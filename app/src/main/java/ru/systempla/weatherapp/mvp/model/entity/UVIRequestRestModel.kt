package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.SerializedName

class UVIRequestRestModel {
    @SerializedName("lat")
    var latitude = 0f

    @SerializedName("lon")
    var longitude = 0f

    @SerializedName("date_iso")
    var dateTime: String? = null

    @SerializedName("date")
    var timeStamp = 0

    @SerializedName("value")
    var uviValue = 0f
}