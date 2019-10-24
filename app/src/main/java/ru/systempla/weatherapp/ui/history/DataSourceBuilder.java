package ru.systempla.weatherapp.ui.history;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import ru.systempla.weatherapp.R;

public class DataSourceBuilder {

    private final List<HistoryEntry> dataSource;
    private final Resources resources;

    public DataSourceBuilder(Resources resources) {
        dataSource = new ArrayList<>(6);
        this.resources = resources;
    }

    public List<HistoryEntry> build() {
        String[] dates = resources.getStringArray(R.array.dates);
        String[] temperatures = resources.getStringArray(R.array.temperatures);
        String[] pressures = resources.getStringArray(R.array.pressures);
        String[] windSpeeds = resources.getStringArray(R.array.windSpeeds);
        String[] humidities = resources.getStringArray(R.array.humidities);
        for (int i = 0; i < dates.length; i++)
            dataSource.add(new HistoryEntry(dates[i], temperatures[i], pressures[i], windSpeeds[i], humidities[i]));
        return dataSource;
    }
}