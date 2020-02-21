package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SysRestModel {

    @SerializedName("type") public int type;
    @SerializedName("id") public int id;
    @SerializedName("message") public float message;
    @SerializedName("country") public String country;
    @SerializedName("sunrise") public long sunrise;
    @SerializedName("sunset") public long sunset;
}
