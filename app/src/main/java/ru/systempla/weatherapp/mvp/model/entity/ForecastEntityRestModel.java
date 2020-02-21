package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class ForecastEntityRestModel {

    @SerializedName("dt")
    @Expose
    public Integer dt;

    @SerializedName("main")
    @Expose
    public MainRestModel main;

    @SerializedName("weather")
    @Expose
    public List<WeatherRestModel> weather = null;

    @SuppressWarnings("unused")
    @SerializedName("clouds")
    @Expose
    public CloudsRestModel clouds;

    @SuppressWarnings("unused")
    @SerializedName("wind")
    @Expose
    public WindRestModel wind;

    @SuppressWarnings("unused")
    @SerializedName("snow")
    @Expose
    public SnowRestModel snow;

    @SuppressWarnings("unused")
    @SerializedName("sys")
    @Expose
    public SysForecastRestModel sys;

    @SerializedName("dt_txt")
    @Expose
    public String dtTxt;


}
