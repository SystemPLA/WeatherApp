package ru.systempla.weatherapp.mvp.model.location;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import java.io.IOException;
import java.util.List;

import io.reactivex.Single;
import ru.systempla.weatherapp.mvp.App;

import static ru.systempla.weatherapp.mvp.model.final_groups.FinalGroups.messages.MSG_NO_DATA;

@SuppressWarnings("MissingPermission")
public class GPSLocationGetter implements ILocationGetter {

    private LocationManager locManager;
    private LocationListener locListener;

    public GPSLocationGetter(LocationManager locManager, LocationListener locListener) {
        this.locManager = locManager;
        this.locListener = locListener;
    }

    @Override
    public Single<String> getCity() {
        return Single.fromCallable(() -> getAddressByLoc
                (locManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)));
    }

    private String getAddressByLoc(Location loc) {
        if (loc == null) return MSG_NO_DATA;
        final Geocoder geo = new Geocoder(App.getInstance());
        List<Address> list;
        try {
            list = geo.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
        if (list.isEmpty()) {
            return MSG_NO_DATA;
        } else {
            Address address = list.get(0);
            return address.getLocality();
        }
    }

    @Override
    public void startUpdatingLocation(){
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000L, 1.0F, locListener);
    }

    @Override
    public void stopUpdatingLocation(){
        if (locListener != null) locManager.removeUpdates(locListener);
    }
}

