package ru.systempla.weatherapp.mvp.model.settings

import io.paperdb.Paper
import io.reactivex.Completable
import io.reactivex.Single

class PaperBasedSetting : ISettings {
    override fun saveCitySetting(setting: String) {
        Completable.fromAction { Paper.book("settings").write("setting", setting) }.subscribe()
    }

    override val citySetting: Single<String>
        get() = Single.fromCallable<String> { Paper.book("settings").read("setting") }

    override fun resetCitySetting() {
        Completable.fromAction { Paper.book("settings").write("setting", "gps") }.subscribe()
    }

    override fun savePermissionsSetting(setting: Int) {
        Completable.fromAction { Paper.book("settings").write("permissions", setting) }.subscribe()
    }

    override val permissionsSetting: Single<Int>
        get() = Single.fromCallable<Int> { Paper.book("settings").read("permissions") }

    override fun resetPermissionsSetting() {
        Completable.fromAction { Paper.book("settings").write("permissions", 1) }.subscribe()
    }
}