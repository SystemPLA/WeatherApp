package ru.systempla.weatherapp.di.module;

import android.location.LocationListener;
import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.systempla.weatherapp.mvp.App;
import ru.systempla.weatherapp.mvp.model.location.GPSLocationGetter;
import ru.systempla.weatherapp.mvp.model.location.ILocationGetter;

import static android.content.Context.LOCATION_SERVICE;

@SuppressWarnings("WeakerAccess")
@Module(includes = {LocationListenerModule.class})
public class LocationGetterModule {

    @Singleton
    @Provides
    public ILocationGetter GPSLocation(LocationListener locationListener){
        return new GPSLocationGetter((LocationManager) App.getInstance().getSystemService(LOCATION_SERVICE), locationListener);
    }
}
