package ru.systempla.weatherapp.mvp.model.location;

import io.reactivex.Single;

public interface ILocationGetter {
    Single<String> getCity();
}
