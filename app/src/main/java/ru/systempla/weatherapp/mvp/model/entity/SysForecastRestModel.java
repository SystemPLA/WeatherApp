package ru.systempla.weatherapp.mvp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SysForecastRestModel {
    @SerializedName("pod")
    @Expose
    public String pod;
}
