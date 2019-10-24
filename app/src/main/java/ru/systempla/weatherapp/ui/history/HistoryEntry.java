package ru.systempla.weatherapp.ui.history;


public class HistoryEntry {
    private final String date;
    private final String temperature;
    private final String pressure;
    private final String windSpeed;
    private final String humidity;

    public HistoryEntry(String date, String temperature, String pressure, String windSpeed, String humidity) {
        this.date = date;
        this.temperature = temperature;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }
}
