package ru.systempla.weatherapp.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface WeatherDataView : MvpView {
    fun setCityName(name: String)
    fun setWeatherDescription(description: String)
    fun setUVIndex(uvIndex: Float)
    fun setPressure(pressure: Float)
    fun setHumidity(humidity: Float)
    fun setWindSpeed(speed: Double)
    fun setWeatherIcon(actualId: Int, sunrise: Long, sunset: Long)
    fun setCurrentTemperature(temp: Float)
    fun showLoading()
    fun hideLoading()
    fun checkForGPSUpdate()

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun showMessage(text: String?)
}