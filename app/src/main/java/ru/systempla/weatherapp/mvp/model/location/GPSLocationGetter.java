package ru.systempla.weatherapp.mvp.model.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import ru.systempla.weatherapp.mvp.App;

public class GPSLocationGetter implements ILocationGetter {

    private LocationManager mLocManager;

    public GPSLocationGetter() {
        mLocManager = (LocationManager) App.getInstance().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
    }

    public GPSLocationGetter(LocationManager mLocManager) {
        this.mLocManager = mLocManager;
    }

    @Override
    public Single<String> getCity() {
        return Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getAddressByLoc
                        (mLocManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER));
            }
        });
    }

    private String getAddressByLoc(Location loc) {
        if (loc == null) return MSG_NO_DATA;
        final Geocoder geo = new Geocoder(this);
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

}


//    private Location getLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
//        }
//        return mLocManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//    }

