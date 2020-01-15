package ru.systempla.weatherapp.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.systempla.weatherapp.MainActivity;
import ru.systempla.weatherapp.di.module.AppModule;
import ru.systempla.weatherapp.di.module.LocationGetterModule;
import ru.systempla.weatherapp.mvp.presenter.MainPresenter;

@Singleton
@Component(modules = {
        RepoModule.class,
        AppModule.class,
        LocationGetterModule.class
})

public interface AppComponent {
    void inject(MainPresenter presenter);
    void inject(MainActivity mainActivity);
}