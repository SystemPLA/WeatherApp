package ru.systempla.weatherapp.di.module

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class CiceroneModule {
    private val cicerone = Cicerone.create()

    @Singleton
    @Provides
    fun navigatorHolder(): NavigatorHolder = cicerone.navigatorHolder

    @Singleton
    @Provides
    fun router(): Router = cicerone.router
}