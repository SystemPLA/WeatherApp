package ru.systempla.weatherapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class BackgroundService extends IntentService {

	public static final String PARAM_NAME = "CITY";

	private String mString = null;

	public BackgroundService() {
		super("ru.systempla.weather_loader_service");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		/*new Thread(new Runnable() {
			@Override
			public void run() {
				//your code
			}
		});*/
		
		/* Get data from intent */
		if (intent != null) {			
			mString = intent.getStringExtra(PARAM_NAME);
			
			/* Checking data from intent and show it */
			if (mString != null)			
				Toast.makeText(getApplicationContext(), mString, Toast.LENGTH_LONG).show();
			stopSelf();
		}
		
		/* Invoke a parent method and return value */
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) { return null; }

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Local service was stopped", Toast.LENGTH_LONG).show();
		super.onDestroy();
	}

	@Override
	protected void onHandleIntent(@Nullable Intent intent) {

	}
}