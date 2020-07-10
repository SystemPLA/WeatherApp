package ru.systempla.weatherapp.di.module

import dagger.Module
import dagger.Provides
import ru.systempla.weatherapp.mvp.App

@Module
class MyAppModule(private val app: App) {
    @Provides
    fun app(): App = app
}