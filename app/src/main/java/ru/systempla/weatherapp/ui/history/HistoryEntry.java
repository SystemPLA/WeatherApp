package ru.systempla.weatherapp.ui.history;

class HistoryEntry {
    private final String date;
    private final String temperature;
    private final String pressure;
    private final String windSpeed;
    private final String humidity;

    HistoryEntry(String date, String temperature, String pressure, String windSpeed, String humidity) {
        this.date = date;
        this.temperature = temperature;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
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
