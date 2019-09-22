package ru.systempla.weatherapp;

import java.io.Serializable;

public class Parcel implements Serializable {

    private final SettingsParcel settingsParcel;
    private final String cityName;
    private final int cityIndex;

    public int getCityIndex() {
        return cityIndex;
    }

    public String getCityName() {
        return cityName;
    }

    public SettingsParcel getSettingsParcel() {
        return settingsParcel;
    }

    public Parcel(int cityIndex, String cityName, SettingsParcel settingsParcel) {
        this.settingsParcel = settingsParcel;
        this.cityName = cityName;
        this.cityIndex = cityIndex;
    }
}
