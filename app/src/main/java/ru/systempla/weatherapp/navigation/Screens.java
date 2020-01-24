package ru.systempla.weatherapp.navigation;

import androidx.fragment.app.Fragment;

import ru.systempla.weatherapp.mvp.view.ui.fragment.ForecastFragment;
import ru.systempla.weatherapp.mvp.view.ui.fragment.WeatherDataFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {
    public static class WeatherDataScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return WeatherDataFragment.newInstance();
        }
    }

    public static class WeekForcastScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return ForecastFragment.newInstance();
        }
    }
}
