package ru.systempla.weatherapp.di.module;

import android.location.LocationListener;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.systempla.weatherapp.mvp.model.location.BlankLocationListener;

@Module
public class LocationListenerModule {

    @Singleton
    @Provides
    public LocationListener locationListener() {
        return new BlankLocationListener();
    }
}
