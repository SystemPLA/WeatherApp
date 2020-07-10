package ru.systempla.weatherapp.mvp.presenter

import io.reactivex.subjects.PublishSubject
import ru.systempla.weatherapp.mvp.view.list.ForecastItemView

interface IForecastListPresenter {
    fun bind(view: ForecastItemView)
    val count: Int
    val clickSubject: PublishSubject<ForecastItemView>
}