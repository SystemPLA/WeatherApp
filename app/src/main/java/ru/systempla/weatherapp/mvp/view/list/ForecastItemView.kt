package ru.systempla.weatherapp.mvp.view.list

interface ForecastItemView {
    val pos: Int

    fun setDateTime(dt: Long)
    fun setTemperature(temp: Float)
    fun setWeatherDescription(description: String)
    fun setWeatherIcon(actualId: Int, icon: String)
}