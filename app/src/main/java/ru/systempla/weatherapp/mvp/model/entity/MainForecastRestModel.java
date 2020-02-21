package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MainForecastRestModel {

    @SerializedName("temp")
    @Expose
    public Double temp;

    @SerializedName("feels_like")
    @Expose
    public Double feelsLike;

    @SerializedName("temp_min")
    @Expose
    public Double tempMin;

    @SerializedName("temp_max")
    @Expose
    public Double tempMax;

    @SerializedName("pressure")
    @Expose
    public Integer pressure;

    @SerializedName("sea_level")
    @Expose
    public Integer seaLevel;

    @SerializedName("grnd_level")
    @Expose
    public Integer grndLevel;

    @SerializedName("humidity")
    @Expose
    public Integer humidity;

    @SerializedName("temp_kf")
    @Expose
    public Double tempKf;
}
