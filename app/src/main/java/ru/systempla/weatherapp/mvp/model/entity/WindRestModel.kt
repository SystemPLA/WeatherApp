package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WindRestModel {
    @Expose
    var speed: Double? = null

    @Expose
    var deg: Int? = null
}