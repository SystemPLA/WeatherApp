package ru.systempla.weatherapp.di

import com.tbruyelle.rxpermissions3.RxPermissions
import dagger.Component
import ru.systempla.weatherapp.di.module.*
import ru.systempla.weatherapp.mvp.presenter.ForecastPresenter
import ru.systempla.weatherapp.mvp.presenter.MainPresenter
import ru.systempla.weatherapp.mvp.presenter.WeatherDataPresenter
import ru.systempla.weatherapp.mvp.view.ui.MainActivity
import ru.systempla.weatherapp.mvp.view.ui.fragment.ForecastFragment
import ru.systempla.weatherapp.mvp.view.ui.fragment.WeatherDataFragment
import javax.inject.Singleton

@Singleton
@Component(modules =
    [RepoModule::class,
    LocationGetterModule::class,
    SettingsModule::class,
    CiceroneModule::class]
)
interface AppComponent {
    fun inject(presenter: MainPresenter)
    fun inject(mainActivity: MainActivity)
    fun inject(weatherDataPresenter: WeatherDataPresenter)
    fun inject(weatherDataFragment: WeatherDataFragment)
    fun inject(forecastPresenter: ForecastPresenter)
    fun inject(forecastFragment: ForecastFragment)
}