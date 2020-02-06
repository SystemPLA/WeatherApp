package ru.systempla.weatherapp.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.systempla.weatherapp.mvp.model.settings.ISettings;
import ru.systempla.weatherapp.mvp.model.settings.PaperBasedSetting;

@SuppressWarnings("WeakerAccess")
@Module
public class SettingsModule {

    @Singleton
    @Provides
    public ISettings getSettingManager(){return new PaperBasedSetting();}
}
