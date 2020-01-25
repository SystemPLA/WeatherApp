package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastRequestRestModel {
    @SerializedName("cod") public int cod;
    @SerializedName("message") public float message;
    @SerializedName("cnt") public int cnt;
    @SerializedName("list") public List<ForecastEntityRestModel> forecasts;
    @SerializedName("city") public CityRestModel cityRestModel;
}
