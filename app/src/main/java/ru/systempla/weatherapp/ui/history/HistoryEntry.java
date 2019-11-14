package ru.systempla.weatherapp.ui.history;

import java.text.DateFormat;
import java.util.Date;

import ru.systempla.weatherapp.database.WeatherHistoryEntryModel;

class HistoryEntry {

    private final String cityCountry;
    private final String date;
    private final String temperature;
    private final String pressure;
    private final String windSpeed;
    private final String humidity;

    HistoryEntry(WeatherHistoryEntryModel model) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        this.cityCountry = model.getName().toUpperCase() + ", " + model.getCountry();
        this.date = dateFormat.format(new Date(model.getDt() * 1000));
        this.temperature = String.valueOf(model.getTemp());
        this.pressure = String.valueOf(model.getPressure());
        this.windSpeed = String.valueOf(model.getSpeed());
        this.humidity = String.valueOf(model.getHumidity());
    }

    String getCityCountry() {
        return cityCountry;
    }

    String getDate() {
        return date;
    }

    String getTemperature() {
        return temperature;
    }

    String getPressure() {
        return pressure;
    }

    String getWindSpeed() {
        return windSpeed;
    }

    String getHumidity() {
        return humidity;
    }
}
