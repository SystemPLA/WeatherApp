package ru.systempla.weatherapp.mvp.presenter

import moxy.InjectViewState
import moxy.MvpPresenter
import ru.systempla.weatherapp.mvp.model.location.ILocationGetter
import ru.systempla.weatherapp.mvp.view.MainView
import ru.systempla.weatherapp.navigation.Screens.WeatherDataScreen
import ru.systempla.weatherapp.navigation.Screens.WeekForcastScreen
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject
    lateinit var locationGetter: ILocationGetter

    @Inject
    lateinit var router: Router

    fun stopGPSUpdate() {
        locationGetter.stopUpdatingLocation()
    }

    fun requestGPSUpdate() {
        viewState.checkForGPSUpdate()
    }

    fun startGPSUpdate() {
        locationGetter.startUpdatingLocation()
    }

    fun navigateToWeatherData() {
        router.navigateTo(WeatherDataScreen())
    }

    fun navigateToForecast() {
        router.navigateTo(WeekForcastScreen())
    }
}