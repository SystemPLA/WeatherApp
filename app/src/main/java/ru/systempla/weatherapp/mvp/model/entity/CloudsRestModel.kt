package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CloudsRestModel {
    @SerializedName("all")
    @Expose
    var all: Int? = null
}