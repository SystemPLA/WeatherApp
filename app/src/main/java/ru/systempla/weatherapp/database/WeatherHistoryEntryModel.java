package ru.systempla.weatherapp.database;

public class WeatherHistoryEntryModel {

    private String name;
    private String country;
    private float temp;
    private float humidity;
    private float pressure;
    private float speed;
    private long dt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    void setCountry(String country) {
        this.country = country;
    }

    public float getTemp() {
        return temp;
    }

    void setTemp(float temp) {
        this.temp = temp;
    }

    public float getHumidity() {
        return humidity;
    }

    void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getPressure() {
        return pressure;
    }

    void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getSpeed() {
        return speed;
    }

    void setSpeed(float speed) {
        this.speed = speed;
    }


    public long getDt() {
        return dt;
    }

    void setDt(long dt) {
        this.dt = dt;
    }
}
