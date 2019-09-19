package ru.systempla.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

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
            fragmentTransaction.remove(fragmentSettingButton);
            fragmentTransaction.commit();
        }
        if (interactionId==2) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.ls_fragment_container, fragmentCities);
            fragmentTransaction.add(R.id.settings_button_fragment_container, fragmentSettingButton);
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
        fragmentTransaction.add(R.id.ls_fragment_container, fragmentCities);
        fragmentTransaction.add(R.id.settings_button_fragment_container, fragmentSettingButton);
        fragmentTransaction.commit();
    }

    public SettingsParcel getSettingsParcel(){
        return this.settingsParcel;
    }
}
