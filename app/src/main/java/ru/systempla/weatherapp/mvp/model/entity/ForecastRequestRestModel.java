package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

import java.util.List;

public class ForecastRequestRestModel {

    @SerializedName("cod")
    @Expose
    public String cod;
    @SerializedName("message")
    @Expose
    public Integer message;
    @SerializedName("cnt")
    @Expose
    public Integer cnt;
    @SerializedName("list")
    @Expose
    public List<ForecastEntityRestModel> list = null;
    @SerializedName("city")
    @Expose
    public CityRestModel city;

}
