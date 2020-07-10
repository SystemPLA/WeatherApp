package ru.systempla.weatherapp.mvp.presenter

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
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
class ForecastPresenter(private val mainThreadScheduler: Scheduler, private val ioThreadScheduler: Scheduler) : MvpPresenter<ForecastView?>() {
    init {
        val forecastListPresenter = ForecastListPresenter()
    }

    internal inner class ForecastListPresenter : IForecastListPresenter {
        var clickSubject = PublishSubject.create<ForecastItemView>()
        var forecastBlocks: MutableList<ForecastEntityRestModel> = ArrayList<ForecastEntityRestModel>()
        override fun bind(view: ForecastItemView) {
            view.setDateTime(forecastBlocks[view.pos].dt)
            view.setTemperature(forecastBlocks[view.pos].main.temp)
            view.setWeatherDescription(forecastBlocks[view.pos].weather.get(0).description)
            view.setWeatherIcon(forecastBlocks[view.pos].weather.get(0).id,
                    forecastBlocks[view.pos].weather.get(0).icon)
        }

        override val count: Int
            get() = forecastBlocks.size

        override val clickSubject: PublishSubject<ForecastItemView>
            get() = clickSubject
    }

    private var language: String? = null

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

    fun loadAccordingToSettings() {
        val disposable: Disposable = settings.setting.subscribe { res ->
            if (res == "gps") {
                val disposableSup: Disposable = locationGetter.city.subscribe { city: String -> loadData(city) }
            } else {
                loadData(res)
            }
        }
    }

    fun checkSettings() {
        val disposable: Disposable = settings.setting.subscribe({ _ -> }, { _ -> settings.resetSetting() })
    }

    private fun loadData(city: String) {
        viewState!!.showLoading()
        val disposable: Disposable = weatherRepo.loadForecast(city, OPEN_WEATHER_API_KEY, METRIC_UNITS, language)
                .subscribeOn(ioThreadScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe({ model ->
                    viewState!!.setCity(model.city.name)
                    forecastListPresenter.forecastBlocks.clear()
                    forecastListPresenter.forecastBlocks.addAll(model.list)
                    viewState!!.updateList()
                    viewState!!.hideLoading()
                }, { _ ->
                    viewState!!.showMessage("Место не найдено")
                    settings.resetSetting()
                    viewState!!.hideLoading()
                })
    }

    fun setSetting(setting: String) {
        settings.saveSetting(setting)
    }

    fun setLanguage(language: String) {
        this.language = language
    }

    companion object {
        private const val OPEN_WEATHER_API_KEY = "bf47d8733b57a7fad0801641fe3dc5cc"
        private const val METRIC_UNITS = "metric"
    }

}