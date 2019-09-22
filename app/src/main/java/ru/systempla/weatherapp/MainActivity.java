package ru.systempla.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import android.util.Log;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private Fragment fragmentCities;
    private Fragment fragmentSettingButton;
    private Fragment fragmentSettings;
    private SettingsParcel settingsParcel = new SettingsParcel(true,true,true);


    @Override
    public void onInteraction(int interactionId, SettingsParcel sparcel) {
        if (interactionId==1){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.ls_fragment_container, fragmentSettings);
            fragmentSettingButton.getView().setVisibility(View.GONE);
            if (fragmentSettingButton.getView().getVisibility()==View.GONE) {
            }
            fragmentTransaction.commit();
        }
        if (interactionId==2) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.ls_fragment_container, fragmentCities);
            fragmentSettingButton.getView().setVisibility(View.VISIBLE);
            fragmentTransaction.commit();
            settingsParcel = sparcel;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentCities = new CitiesFragment();
        fragmentSettingButton = new SettingsButtonFragment();
        fragmentSettings = new SettingsFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ls_fragment_container, fragmentCities);
        fragmentTransaction.replace(R.id.settings_button_fragment_container, fragmentSettingButton);
        Log.i("MA","On Create: replace fraCities, FSB");
        fragmentTransaction.commit();
    }

    public SettingsParcel getSettingsParcel(){
        return this.settingsParcel;
    }
}
