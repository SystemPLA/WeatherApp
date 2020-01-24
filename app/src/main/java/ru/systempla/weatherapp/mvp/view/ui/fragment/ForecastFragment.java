package ru.systempla.weatherapp.mvp.view.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.ButterKnife;
import moxy.MvpAppCompatFragment;
import ru.systempla.weatherapp.R;
import ru.systempla.weatherapp.mvp.App;

public class ForecastFragment extends MvpAppCompatFragment {

    public static ForecastFragment newInstance(){
        return new ForecastFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forcast, container, false);
        ButterKnife.bind(this, view);
        App.getInstance().getAppComponent().inject(this);
        return view;
    }
}
