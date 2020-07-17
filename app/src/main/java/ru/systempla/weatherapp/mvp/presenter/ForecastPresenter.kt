package ru.systempla.weatherapp.mvp.presenter

import android.annotation.SuppressLint
import io.reactivex.Scheduler
import io.reactivex.subjects.PublishSubject
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.systempla.weatherapp.mvp.model.entity.ForecastEntityRestModel
import ru.systempla.weatherapp.mvp.model.location.ILocationGetter
import ru.systempla.weatherapp.mvp.model.repo.IWeatherRepo
import ru.systempla.weatherapp.mvp.model.settings.ISettings
import ru.systempla.weatherapp.mvp.view.ForecastView
import ru.systempla.weatherapp.mvp.view.list.ForecastItemView
import java.util.*
import javax.inject.Inject

@InjectViewState
class ForecastPresenter(private val mainThreadScheduler: Scheduler, private val ioThreadScheduler: Scheduler) : MvpPresenter<ForecastView>() {

    var forecastListPresenter: IForecastListPresenter

    init {
        forecastListPresenter = ForecastListPresenter()
    }

    internal inner class ForecastListPresenter : IForecastListPresenter {
        override var clickSubject: PublishSubject<ForecastItemView> = PublishSubject.create()
        override var forecastBlocks: MutableList<ForecastEntityRestModel> = ArrayList()
        override fun bind(view: ForecastItemView) {
            forecastBlocks[view.pos].dt?.let { view.setDateTime(it) }
            view.setTemperature(forecastBlocks[view.pos].main!!.temp)
            forecastBlocks[view.pos].weather!![0].description?.let { view.setWeatherDescription(it) }
            forecastBlocks[view.pos].weather!![0].id?.let {
                forecastBlocks[view.pos].weather!![0].icon?.let { it1 ->
                    view.setWeatherIcon(it,
                            it1)
                }
            }
        }

        override val count: Int
            get() = forecastBlocks.size
    }

    private lateinit var language: String

    @Inject
    lateinit var weatherRepo: IWeatherRepo

    @Inject
    lateinit var locationGetter: ILocationGetter

    @Inject
    lateinit var settings: ISettings

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState!!.init()
    }

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
       settings.citySetting.subscribe({}, { settings.resetCitySetting() })
    }

    @SuppressLint("CheckResult")
    fun loadGPSData(){
        locationGetter.city.subscribe { city: String -> loadData(city) }
    }

    @SuppressLint("CheckResult")
    private fun loadData(city: String) {
        viewState.showLoading()
        weatherRepo.loadForecast(city, OPEN_WEATHER_API_KEY, METRIC_UNITS, language)
                .subscribeOn(ioThreadScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe({ model ->
                    viewState.setCity(model.city!!.name)
                    forecastListPresenter.forecastBlocks.clear()
                    model.list?.let { forecastListPresenter.forecastBlocks.addAll(it) }
                    viewState.updateList()
                    viewState.hideLoading()
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