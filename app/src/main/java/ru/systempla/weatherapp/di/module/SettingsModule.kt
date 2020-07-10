package ru.systempla.weatherapp.di.module

import dagger.Module
import dagger.Provides
import ru.systempla.weatherapp.mvp.model.settings.ISettings
import ru.systempla.weatherapp.mvp.model.settings.PaperBasedSetting
import javax.inject.Singleton

@Module
class SettingsModule {
    @Singleton
    @Provides
    fun settingManager(): ISettings = PaperBasedSetting()
}