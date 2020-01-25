package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class ForecastEntityRestModel {
    @SerializedName("dt") public long dt;
    @SerializedName("main") public MainForecastRestModel mainForecastRestModel;
    @SerializedName("weather") public WeatherRestModel weatherRestModel;
    @SerializedName("clouds") public CloudsRestModel cloudsRestModel;
    @SerializedName("wind") public WindRestModel windRestModel;
    @SerializedName("sys") public SysForecastRestModel sys;
    @SerializedName("dt_txt") public String dt_txt;
}
