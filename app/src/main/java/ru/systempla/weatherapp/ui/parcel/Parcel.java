package ru.systempla.weatherapp.ui.parcel;

import java.io.Serializable;

import ru.systempla.weatherapp.service.BoundService;

public class Parcel implements Serializable {


    private BoundService.ServiceBinder mService;
    private final SettingsParcel settingsParcel;
    private final String cityName;

    public Parcel() {
        this.mService = null;
        this.cityName = "";
        this.settingsParcel = new SettingsParcel();
    }

    public String getCityName() {
        return cityName;
    }

    public SettingsParcel getSettingsParcel() {
        return settingsParcel;
    }

    public BoundService.ServiceBinder getmService() {
        return mService;
    }

    public Parcel(String cityName, SettingsParcel settingsParcel, BoundService.ServiceBinder mService) {
        this.settingsParcel = settingsParcel;
        this.cityName = cityName;
        this.mService = mService;
    }
}
