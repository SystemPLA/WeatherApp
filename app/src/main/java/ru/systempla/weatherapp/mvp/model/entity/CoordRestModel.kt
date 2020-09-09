package ru.systempla.weatherapp.mvp.model.entity

import com.google.gson.annotations.Expose

class CoordRestModel {
    @Expose
    var lat: Double? = null

    @Expose
    var lon: Double? = null
}