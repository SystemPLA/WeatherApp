package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose

class MainRestModel {
    @Expose
    var temp = 0f

    @Expose
    var pressure = 0f

    @Expose
    var humidity = 0f
}