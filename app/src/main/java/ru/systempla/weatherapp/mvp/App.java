package ru.systempla.weatherapp.mvp;

import android.app.Application;

import ru.systempla.weatherapp.di.AppComponent;
import timber.log.Timber;

public class App extends Application {

    static private App instance;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

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
