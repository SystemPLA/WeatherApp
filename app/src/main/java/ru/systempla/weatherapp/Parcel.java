package ru.systempla.weatherapp;

import java.io.Serializable;

public class Parcel implements Serializable {
    public String text;
    public boolean pressureFlag;
    public boolean windFlag;
    public boolean humidityFlag;
}
