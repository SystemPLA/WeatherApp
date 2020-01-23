package ru.systempla.weatherapp.mvp;

import android.app.Application;

import io.paperdb.Paper;
import ru.systempla.weatherapp.di.AppComponent;
import ru.systempla.weatherapp.di.DaggerAppComponent;
import ru.systempla.weatherapp.di.module.AppModule;
import timber.log.Timber;

public class App extends Application {

    static private App instance;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Timber.plant(new Timber.DebugTree());
        Paper.init(this);

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
