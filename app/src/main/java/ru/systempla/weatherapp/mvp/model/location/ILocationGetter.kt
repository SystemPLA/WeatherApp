package ru.systempla.weatherapp.mvp.model.location

import io.reactivex.Single

interface ILocationGetter {
    val city: Single<String>
    fun startUpdatingLocation()
    fun stopUpdatingLocation()
}
