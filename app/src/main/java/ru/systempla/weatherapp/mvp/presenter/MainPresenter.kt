package ru.systempla.weatherapp.mvp.presenter

import android.annotation.SuppressLint
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.systempla.weatherapp.mvp.model.location.ILocationGetter
import ru.systempla.weatherapp.mvp.view.MainView
import ru.systempla.weatherapp.navigation.Screens.WeatherDataScreen
import ru.systempla.weatherapp.navigation.Screens.WeekForcastScreen
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView?>() {
    @Inject
    var locationGetter: ILocationGetter? = null

    @Inject
    var router: Router? = null

    @SuppressLint("CheckResult")
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState!!.checkGeolocationPermission()
    }

    fun stopGPSUpdate() {
        locationGetter.stopUpdatingLocation()
    }

    fun startGPSUpdate() {
        locationGetter.startUpdatingLocation()
    }

    fun navigateToWeatherData() {
        router!!.navigateTo(WeatherDataScreen())
    }

    fun navigateToForecast() {
        router!!.navigateTo(WeekForcastScreen())
    }
}