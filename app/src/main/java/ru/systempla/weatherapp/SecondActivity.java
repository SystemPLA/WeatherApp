package ru.systempla.weatherapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // получить данные из Intent
        Parcel parcel = (Parcel) getIntent().getExtras().getSerializable(StartSecondActivity.TEXT);

        TextView textView = findViewById(R.id.textView);
        EditText editText = findViewById(R.id.editText3);

        textView.setText(parcel.text); // Отобразить их в TextView
        editText.setText(String.valueOf(parcel.number));

        Toast.makeText(getApplicationContext(),"Second - onCreate()", Toast.LENGTH_SHORT).show();
    }
}
