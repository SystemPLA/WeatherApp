package ru.systempla.weatherapp.mvp

import android.app.Application
import io.paperdb.Paper
import ru.systempla.weatherapp.di.AppComponent
import ru.systempla.weatherapp.di.DaggerAppComponent
import timber.log.Timber

class App: Application() {
    companion object {
        lateinit var instance: App private set
    }

    lateinit var appComponent: AppComponent private set

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Paper.init(this)
        instance = this
        appComponent = DaggerAppComponent.builder()
                .build()
    }
}