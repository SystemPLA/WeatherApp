package ru.systempla.weatherapp.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void showLoading();
    void hideLoading();
    void showMessage(String text);
    void setCityName(String name, String country);
    void setWeatherDescription(String description);
    void setUVIndex (int uvIndex);
    void setPressure(float pressure);
    void setHumidity(float humidity);
    void setWindSpeed(float speed);
    void setWeatherIcon(int actualId, long sunrise, long sunset);
    void setCurrentTemperature(float temp);
    void getPermission(String permission);
    void getPermission(String ... permissions);


}
