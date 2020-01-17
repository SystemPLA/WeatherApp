package ru.systempla.weatherapp.di.module;

import dagger.Module;
import dagger.Provides;
import ru.systempla.weatherapp.mvp.model.location.GPSLocationGetter;
import ru.systempla.weatherapp.mvp.model.location.ILocationGetter;

@Module
public class LocationGetterModule {

    @Provides
    public ILocationGetter GPSLocation(){
        return new GPSLocationGetter();
    }
}
