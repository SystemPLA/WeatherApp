package ru.systempla.weatherapp.mvp.model.settings

import io.reactivex.Single

interface ISettings {
    fun saveSetting(setting: String)
    val setting: Single<String>
    fun resetSetting()
}