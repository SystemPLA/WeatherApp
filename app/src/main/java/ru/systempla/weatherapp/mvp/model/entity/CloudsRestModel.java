package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CloudsRestModel {
    @SerializedName("all")
    @Expose
    public Integer all;
}
