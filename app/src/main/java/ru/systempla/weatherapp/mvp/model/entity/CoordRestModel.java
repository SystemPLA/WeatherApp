package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoordRestModel {

    @SerializedName("lat")
    @Expose
    public Double lat;

    @SerializedName("lon")
    @Expose
    public Double lon;
}
