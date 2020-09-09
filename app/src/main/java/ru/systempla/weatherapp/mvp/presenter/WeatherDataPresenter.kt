package ru.systempla.weatherapp.mvp.presenter

import android.annotation.SuppressLint
import io.reactivex.Scheduler
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.systempla.weatherapp.mvp.model.location.ILocationGetter
import ru.systempla.weatherapp.mvp.model.repo.IWeatherRepo
import ru.systempla.weatherapp.mvp.model.settings.ISettings
import ru.systempla.weatherapp.mvp.view.WeatherDataView
import javax.inject.Inject

@InjectViewState
class WeatherDataPresenter(private val mainThreadScheduler: Scheduler, private val ioThreadScheduler: Scheduler) : MvpPresenter<WeatherDataView>() {
    private lateinit var language: String

    @Inject
    lateinit var weatherRepo: IWeatherRepo

    @Inject
    lateinit var locationGetter: ILocationGetter

    @Inject
    lateinit var settings: ISettings

    @SuppressLint("CheckResult")
    fun loadAccordingToSettings() {
        settings.citySetting.subscribe { res ->
            if (res == "gps") {
                settings.permissionsSetting.subscribe(
                        {
                            setting -> if (setting != 0) viewState.checkForGPSUpdate()
                        },
                        {
                            settings.resetPermissionsSetting()
                            viewState.checkForGPSUpdate()
                        })
            } else {
                loadData(res)
            }
        }
    }

    @SuppressLint("CheckResult")
    fun checkSettings() {
        settings.citySetting.subscribe({  }, { settings.resetCitySetting() })
    }

    @SuppressLint("CheckResult")
    fun loadGPSData(){
        locationGetter.city.subscribe { city: String -> loadData(city) }
    }

    @SuppressLint("CheckResult")
    private fun loadData(city: String) {
        viewState.showLoading()
        weatherRepo.loadWeather(city, OPEN_WEATHER_API_KEY, METRIC_UNITS, language)
                .subscribeOn(ioThreadScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe({ model ->
                    viewState.setCityName(model.name)
                    viewState.setCurrentTemperature(model.main.temp)
                    viewState.setHumidity(model.main.humidity)
                    viewState.setPressure(model.main.pressure)
                    viewState.setWeatherDescription(model.weather[0].description)
                    viewState.setWeatherIcon(model.weather[0].id,
                        model.sys.sunrise * 1000,
                        model.sys.sunset * 1000)
                    viewState.setWindSpeed(model.wind.speed)
                    weatherRepo.loadUVI(OPEN_WEATHER_API_KEY, model.coordinates.lat, model.coordinates.lon)
                                .subscribeOn(ioThreadScheduler)
                                .observeOn(mainThreadScheduler)
                                .subscribe({ uviRequestRestModel ->
                                    viewState.setUVIndex(uviRequestRestModel.uviValue)
                                    viewState.hideLoading()
                                }, {
                                    viewState.showMessage("ошибка получения UV индекса")
                                    viewState.setUVIndex(0f)
                                    viewState.hideLoading()
                                })
                }, {
                    viewState.showMessage("Место не найдено")
                    settings.resetCitySetting()
                    viewState.hideLoading()
                })
    }

    fun setCitySetting(setting: String) {
        settings.saveCitySetting(setting)
    }

    fun setPermissionsSetting(setting: Int) {
        settings.savePermissionsSetting(setting)
    }

    fun setLanguage(language: String) {
        this.language = language
    }

    companion object {
        private const val OPEN_WEATHER_API_KEY = "bf47d8733b57a7fad0801641fe3dc5cc"
        private const val METRIC_UNITS = "metric"
    }

}