package ru.systempla.weatherapp.mvp.presenter;

import io.reactivex.subjects.PublishSubject;
import ru.systempla.weatherapp.mvp.view.list.ForecastItemView;

public interface IForecastListPresenter {
    void bind(ForecastItemView view);
    int getCount();
    PublishSubject<ForecastItemView> getClickSubject();
}