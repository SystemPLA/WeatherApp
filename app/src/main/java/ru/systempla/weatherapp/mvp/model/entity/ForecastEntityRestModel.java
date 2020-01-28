package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("clouds")
    @Expose
    public CloudsRestModel clouds;
    @SerializedName("wind")
    @Expose
    public WindRestModel wind;
    @SerializedName("snow")
    @Expose
    public SnowRestModel snow;
    @SerializedName("sys")
    @Expose
    public SysForecastRestModel sys;
    @SerializedName("dt_txt")
    @Expose
    public String dtTxt;


}
