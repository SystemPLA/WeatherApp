package ru.systempla.weatherapp.di.module;

import dagger.Module;
import dagger.Provides;
import ru.systempla.weatherapp.mvp.location_getter.GPSLocationGetter;
import ru.systempla.weatherapp.mvp.location_getter.ILocationGetter;

@Module
public class LocationGetterModule {

    @Provides
    public ILocationGetter GPSLocation(){
        return new GPSLocationGetter();
    }
}
