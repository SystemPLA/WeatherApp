package ru.systempla.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class StartSecondActivity implements View.OnClickListener {

    public final static String TEXT = "Text";

    private final Activity sourceActivity;

    public StartSecondActivity(Activity sourceActivity){
        this.sourceActivity = sourceActivity;
    }

    @Override
    public void onClick(View view) {

        EditText txt = sourceActivity.findViewById(R.id.editText);
        CheckBox pressureBox = sourceActivity.findViewById(R.id.checkBox);
        CheckBox windBox = sourceActivity.findViewById(R.id.checkBox2);
        CheckBox humidityBox = sourceActivity.findViewById(R.id.checkBox3);

        Parcel parcel = new Parcel();
        parcel.text = txt.getText().toString();
        parcel.pressureFlag = pressureBox.isChecked();
        parcel.windFlag = windBox.isChecked();
        parcel.humidityFlag = humidityBox.isChecked();

        Intent intent = new Intent(sourceActivity, SecondActivity.class);
        intent.putExtra(TEXT, parcel);

        sourceActivity.startActivityForResult(intent, MainActivity.REQUEST_CODE);
    }
}

