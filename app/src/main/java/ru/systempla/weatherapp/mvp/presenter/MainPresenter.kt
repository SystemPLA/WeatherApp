package ru.systempla.weatherapp.mvp.presenter

import android.annotation.SuppressLint
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.systempla.weatherapp.mvp.model.location.ILocationGetter
import ru.systempla.weatherapp.mvp.model.settings.ISettings
import ru.systempla.weatherapp.mvp.view.MainView
import ru.systempla.weatherapp.navigation.Screens.WeatherDataScreen
import ru.systempla.weatherapp.navigation.Screens.WeekForecastScreen
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject
    lateinit var locationGetter: ILocationGetter

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var settings: ISettings

    fun stopGPSUpdate() {
        locationGetter.stopUpdatingLocation()
    }

    @SuppressLint("CheckResult")
    fun requestGPSUpdate() {
        settings.permissionsSetting.subscribe(
                {setting -> if (setting != 0) viewState.checkForGPSUpdate()},
                {
                    settings.resetPermissionsSetting()
                    viewState.checkForGPSUpdate()
                }
        )
    }

    fun startGPSUpdate() {
        locationGetter.startUpdatingLocation()
    }

    fun navigateToWeatherData() {
        router.navigateTo(WeatherDataScreen())
    }

    fun navigateToForecast() {
        router.navigateTo(WeekForecastScreen())
    }

    fun setPermissionsSetting(setting: Int) {
        settings.savePermissionsSetting(setting)
    }
}