package ru.systempla.weatherapp.mvp.model.settings

import io.reactivex.Single

interface ISettings {
    fun saveCitySetting(setting: String)
    val citySetting: Single<String>
    fun resetCitySetting()
    fun savePermissionsSetting(setting: Int)
    val permissionsSetting: Single<Int>
    fun resetPermissionsSetting()
}