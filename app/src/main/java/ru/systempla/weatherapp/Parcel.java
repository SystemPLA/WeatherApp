package ru.systempla.weatherapp;

import java.io.Serializable;

public class Parcel implements Serializable {

    private final String cityName;
    private final int cityIndex;

    public int getCityIndex() {
        return cityIndex;
    }

    public String getCityName() {
        return cityName;
    }

    public Parcel(int cityIndex, String cityName) {
        this.cityIndex = cityIndex;
        this.cityName = cityName;
    }
}
