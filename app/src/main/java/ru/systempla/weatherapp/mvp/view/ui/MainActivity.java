package ru.systempla.weatherapp.mvp.view.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.Locale;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.systempla.weatherapp.R;
import ru.systempla.weatherapp.mvp.App;
import ru.systempla.weatherapp.mvp.presenter.MainPresenter;
import ru.systempla.weatherapp.mvp.view.MainView;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter presenter;

    @BindView(R.id.n_city_label)
    TextView cityLabel;

    @BindView(R.id.n_humidity_value)
    TextView humidityValue;

    @BindView(R.id.n_pressure_value)
    TextView pressureValue;

    @BindView(R.id.n_uv_value)
    TextView uvValue;

    @BindView(R.id.n_weather_desc_value)
    TextView weatherDescValue;

    @BindView(R.id.n_weather_image)
    ImageView weatherImage;

    @BindView(R.id.n_wind_speed_value)
    TextView windSpeedValue;

    @BindView(R.id.rl_loading)
    RelativeLayout loadingRelativeLayout;

    @BindView(R.id.n_temperature_value)
    TextView temperatureValue;

    @BindDrawable(R.drawable.ic_sun)
    Bitmap iconSun;

    @BindDrawable(R.drawable.ic_moon)
    Bitmap iconMoon;

    @BindDrawable(R.drawable.ic_snow)
    Bitmap iconSnow;

    @BindDrawable(R.drawable.ic_fog)
    Bitmap iconFog;

    @BindDrawable(R.drawable.ic_rain)
    Bitmap iconRain;

    @BindDrawable(R.drawable.ic_cloud)
    Bitmap iconCloud;

    @BindDrawable(R.drawable.ic_thunderstorm)
    Bitmap iconThunderstorm;

    @BindDrawable(R.drawable.ic_drizzle)
    Bitmap iconDrizzle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        App.getInstance().getAppComponent().inject(this);
        setContentView(R.layout.weather_data);
        ButterKnife.bind(this);
    }

    @ProvidePresenter
    public MainPresenter providePresenter() {
        MainPresenter presenter = new MainPresenter(AndroidSchedulers.mainThread(), Schedulers.io());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    public void showLoading() {
        loadingRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingRelativeLayout.setVisibility(View.GONE);
    }


    @Override
    public void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setCityName(String name, String country) {
        String cityText = name.toUpperCase() + ", " + country;
        cityLabel.setText(cityText);
    }

    @Override
    public void setWeatherDescription(String description) {
        weatherDescValue.setText(description.toUpperCase());
    }

    @Override
    public void setUVIndex(int uvIndex) {
        uvValue.setText(uvIndex);
    }

    @Override
    public void setPressure(float pressure) {
        pressureValue.setText(String.format("%s hPa", pressure));
    }

    @Override
    public void setHumidity(float humidity) {
        humidityValue.setText(String.format("%s %%", humidity));
    }

    @Override
    public void setWindSpeed(float speed) {
        windSpeedValue.setText(String.format("%s mps", speed));
    }

    @Override
    public void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        Bitmap icon = null;

        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = iconSun;
            } else {
                icon = iconMoon;
            }
        } else {
            switch (id) {
                case 2: {
                    icon = iconThunderstorm;
                    break;
                }
                case 3: {
                    icon = iconDrizzle;
                    break;
                }
                case 5: {
                    icon = iconRain;
                    break;
                }
                case 6: {
                    icon = iconSnow;
                    break;
                }
                case 7: {
                    icon = iconFog;
                    break;
                }
                case 8: {
                    icon = iconCloud;
                    break;
                }
            }
        }
        weatherImage.setImageBitmap(icon);
    }

    @Override
    public void setCurrentTemperature(float temp) {
        String currentTextText = String.format(Locale.getDefault(), "%.2f", temp) + " \u2103";
        temperatureValue.setText(currentTextText);
    }
}