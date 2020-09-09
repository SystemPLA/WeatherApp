package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UVIRequestRestModel {
    @SerializedName("value")
    @Expose
    var uviValue = 0f
}