package ru.systempla.weatherapp.di.module

import android.location.LocationListener
import dagger.Module
import dagger.Provides
import ru.systempla.weatherapp.mvp.model.location.BlankLocationListener
import javax.inject.Singleton

@Module
class LocationListenerModule {
    @Singleton
    @Provides
    fun locationListener(): LocationListener = BlankLocationListener()
}