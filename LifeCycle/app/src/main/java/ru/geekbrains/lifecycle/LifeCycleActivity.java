package ru.geekbrains.lifecycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LifeCycleActivity extends AppCompatActivity {

    private static final String TAG_A = "Activity";
    private static final String KEY = "Counter";

    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);

        String instanceState;
        if (savedInstanceState == null)
            instanceState = "Первый запуск!";
        else
            instanceState = "Повторный запуск!";

        Log.i(TAG_A,  instanceState + " - onCreate()");
    }

    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);

        saveInstanceState.putInt(KEY, counter);
        Log.i(TAG_A,  "onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);

        counter = saveInstanceState.getInt(KEY);
        Log.i(TAG_A,  "Повторный запуск!! - onRestoreInstanceState()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG_A,  "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG_A,  "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG_A,  "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG_A,  "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG_A,  "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG_A,  "onDestroy()");
    }
}
