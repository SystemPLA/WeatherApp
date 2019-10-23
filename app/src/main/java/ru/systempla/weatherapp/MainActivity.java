package ru.systempla.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private Toolbar toolbar;
    private EditText searchEditText;
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

        initViews();

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

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchEditText = findViewById(R.id.searchEditText);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        handleMenuItemClick(item);
        return super.onOptionsItemSelected(item);
    }

    private void handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.change_settings: {
                Toast.makeText(getApplicationContext(), "Изменение настроек", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.change_city: {
                if(searchEditText.getVisibility() == View.VISIBLE) {
                    searchEditText.setVisibility(View.GONE);
                    Objects.requireNonNull(getSupportActionBar())
                            .setTitle(getString(R.string.app_name));
                } else {
                    Objects.requireNonNull(getSupportActionBar()).setTitle("");
                    searchEditText.setVisibility(View.VISIBLE);
                    searchEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            //Вам приходит на вход текст поиска - ищем его - в бд, через АПИ (сервер в инете) и т.д.
                            Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            }
            default: {
                Toast.makeText(getApplicationContext(), getString(R.string.action_not_found),
                        Toast.LENGTH_SHORT).show();
            }

            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
