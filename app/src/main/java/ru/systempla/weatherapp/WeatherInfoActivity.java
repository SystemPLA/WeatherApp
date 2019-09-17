package ru.systempla.weatherapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class WeatherInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {

            WeatherInfoFragment details = new WeatherInfoFragment();
            details.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, details).commit();
        }
    }
}
