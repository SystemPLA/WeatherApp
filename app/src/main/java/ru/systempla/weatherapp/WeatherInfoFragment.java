package ru.systempla.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class WeatherInfoFragment extends Fragment {

    public static final String PARCEL = "parcel";

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

        TextView cityNameView = layout.findViewById(R.id.textView);

//        TextView temperatureLabel = layout.findViewById(R.id.temperature_label);
//        TextView temperatureValue = layout.findViewById(R.id.temperature_value);

        TextView pressureLabel = layout.findViewById(R.id.pressure_label);
        TextView pressureValue = layout.findViewById(R.id.pressure_value);

        TextView windLabel = layout.findViewById(R.id.wind_label);
        TextView windValue = layout.findViewById(R.id.wind_value);

        TextView humidityLabel = layout.findViewById(R.id.humidity_label);
        TextView humidityValue = layout.findViewById(R.id.humidity_value);

        Parcel parcel = getParcel();

        cityNameView.setText(parcel.getCityName());

        pressureLabel.setVisibility(getVisiabolityInt(parcel.getSettingsParcel().isPressureFlag()));
        pressureValue.setVisibility(getVisiabolityInt(parcel.getSettingsParcel().isPressureFlag()));

        windLabel.setVisibility(getVisiabolityInt(parcel.getSettingsParcel().isWindFlag()));
        windValue.setVisibility(getVisiabolityInt(parcel.getSettingsParcel().isWindFlag()));

        humidityLabel.setVisibility(getVisiabolityInt(parcel.getSettingsParcel().isHumidityFlag()));
        humidityValue.setVisibility(getVisiabolityInt(parcel.getSettingsParcel().isHumidityFlag()));

        return layout;
    }

    private int getVisiabolityInt(boolean flag){
        if (flag) {
            return 0;
        } else {
            return 8;
        }
    }
}
