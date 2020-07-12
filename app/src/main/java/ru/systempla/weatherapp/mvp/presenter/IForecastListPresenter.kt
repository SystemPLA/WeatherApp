package ru.systempla.weatherapp.mvp.presenter

import io.reactivex.subjects.PublishSubject
import ru.systempla.weatherapp.mvp.model.entity.ForecastEntityRestModel
import ru.systempla.weatherapp.mvp.view.list.ForecastItemView

interface IForecastListPresenter {
    fun bind(view: ForecastItemView)
    val count: Int
    val forecastBlocks: MutableList<ForecastEntityRestModel>
    val clickSubject: PublishSubject<ForecastItemView>
}