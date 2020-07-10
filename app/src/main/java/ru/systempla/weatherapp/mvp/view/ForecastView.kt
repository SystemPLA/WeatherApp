package ru.systempla.weatherapp.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ForecastView : MvpView {
    fun init()
    fun updateList()
    fun setCity(city: String?)
    fun showLoading()
    fun hideLoading()

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun showMessage(text: String?)
}