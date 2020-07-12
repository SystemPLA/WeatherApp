package ru.systempla.weatherapp.mvp.presenter

import android.annotation.SuppressLint
import android.text.TextUtils.equals
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
    lateinit var weatherRepo: IWeatherRepo

    @Inject
    lateinit var locationGetter: ILocationGetter

    @Inject
    lateinit var settings: ISettings

    fun loadAccordingToSettings() {
        val disposable: Disposable = settings.setting.subscribe { res ->
            if (res == "gps") {
                val disposable1Sup: Disposable = locationGetter.city.subscribe { city: String -> loadData(city) }
            } else {
                loadData(res)
            }
        }
    }

    fun checkSettings() {
        val disposable: Disposable = settings.setting.subscribe({  }, { settings.resetSetting() })
    }

    @SuppressLint("CheckResult")
    private fun loadData(city: String) {
        viewState!!.showLoading()
        val disposable: Disposable = language?.let { it ->
            weatherRepo.loadWeather(city, OPEN_WEATHER_API_KEY, METRIC_UNITS, it)
                    .subscribeOn(ioThreadScheduler)
                    .observeOn(mainThreadScheduler)
                    .subscribe({ model ->
                        model.name?.let { it1 -> viewState!!.setCityName(it1) }
                        viewState!!.setCurrentTemperature(model.main!!.temp)
                        viewState!!.setHumidity(model.main!!.humidity)
                        viewState!!.setPressure(model.main!!.pressure)
                        model.weather!![0].description?.let { viewState!!.setWeatherDescription(it) }
                        model.weather!![0].id?.let {
                            viewState!!.setWeatherIcon(it,
                                    model.sys!!.sunrise * 1000,
                                    model.sys!!.sunset * 1000)
                        }
                        model.wind!!.speed?.let { viewState!!.setWindSpeed(it) }
                        val disposableSup: Disposable = model.coordinates!!.lon?.let {
                            model.coordinates!!.lat?.let { it1 ->
                                weatherRepo.loadUVI(OPEN_WEATHER_API_KEY, it1, it)
                                        .subscribeOn(ioThreadScheduler)
                                        .observeOn(mainThreadScheduler)
                                        .subscribe({ uviRequestRestModel ->
                                            viewState!!.setUVIndex(uviRequestRestModel.uviValue)
                                            viewState!!.hideLoading()
                                        }, {
                                            viewState!!.showMessage("ошибка получения UV индекса")
                                            viewState!!.setUVIndex(0f)
                                            viewState!!.hideLoading()
                                        })
                            }
                        }!!
                    }, {
                        viewState!!.showMessage("Место не найдено")
                        settings.resetSetting()
                        viewState!!.hideLoading()
                    })
        }!!
    }

    fun setSetting(setting: String) {
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