package ru.systempla.weatherapp.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import ru.systempla.weatherapp.R;
import ru.systempla.weatherapp.ui.parcel.Parcel;
import ru.systempla.weatherapp.ui.parcel.SettingsParcel;

public class SettingsFragment extends Fragment {

    private static final String SETTINGS = "parcel";

    private CheckBox pressureCB;
    private CheckBox windSpeedCB;
    private CheckBox humidityCB;
    private Button applySettingsButton;
    private SettingsChangeListener settingsChangeListener;

    public static SettingsFragment create(Parcel parcel) {
        SettingsFragment fragment = new SettingsFragment();

        Bundle args = new Bundle();
        args.putSerializable(SETTINGS, parcel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        settingsChangeListener = (SettingsChangeListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        settingsChangeListener = null;
    }

    private Parcel getParcel() {
        return (Parcel) Objects.requireNonNull(getArguments()).getSerializable(SETTINGS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_settings, container, false);

        Parcel parcel = getParcel();

        initView(layout);
        applySettings(parcel);

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        applySettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsChangeListener.onSettingsChange(new SettingsParcel(pressureCB.isChecked(),
                        windSpeedCB.isChecked(), humidityCB.isChecked()));
            }
        });
    }

    private void initView(View layout) {
        pressureCB = layout.findViewById(R.id.cb_pressure);
        windSpeedCB = layout.findViewById(R.id.cb_wind);
        humidityCB = layout.findViewById(R.id.cb_humidity);
        applySettingsButton = layout.findViewById(R.id.ok_button);
    }

    private void applySettings(Parcel parcel) {
        pressureCB.setChecked(parcel.getSettingsParcel().isPressureFlag());
        windSpeedCB.setChecked(parcel.getSettingsParcel().isWindFlag());
        humidityCB.setChecked(parcel.getSettingsParcel().isHumidityFlag());
    }
}
