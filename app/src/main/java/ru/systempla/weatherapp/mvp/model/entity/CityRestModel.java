package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CityRestModel {

    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("coord")
    @Expose
    public CoordRestModel coord;

    @SerializedName("country")
    @Expose
    public String country;

    @SerializedName("population")
    @Expose
    public Integer population;

    @SerializedName("timezone")
    @Expose
    public Integer timezone;

    @SerializedName("sunrise")
    @Expose
    public Integer sunrise;

    @SerializedName("sunset")
    @Expose
    public Integer sunset;
}
