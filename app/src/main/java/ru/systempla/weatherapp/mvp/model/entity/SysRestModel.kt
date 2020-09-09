package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose

class SysRestModel {
    @Expose
    var id = 0

    @Expose
    var sunrise: Long = 0

    @Expose
    var sunset: Long = 0
}