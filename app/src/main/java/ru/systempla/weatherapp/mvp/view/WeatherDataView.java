package ru.systempla.weatherapp.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface WeatherDataView extends MvpView {

    void showMessage(String text);
    void setCityName(String name);
    void setWeatherDescription(String description);
    void setUVIndex (float uvIndex);
    void setPressure(float pressure);
    void setHumidity(float humidity);
    void setWindSpeed(float speed);
    void setWeatherIcon(int actualId, long sunrise, long sunset);
    void setCurrentTemperature(float temp);
    void checkGeolocationPermission();
    void showLoading();
    void hideLoading();
}
