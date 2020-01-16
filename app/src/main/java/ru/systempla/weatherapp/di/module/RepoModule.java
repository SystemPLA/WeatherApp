package ru.systempla.weatherapp.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.systempla.weatherapp.mvp.model.api.IDataSource;
import ru.systempla.weatherapp.mvp.model.repo.IWeatherRepo;
import ru.systempla.weatherapp.mvp.model.repo.WeatherRepo;


@Module(includes = {ApiModule.class})
public class RepoModule {

    @Singleton
    @Provides
    public IWeatherRepo weatherRepo(IDataSource api) {
        return new WeatherRepo(api);
    }
}
