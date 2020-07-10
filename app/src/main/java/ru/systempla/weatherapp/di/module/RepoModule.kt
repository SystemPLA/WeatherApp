package ru.systempla.weatherapp.di.module

import dagger.Module
import dagger.Provides
import ru.systempla.weatherapp.mvp.model.api.IDataSource
import ru.systempla.weatherapp.mvp.model.repo.IWeatherRepo
import ru.systempla.weatherapp.mvp.model.repo.WeatherRepo
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class RepoModule {
    @Singleton
    @Provides
    fun weatherRepo(api: IDataSource): IWeatherRepo = WeatherRepo(api)
}