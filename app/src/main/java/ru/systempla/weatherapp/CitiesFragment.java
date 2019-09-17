package ru.systempla.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

public class CitiesFragment extends ListFragment {

    private static final String KEY = "CurrentCity";

    private boolean isExistWeatherInfo;
    private Parcel currentParcel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Cities, android.R.layout.simple_list_item_activated_1);

        setListAdapter(adapter);

        View detailsFrame = getActivity().findViewById(R.id.weather_info);
        isExistWeatherInfo = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null)
            currentParcel = (Parcel) savedInstanceState.getSerializable(KEY);
        else
            currentParcel = new Parcel(0,
                    getResources().getTextArray(R.array.Cities)[0].toString());

        if (isExistWeatherInfo) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showWeatherInfo(currentParcel);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY, currentParcel);
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        TextView cityNameView = (TextView) view;
        currentParcel =  new Parcel(position, cityNameView.getText().toString());
        showWeatherInfo(currentParcel);
    }

    private void showWeatherInfo(Parcel parcel) {
        if (isExistWeatherInfo) {
            getListView().setItemChecked( parcel.getCityIndex(), true);

            WeatherInfoFragment detail = (WeatherInfoFragment)
                    getFragmentManager().findFragmentById(R.id.weather_info);
            if (detail == null || detail.getParcel().getCityIndex() != parcel.getCityIndex()) {

                detail = WeatherInfoFragment.create(parcel);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.weather_info, detail);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }
        else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), WeatherInfoActivity.class);
            intent.putExtra(WeatherInfoFragment.PARCEL, parcel);
            startActivity(intent);
        }
    }
}
