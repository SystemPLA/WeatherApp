package ru.systempla.weatherapp.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import moxy.viewstate.strategy.OneExecutionStateStrategy;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface WeatherDataView extends MvpView {

    void setCityName(String name);
    void setWeatherDescription(String description);
    void setUVIndex (float uvIndex);
    void setPressure(float pressure);
    void setHumidity(float humidity);
    void setWindSpeed(double speed);
    void setWeatherIcon(int actualId, long sunrise, long sunset);
    void setCurrentTemperature(float temp);
    void showLoading();
    void hideLoading();

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void showMessage(String text);
}
