package ru.systempla.weatherapp.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
public class CiceroneModule {

    Cicerone<Router> cicerone = Cicerone.create();

    @Singleton
    @Provides
    public NavigatorHolder navigatorHolder(){
        return cicerone.getNavigatorHolder();
    }

    @Singleton
    @Provides
    public Router router(){
        return cicerone.getRouter();
    }
}
