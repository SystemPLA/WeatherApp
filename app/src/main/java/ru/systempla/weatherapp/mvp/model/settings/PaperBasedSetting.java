package ru.systempla.weatherapp.mvp.model.settings;

import io.paperdb.Paper;
import io.reactivex.Completable;
import io.reactivex.Single;

public class PaperBasedSetting implements ISettings {

    @Override
    public void saveSetting(String setting) {
        Completable.fromAction(()-> Paper.book("settings").write("setting",setting)).subscribe();
    }

    @Override
    public Single<String> getSetting() {
        return Single.fromCallable(()->Paper.book("settings").read("setting"));
    }

    @Override
    public void resetSetting() {
        Completable.fromAction(()->Paper.book("settings").write("setting","gps")).subscribe();
    }
}
