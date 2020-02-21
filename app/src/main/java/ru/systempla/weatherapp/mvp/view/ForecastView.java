package ru.systempla.weatherapp.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface ForecastView extends MvpView {

    void init();
    void updateList();
    void setCity(String city);
    void showLoading();
    void hideLoading();

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void showMessage(String text);
}
