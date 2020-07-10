package ru.systempla.weatherapp.di.module

import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import dagger.Module
import dagger.Provides
import ru.systempla.weatherapp.mvp.App
import ru.systempla.weatherapp.mvp.model.location.GPSLocationGetter
import ru.systempla.weatherapp.mvp.model.location.ILocationGetter
import javax.inject.Singleton

@Module(includes = [LocationListenerModule::class])
class LocationGetterModule {
    @Singleton
    @Provides
    fun gpsLocation(locationListener: LocationListener): ILocationGetter =
            GPSLocationGetter(App.instance.getSystemService(Context.LOCATION_SERVICE) as LocationManager, locationListener)
}