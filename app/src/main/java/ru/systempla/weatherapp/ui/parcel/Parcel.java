package ru.systempla.weatherapp.ui.parcel;

import java.io.Serializable;

public class Parcel implements Serializable {

    private final SettingsParcel settingsParcel;
    private final String cityName;

    public Parcel() {
        this.cityName = "";
        this.settingsParcel = new SettingsParcel();
    }

    public String getCityName() {
        return cityName;
    }

    public SettingsParcel getSettingsParcel() {
        return settingsParcel;
    }

    public Parcel(String cityName, SettingsParcel settingsParcel) {
        this.settingsParcel = settingsParcel;
        this.cityName = cityName;
    }
}
