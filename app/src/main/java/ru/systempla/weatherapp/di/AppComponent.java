package ru.systempla.weatherapp.di;

import javax.inject.Singleton;

import dagger.Component;

import ru.systempla.weatherapp.di.module.AppModule;
import ru.systempla.weatherapp.di.module.CiceroneModule;
import ru.systempla.weatherapp.di.module.LocationGetterModule;
import ru.systempla.weatherapp.di.module.RepoModule;
import ru.systempla.weatherapp.di.module.SettingsModule;
import ru.systempla.weatherapp.mvp.presenter.MainPresenter;
import ru.systempla.weatherapp.mvp.presenter.WeatherDataPresenter;
import ru.systempla.weatherapp.mvp.view.ui.MainActivity;
import ru.systempla.weatherapp.mvp.view.ui.fragment.ForecastFragment;
import ru.systempla.weatherapp.mvp.view.ui.fragment.WeatherDataFragment;

@Singleton
@Component(modules = {
        RepoModule.class,
        AppModule.class,
        LocationGetterModule.class,
        SettingsModule.class,
        CiceroneModule.class
})

public interface AppComponent {
    void inject(MainPresenter presenter);
    void inject(MainActivity mainActivity);
    void inject(WeatherDataPresenter weatherDataPresenter);
    void inject(WeatherDataFragment weatherDataFragment);
    void inject(ForecastFragment forcastFragment);
}