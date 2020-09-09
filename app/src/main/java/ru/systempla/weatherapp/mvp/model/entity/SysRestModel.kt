package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.SerializedName

class SysRestModel {
    var type = 0

    var id = 0

    var message = 0f

    var country: String? = null

    var sunrise: Long = 0

    var sunset: Long = 0
}