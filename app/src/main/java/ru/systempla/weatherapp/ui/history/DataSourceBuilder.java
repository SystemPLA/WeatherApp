package ru.systempla.weatherapp.ui.history;

import java.util.ArrayList;
import java.util.List;

import ru.systempla.weatherapp.database.WeatherHistoryEntryModel;

class DataSourceBuilder {

    private final List<HistoryEntry> dataSource;
    private final List<WeatherHistoryEntryModel> resources;

    DataSourceBuilder(List<WeatherHistoryEntryModel> resources) {
        dataSource = new ArrayList<>(resources.size());
        this.resources = resources;
    }

    List<HistoryEntry> build() {
        for (int i = 0; i < resources.size(); i++)
            dataSource.add(new HistoryEntry(resources.get(i)));
        return dataSource;
    }
}