package ru.systempla.weatherapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class BoundService extends Service implements Serializable {

	private static final String OPEN_WEATHER_API_KEY = "bf47d8733b57a7fad0801641fe3dc5cc";
	private static final String OPEN_WEATHER_API_URL =
			"https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
	private static final String KEY = "x-api-key";
	private static final String RESPONSE = "cod";
	private static final int ALL_GOOD = 200;
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

	public JSONObject getWeatherUpdate(String city) {
		try {
			URL url = new URL(String.format(OPEN_WEATHER_API_URL, city));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.addRequestProperty(KEY, OPEN_WEATHER_API_KEY);

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder rawData = new StringBuilder(1024);
			String tempVariable;

			while ((tempVariable = reader.readLine()) != null) {
				rawData.append(tempVariable).append("\n");
			}

			reader.close();

			JSONObject jsonObject = new JSONObject(rawData.toString());
			if(jsonObject.getInt(RESPONSE) != ALL_GOOD) {
				return null;
			} else {
				return jsonObject;
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}

	public class ServiceBinder extends Binder implements Serializable {
		public BoundService getService() {
			return BoundService.this;
		}
	}

}