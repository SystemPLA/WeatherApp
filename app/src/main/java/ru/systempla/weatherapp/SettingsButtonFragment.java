package ru.systempla.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.widget.Button;

public class SettingsButtonFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private  View SBFView;
    private Button fragmentSettingButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SBFView = inflater.inflate(R.layout.fragment_settings_button, container, false);
        fragmentSettingButton = (Button) SBFView.findViewById(R.id.settings_button);

        fragmentSettingButton.setOnClickListener(this);

        return SBFView;
    }


    @Override
    public void onClick(View view) {
        mListener.onInteraction(1, null);
    }

}
