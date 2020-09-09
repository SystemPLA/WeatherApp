package ru.systempla.weatherapp.navigation

import androidx.fragment.app.Fragment
import ru.systempla.weatherapp.mvp.view.ui.fragment.ForecastFragment
import ru.systempla.weatherapp.mvp.view.ui.fragment.WeatherDataFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class WeatherDataScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return WeatherDataFragment.newInstance()
        }
    }

    class WeekForecastScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ForecastFragment.newInstance()
        }
    }
}