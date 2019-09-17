package ru.geekbrains.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LifeCycleFragment extends Fragment {

    private static final String TAG_F = "Fragment";
    private int counter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_life_cycle, container, false);

        final LifeCyclePresenter presenter = LifeCyclePresenter.getInstance();

        final TextView textCounter = view.findViewById(R.id.textCounter);
        counter = presenter.getCounter();
        textCounter.setText(String.valueOf(counter));

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {      // Обработка нажатий
            @Override
            public void onClick(View v) {
                presenter.incrementCounter();
                counter = presenter.getCounter();
                textCounter.setText(String.valueOf(counter));
            }
        });

        Log.i(TAG_F,  "onCreateView()");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG_F,  "onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG_F,  "onCreate()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG_F,  "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG_F,  "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG_F,  "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG_F,  "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG_F,  "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG_F,  "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG_F,  "onDetach()");
    }
}
