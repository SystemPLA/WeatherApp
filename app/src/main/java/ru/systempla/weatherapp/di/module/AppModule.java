package ru.systempla.weatherapp.di.module;

import dagger.Module;
import dagger.Provides;
import ru.systempla.weatherapp.mvp.App;

@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    public App app(){
        return app;
    }
}
