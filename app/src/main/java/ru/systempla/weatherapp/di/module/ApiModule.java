package ru.systempla.weatherapp.di.module;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.systempla.weatherapp.mvp.model.api.IDataSource;

@Module
public class ApiModule {

    @Provides
    public IDataSource api() {
        return new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IDataSource.class);
    }
}