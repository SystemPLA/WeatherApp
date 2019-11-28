package ru.systempla.weatherapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;
import java.io.Serializable;

import ru.systempla.weatherapp.rest.OpenWeatherRepo;
import ru.systempla.weatherapp.rest.entities.WeatherRequestRestModel;

public class BoundService extends Service implements Serializable {

	private static final String OPEN_WEATHER_API_KEY = "bf47d8733b57a7fad0801641fe3dc5cc";

	private final IBinder mBinder = new ServiceBinder();
	
	/**
	 * Return the communication channel to the service.  May return null if
	 * clients can not bind to the service.
	 * */
	@Override
	public IBinder onBind(Intent intent) { return mBinder; }
	
	/** 
	 * Method for clients.  
	 * */

	public WeatherRequestRestModel getWeatherUpdate(String city) {
		try {
			return OpenWeatherRepo.getSingleton().getAPI().loadWeather(city + ",ru",
					OPEN_WEATHER_API_KEY, "metric").execute().body();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public class ServiceBinder extends Binder implements Serializable {
		public BoundService getService() {
			return BoundService.this;
		}
	}

}