package ru.systempla.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Parcel parcel = (Parcel) getIntent().getExtras().getSerializable(StartSecondActivity.TEXT);

        TextView cityText = findViewById(R.id.textView);

        TextView pressureLabel = findViewById(R.id.textView6);
        TextView pressureValue = findViewById(R.id.textView8);

        TextView windLabel = findViewById(R.id.textView7);
        TextView windValue = findViewById(R.id.textView9);

        TextView humidityLabel = findViewById(R.id.textView10);
        TextView humidityValue = findViewById(R.id.textView11);

        cityText.setText(parcel.text);

        pressureLabel.setVisibility(getVisiability(parcel.pressureFlag));
        pressureValue.setVisibility(getVisiability(parcel.pressureFlag));

        windLabel.setVisibility(getVisiability(parcel.windFlag));
        windValue.setVisibility(getVisiability(parcel.windFlag));

        humidityLabel.setVisibility(getVisiability(parcel.humidityFlag));
        humidityValue.setVisibility(getVisiability(parcel.humidityFlag));
    }

    private int getVisiability (boolean flag){
        if (flag) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }
}
