package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class MainForecastRestModel {
    @SerializedName("temp") public float temp;
    @SerializedName("temp_min") public float temp_min;
    @SerializedName("temp_max") public float temp_max;
    @SerializedName("pressure") public float pressure;
    @SerializedName("sea_level") public float sea_level;
    @SerializedName("grnd_level") public float grnd_level;
    @SerializedName("humidity") public int humidity;
    @SerializedName("temp_kf") public float temp_kf;
}
