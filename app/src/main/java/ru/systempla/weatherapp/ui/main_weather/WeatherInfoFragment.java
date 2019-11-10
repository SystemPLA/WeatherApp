package ru.systempla.weatherapp.ui.main_weather;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import ru.systempla.weatherapp.service.BoundService;
import ru.systempla.weatherapp.ui.history.HistoryActivity;
import ru.systempla.weatherapp.ui.parcel.Parcel;
import ru.systempla.weatherapp.R;

import static android.content.Context.BIND_AUTO_CREATE;

public class WeatherInfoFragment extends Fragment {

    private boolean isBind = false;
    private BoundService.ServiceBinder mService = null;
    private MyServiceConnection mConnection = null;

    private final static String LOG_TAG = "WeatherInfoFragment";

    private Typeface weatherFont;
    private final Handler handler = new Handler();
    private TextView cityNameView;
    private TextView updatedTextView;
    private TextView weatherIconTextView;
    private TextView detailsTextView;
    private TextView pressureLabel;
    private TextView pressureValue;
    private TextView windLabel;
    private TextView windValue;
    private TextView humidityLabel;
    private TextView humidityValue;
    private TextView temperatureValue;

    private Button historyButton;
    private Parcel parcel;

    private static final String PARCEL = "parcel";

    public static WeatherInfoFragment create(Parcel parcel) {
        WeatherInfoFragment fragment = new WeatherInfoFragment();

        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        fragment.setArguments(args);
        return fragment;
    }

    public Parcel getParcel() {
        return (Parcel) getArguments().getSerializable(PARCEL);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_weather, container, false);

        mConnection = new MyServiceConnection();

        initViews(layout);
        initFonts();

        parcel = getParcel();

        applySettings(parcel);

        cityNameView.setText(parcel.getCityName());

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isBind) {
            Intent intent = new Intent(getActivity(), BoundService.class);
            getActivity().bindService(intent, mConnection, BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onStop() {
        if (isBind) {
            getActivity().unbindService(mConnection);
            isBind = false;
        }
        super.onStop();
    }

    private int getVisibilityInt(boolean flag){
        if (flag) {
            return 0;
        } else {
            return 8;
        }
    }

    private void initViews(View layout) {
        cityNameView = layout.findViewById(R.id.city_field);

        updatedTextView = layout.findViewById(R.id.updated_field);
        weatherIconTextView = layout.findViewById(R.id.weather_icon);

        detailsTextView = layout.findViewById(R.id.details_field);

        pressureLabel = layout.findViewById(R.id.pressure_label);
        pressureValue = layout.findViewById(R.id.pressure_value);

        windLabel = layout.findViewById(R.id.wind_label);
        windValue = layout.findViewById(R.id.wind_value);

        humidityLabel = layout.findViewById(R.id.humidity_label);
        humidityValue = layout.findViewById(R.id.humidity_value);

        temperatureValue = layout.findViewById(R.id.temperature_value);

        historyButton = layout.findViewById(R.id.history_bt);
    }

    private void applySettings(Parcel parcel) {
        pressureLabel.setVisibility(getVisibilityInt(parcel.getSettingsParcel().isPressureFlag()));
        pressureValue.setVisibility(getVisibilityInt(parcel.getSettingsParcel().isPressureFlag()));

        windLabel.setVisibility(getVisibilityInt(parcel.getSettingsParcel().isWindFlag()));
        windValue.setVisibility(getVisibilityInt(parcel.getSettingsParcel().isWindFlag()));

        humidityLabel.setVisibility(getVisibilityInt(parcel.getSettingsParcel().isHumidityFlag()));
        humidityValue.setVisibility(getVisibilityInt(parcel.getSettingsParcel().isHumidityFlag()));
    }

    private void initFonts() {
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        weatherIconTextView.setTypeface(weatherFont);
    }

    private void updateWeatherData(final String city) {
        new Thread() {
            @Override
            public void run() {
                final JSONObject jsonObject = mService.getService().getWeatherUpdate(city);
                if(jsonObject == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(), R.string.place_not_found,
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            renderWeather(jsonObject);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject jsonObject) {
        Log.d(LOG_TAG, "json: " + jsonObject.toString());
        try {
            JSONObject details = jsonObject.getJSONArray("weather").getJSONObject(0);
            JSONObject main = jsonObject.getJSONObject("main");
            JSONObject wind = jsonObject.getJSONObject("wind");

            setPlaceName(jsonObject);
            setCurrentTemp(main);
            setWeatherIcon(details.getInt("id"),
                    jsonObject.getJSONObject("sys").getLong("sunrise") * 1000,
                    jsonObject.getJSONObject("sys").getLong("sunset") * 1000);
            setUpdatedText(jsonObject);
            setDetails(wind, main, details);
        } catch (Exception exc) {
            exc.printStackTrace();
            Log.e(LOG_TAG, "One or more fields not found in the JSON data");
        }
    }

    private void setPlaceName(JSONObject jsonObject) throws JSONException {
        String cityText = jsonObject.getString("name").toUpperCase() + ", "
                + jsonObject.getJSONObject("sys").getString("country");
        cityNameView.setText(cityText);
    }

    private void setCurrentTemp(JSONObject main) throws JSONException {
        String currentTextText = String.format(Locale.getDefault(), "%.2f",
                main.getDouble("temp")) + "\u2103";
        temperatureValue.setText(currentTextText);
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";

        if(actualId == 800) {
            long currentTime = new Date().getTime();
            if(currentTime >= sunrise && currentTime < sunset) {
                icon = "\u2600";
                //icon = getString(R.string.weather_sunny);
            } else {
                icon = getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2: {
                    icon = getString(R.string.weather_thunder);
                    break;
                }
                case 3: {
                    icon = getString(R.string.weather_drizzle);
                    break;
                }
                case 5: {
                    icon = getString(R.string.weather_rainy);
                    break;
                }
                case 6: {
                    icon = getString(R.string.weather_snowy);
                    break;
                }
                case 7: {
                    icon = getString(R.string.weather_foggy);
                    break;
                }
                case 8: {
                    icon = "\u2601";
                    // icon = getString(R.string.weather_cloudy);
                    break;
                }
            }
        }
        weatherIconTextView.setText(icon);
    }

    private void setUpdatedText(JSONObject jsonObject) throws JSONException {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String updateOn = dateFormat.format(new Date(jsonObject.getLong("dt") * 1000));
        String updatedText = "Last update: " + updateOn;
        updatedTextView.setText(updatedText);
    }

    private void setDetails(JSONObject wind, JSONObject main, JSONObject details) throws JSONException {
        pressureValue.setText(main.getString("pressure") + "hPa");
        humidityValue.setText(main.getString("humidity") + "%");
        windValue.setText(wind.getString("speed") + "mps");
        detailsTextView.setText(details.getString("description").toUpperCase());
    }

    private class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /* Get service object (binder) */
            mService = (BoundService.ServiceBinder) service;
            isBind = mService != null;
            updateWeatherData(parcel.getCityName());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
            mService = null;
        }

    }
}
