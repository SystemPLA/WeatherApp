package ru.systempla.weatherapp.ui.main_weather;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import ru.systempla.weatherapp.rest.entities.WeatherRequestRestModel;
import ru.systempla.weatherapp.service.BoundService;
import ru.systempla.weatherapp.ui.history.HistoryActivity;
import ru.systempla.weatherapp.ui.parcel.Parcel;
import ru.systempla.weatherapp.R;

import static android.content.Context.BIND_AUTO_CREATE;

public class WeatherInfoFragment extends Fragment {

    private boolean isBind = false;
    private BoundService.ServiceBinder mService = null;
    private MyServiceConnection mConnection = null;

    private final Handler handler = new Handler();
    private Typeface weatherFont;
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
                final WeatherRequestRestModel model = mService.getService().getWeatherUpdate(city);
                if(model == null) {
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
                            renderWeather(model);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(WeatherRequestRestModel model) {
        setPlaceName(model.name, model.sys.country);
        setDetails(model.weather[0].description, model.main.humidity, model.main.pressure, model.wind.speed);
        setCurrentTemp(model.main.temp);
        setUpdatedText(model.dt);
        setWeatherIcon(model.weather[0].id,
                model.sys.sunrise * 1000,
                model.sys.sunset * 1000);
    }

    private void setPlaceName(String name, String country) {
        String cityText = name.toUpperCase() + ", " + country;
        cityNameView.setText(cityText);
    }

    private void setDetails(String description, float humidity, float pressure, float speed) {
        pressureValue.setText(pressure + "hPa");
        humidityValue.setText(humidity + "%");
        windValue.setText(speed + "mps");
        detailsTextView.setText(description.toUpperCase());
    }

    private void setCurrentTemp(float temp) {
        String currentTextText = String.format(Locale.getDefault(), "%.2f", temp) + "\u2103";
        temperatureValue.setText(currentTextText);
    }

    private void setUpdatedText(long dt) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String updateOn = dateFormat.format(new Date(dt * 1000));
        String updatedText = "Last update: " + updateOn;
        updatedTextView.setText(updatedText);
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";

        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
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
