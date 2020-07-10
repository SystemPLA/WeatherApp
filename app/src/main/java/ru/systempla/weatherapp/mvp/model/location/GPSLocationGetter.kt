package ru.systempla.weatherapp.mvp.model.location

import android.annotation.SuppressLint
import android.location.*
import io.reactivex.Single
import ru.systempla.weatherapp.mvp.App
import ru.systempla.weatherapp.mvp.model.final_groups.FinalGroups.messages
import java.io.IOException

class GPSLocationGetter(private val locManager: LocationManager, private val locListener: LocationListener) : ILocationGetter {
    override val city: Single<String>
        @SuppressLint("MissingPermission")
        get() = Single.fromCallable { getAddressByLoc(locManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)) }

    private fun getAddressByLoc(loc: Location?): String? {
        if (loc == null) return messages.MSG_NO_DATA
        val geo = Geocoder(App.instance)
        val list: List<Address>
        list = try {
            geo.getFromLocation(loc.latitude, loc.longitude, 1)
        } catch (e: IOException) {
            e.printStackTrace()
            return e.localizedMessage
        }
        return if (list.isEmpty()) {
            messages.MSG_NO_DATA
        } else {
            val address = list[0]
            address.locality
        }
    }

    @SuppressLint("MissingPermission")
    override fun startUpdatingLocation() {
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000L, 1.0f, locListener)
    }

    override fun stopUpdatingLocation() {
        if (locListener != null) locManager.removeUpdates(locListener)
    }

}