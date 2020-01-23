package ru.systempla.weatherapp.mvp.model.settings;

import io.reactivex.Single;

public interface ISettings {

    void saveSetting(String setting);
    Single<String> getSetting();
    void resetSetting();
}
