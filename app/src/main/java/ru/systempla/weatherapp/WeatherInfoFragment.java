package ru.systempla.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        TextView pressureLabel = layout.findViewById(R.id.pressure_label);
        TextView pressureValue = layout.findViewById(R.id.pressure_value);

        TextView windLabel = layout.findViewById(R.id.wind_label);
        TextView windValue = layout.findViewById(R.id.wind_value);

        TextView humidityLabel = layout.findViewById(R.id.humidity_label);
        TextView humidityValue = layout.findViewById(R.id.humidity_value);

        Parcel parcel = getParcel();

        cityNameView.setText(parcel.getCityName());

        pressureLabel.setVisibility(getVisiabilityInt(parcel.getSettingsParcel().isPressureFlag()));
        pressureValue.setVisibility(getVisiabilityInt(parcel.getSettingsParcel().isPressureFlag()));

        windLabel.setVisibility(getVisiabilityInt(parcel.getSettingsParcel().isWindFlag()));
        windValue.setVisibility(getVisiabilityInt(parcel.getSettingsParcel().isWindFlag()));

        humidityLabel.setVisibility(getVisiabilityInt(parcel.getSettingsParcel().isHumidityFlag()));
        humidityValue.setVisibility(getVisiabilityInt(parcel.getSettingsParcel().isHumidityFlag()));

        Button historyButton = layout.findViewById(R.id.history_bt);
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

    private int getVisiabilityInt(boolean flag){
        if (flag) {
            return 0;
        } else {
            return 8;
        }
    }
}
