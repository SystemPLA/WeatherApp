package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class UVIRequestRestModel {
    @SerializedName("lat") public float latitude;
    @SerializedName("lon") public float longitude;
    @SerializedName("date_iso") public String dateTime;
    @SerializedName("date") public int timeStamp;
    @SerializedName("value") public float uviValue;
}
