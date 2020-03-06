package ru.systempla.weatherapp.mvp.view.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.Date;
import java.util.Locale;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.systempla.weatherapp.R;
import ru.systempla.weatherapp.mvp.App;
import ru.systempla.weatherapp.mvp.presenter.WeatherDataPresenter;
import ru.systempla.weatherapp.mvp.view.WeatherDataView;
import timber.log.Timber;

@SuppressWarnings({"unused","ConstantConditions"})
public class WeatherDataFragment extends MvpAppCompatFragment implements WeatherDataView {

    private Unbinder unbinder;
    private DrawerLayout drawer;

    public static WeatherDataFragment newInstance(){
        return new WeatherDataFragment();
    }

    @InjectPresenter
    WeatherDataPresenter presenter;

    @BindString(R.string.language)
    String language;

    @BindString(R.string.wind_speed)
    String speedUnit;

    @BindString(R.string.pressure_unit)
    String pressureUnit;

    @BindView(R.id.loading)
    RelativeLayout loadingRelativeLayout;

    @BindView(R.id.optionHitBoxExtender)
    View popupMenuButton;

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

    @BindView(R.id.n_temperature_value)
    TextView temperatureValue;

    @BindView(R.id.drawer_button)
    ImageView drawerButton;

    @BindDrawable(R.drawable.ic_sun)
    Drawable iconSun;

    @BindDrawable(R.drawable.ic_moon)
    Drawable iconMoon;

    @BindDrawable(R.drawable.ic_snow)
    Drawable iconSnow;

    @BindDrawable(R.drawable.ic_fog)
    Drawable iconFog;

    @BindDrawable(R.drawable.ic_rain)
    Drawable iconRain;

    @BindDrawable(R.drawable.ic_cloud)
    Drawable iconCloud;

    @BindDrawable(R.drawable.ic_thunderstorm)
    Drawable iconThunderstorm;

    @BindDrawable(R.drawable.ic_drizzle)
    Drawable iconDrizzle;

    @SuppressWarnings("WeakerAccess")
    @OnClick(R.id.optionHitBoxExtender)
    public void showMenu(View v){
        showPopupMenu(v);
    }

    @SuppressWarnings("WeakerAccess")
    @OnClick(R.id.drawer_button)
    public void showDrawer(){drawer.openDrawer(GravityCompat.START);}

    private void showPopupMenu(View v){
        PopupMenu popupMenu = new PopupMenu(this.getContext(), v);
        popupMenu.inflate(R.menu.location_change_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.change_city:
                    showInputDialog();
                    return true;
                case R.id.change_to_gps:
                    presenter.setSetting("gps");
                    presenter.loadAccordingToSettings();
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setLanguage(language);
        presenter.checkSettings();
        presenter.loadAccordingToSettings();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable android.view.ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_data, container, false);
        unbinder = ButterKnife.bind(this, view);
        App.getInstance().getAppComponent().inject(this);
        drawer = getActivity().findViewById(R.id.main_drawer_layout);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.location_change_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(R.string.change_city);

        final EditText input = new EditText(this.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            presenter.setSetting(input.getText().toString());
            presenter.loadAccordingToSettings();
        });
        builder.show();
    }

    @SuppressWarnings("WeakerAccess")
    @ProvidePresenter
    public WeatherDataPresenter providePresenter() {
        WeatherDataPresenter presenter = new WeatherDataPresenter(AndroidSchedulers.mainThread(), Schedulers.io());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(this.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setCityName(String name) {
        cityLabel.setText(name);
    }

    @Override
    public void setWeatherDescription(String description) {
        weatherDescValue.setText(description.toLowerCase());
    }

    @Override
    public void setUVIndex(float uvIndex) {
        String currentTextText = String.format(Locale.getDefault(), "%.2f", uvIndex);
        uvValue.setText(currentTextText);
    }

    @Override
    public void setPressure(float pressure) {
        if (language.equals("en")) {
            pressureValue.setText(String.format("%s " + pressureUnit, pressure));
        }
        if (language.equals("ru")) {
            double newPressure = pressure * 0.75006375541921;
            pressureValue.setText(String.format("%.0f " + pressureUnit, newPressure));

        }

    }

    @Override
    public void setHumidity(float humidity) {
        humidityValue.setText(String.format("%s %%", humidity));
    }

    @Override
    public void setWindSpeed(double speed) {
        windSpeedValue.setText(String.format("%s "+speedUnit, speed));
    }

    @Override
    public void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        Drawable icon = null;

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
        weatherImage.setImageDrawable(icon);
    }

    @Override
    public void setCurrentTemperature(float temp) {
        String currentTextText = String.format(Locale.getDefault(), "%.0f", temp);
        temperatureValue.setText(currentTextText);
    }

    @Override
    public void showLoading() {
        loadingRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingRelativeLayout.setVisibility(View.GONE);
    }
}
