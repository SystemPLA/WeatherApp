package ru.systempla.weatherapp.mvp.view.list;

public interface ForecastItemView {

    int getPos();
    void setDateTime(long dt);
    void setTemperature(float temp);
    void setWeatherDescription(String description);
    void setWeatherIcon(int actualId, long sunrise, long sunset);
}
