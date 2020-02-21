package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class WindRestModel {

    @SerializedName("speed")
    @Expose
    public Double speed;

    @SerializedName("deg")
    @Expose
    public Integer deg;
}
