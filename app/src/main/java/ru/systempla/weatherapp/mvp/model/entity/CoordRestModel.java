package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class CoordRestModel {
    @SerializedName("lon") public float lon;
    @SerializedName("lat") public float lat;
}
