package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose

class CoordRestModel {
    @Expose
    var lat: Double = 0.0

    @Expose
    var lon: Double = 0.0
}