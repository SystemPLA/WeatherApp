package ru.systempla.weatherapp.mvp.model.settings

import io.paperdb.Paper
import io.reactivex.Completable
import io.reactivex.Single

class PaperBasedSetting : ISettings {
    override fun saveSetting(setting: String) {
        Completable.fromAction { Paper.book("settings").write("setting", setting) }.subscribe()
    }

    override val setting: Single<String>
        get() = Single.fromCallable<String> { Paper.book("settings").read("setting") }

    override fun resetSetting() {
        Completable.fromAction { Paper.book("settings").write("setting", "gps") }.subscribe()
    }
}