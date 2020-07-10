package ru.systempla.weatherapp.mvp.presenter

import android.annotation.SuppressLint
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.systempla.weatherapp.mvp.model.location.ILocationGetter
import ru.systempla.weatherapp.mvp.model.repo.IWeatherRepo
import ru.systempla.weatherapp.mvp.model.settings.ISettings
import ru.systempla.weatherapp.mvp.view.WeatherDataView
import javax.inject.Inject

@InjectViewState
class WeatherDataPresenter(private val mainThreadScheduler: Scheduler, private val ioThreadScheduler: Scheduler) : MvpPresenter<WeatherDataView?>() {
    private var language: String? = null

    @Inject
    var weatherRepo: IWeatherRepo? = null

    @Inject
    var locationGetter: ILocationGetter? = null

    @Inject
    var settings: ISettings? = null
    fun loadAccordingToSettings() {
        val disposable: Disposable = settings.getSetting().subscribe({ res ->
            if (res.equals("gps")) {
                val disposable1Sup: Disposable = locationGetter.getCity().subscribe({ city: String -> loadData(city) })
            } else {
                loadData(res)
            }
        }
        )
    }

    fun checkSettings() {
        val disposable: Disposable = settings.getSetting().subscribe({ res -> }, { t -> settings.resetSetting() })
    }

    @SuppressLint("CheckResult")
    private fun loadData(city: String) {
        viewState!!.showLoading()
        val disposable: Disposable = weatherRepo.loadWeather(city, OPEN_WEATHER_API_KEY, METRIC_UNITS, language)
                .subscribeOn(ioThreadScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe({ model ->
                    viewState!!.setCityName(model.name)
                    viewState!!.setCurrentTemperature(model.main.temp)
                    viewState!!.setHumidity(model.main.humidity)
                    viewState!!.setPressure(model.main.pressure)
                    viewState!!.setWeatherDescription(model.weather.get(0).description)
                    viewState!!.setWeatherIcon(model.weather.get(0).id,
                            model.sys.sunrise * 1000,
                            model.sys.sunset * 1000)
                    viewState!!.setWindSpeed(model.wind.speed)
                    val disposableSup: Disposable = weatherRepo.loadUVI(OPEN_WEATHER_API_KEY, model.coordinates.lat, model.coordinates.lon)
                            .subscribeOn(ioThreadScheduler)
                            .observeOn(mainThreadScheduler)
                            .subscribe({ uviRequestRestModel ->
                                viewState!!.setUVIndex(uviRequestRestModel.uviValue)
                                viewState!!.hideLoading()
                            }, { t ->
                                viewState!!.showMessage("ошибка получения UV индекса")
                                viewState!!.setUVIndex(0f)
                                viewState!!.hideLoading()
                            })
                }, { t ->
                    viewState!!.showMessage("Место не найдено")
                    settings.resetSetting()
                    viewState!!.hideLoading()
                })
    }

    fun setSetting(setting: String?) {
        settings.saveSetting(setting)
    }

    fun setLanguage(language: String?) {
        this.language = language
    }

    companion object {
        private const val OPEN_WEATHER_API_KEY = "bf47d8733b57a7fad0801641fe3dc5cc"
        private const val METRIC_UNITS = "metric"
    }

}