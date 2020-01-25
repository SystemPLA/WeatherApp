package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class CityRestModel {
    @SerializedName("id") public int id;
    @SerializedName("name") public String name;
    @SerializedName("coord") public CoordRestModel coord;
    @SerializedName("country") public String country;
}
