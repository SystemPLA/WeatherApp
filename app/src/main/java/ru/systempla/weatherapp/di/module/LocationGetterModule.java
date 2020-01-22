package ru.systempla.weatherapp.di.module;

import android.location.LocationListener;
import android.location.LocationManager;

import dagger.Module;
import dagger.Provides;
import ru.systempla.weatherapp.mvp.App;
import ru.systempla.weatherapp.mvp.model.location.GPSLocationGetter;
import ru.systempla.weatherapp.mvp.model.location.ILocationGetter;

import static android.content.Context.LOCATION_SERVICE;

@Module(includes = {LocationListenerModule.class})
public class LocationGetterModule {

    @Provides
    public ILocationGetter GPSLocation(LocationListener locationListener){
        return new GPSLocationGetter((LocationManager) App.getInstance().getSystemService(LOCATION_SERVICE), locationListener);
    }
}
