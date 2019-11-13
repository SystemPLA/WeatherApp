package ru.systempla.weatherapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class WeatherHistoryTable {
    private final static String TABLE_NAME = "WeatherHistory";

    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_NAME = "name";
    private final static String COLUMN_COUNTRY = "country";
    private final static String COLUMN_TEMPERATURE = "temperature";
    private final static String COLUMN_HUMIDITY = "humidity";
    private final static String COLUMN_PRESSURE = "pressure";
    private final static String COLUMN_WIND_SPEED = "wind_speed";
    private final static String COLUMN_DESCRIPTION = "description";
    private final static String COLUMN_WEATHER_ID = "weather_id";
    private final static String COLUMN_SUNRISE = "sunrise";
    private final static String COLUMN_SUNSET = "sunset";
    private final static String COLUMN_DATE_TIME = "date_time";

    static void createTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " VARCHAR(125),"
                + COLUMN_COUNTRY + " VARCHAR(125),"
                + COLUMN_TEMPERATURE + " FLOAT,"
                + COLUMN_HUMIDITY + " FLOAT,"
                + COLUMN_PRESSURE + " FLOAT,"
                + COLUMN_WIND_SPEED + " FLOAT,"
                + COLUMN_DESCRIPTION + " VARCHAR(225),"
                + COLUMN_WEATHER_ID + " INTEGER,"
                + COLUMN_SUNRISE + " INTEGER,"
                + COLUMN_SUNSET + " INTEGER,"
                + COLUMN_DATE_TIME + " INTEGER);");
    }

    public static void addWeatherHistory(String name, String country, float temp, float humidity,
                                         float pressure, float speed, String description,
                                         int actualId, long sunrise, long sunset, long dt,
                                         SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_COUNTRY, country);
        values.put(COLUMN_TEMPERATURE, temp);
        values.put(COLUMN_HUMIDITY, humidity);
        values.put(COLUMN_PRESSURE, pressure);
        values.put(COLUMN_WIND_SPEED, speed);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_WEATHER_ID, actualId);
        values.put(COLUMN_SUNRISE, sunrise);
        values.put(COLUMN_SUNSET, sunset);
        values.put(COLUMN_DATE_TIME, dt);

        database.insert(TABLE_NAME, null, values);
    }

    public static List<WeatherHistoryEntryModel> getAllNotes(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return getResultFromCursor(cursor);
    }

    private static List<WeatherHistoryEntryModel> getResultFromCursor(Cursor cursor) {
        List<WeatherHistoryEntryModel> result = null;

        if(cursor != null && cursor.moveToFirst()) {
            result = new ArrayList<>(cursor.getCount());

            int nameIdx = cursor.getColumnIndex(COLUMN_NAME);
            int countryIdx = cursor.getColumnIndex(COLUMN_COUNTRY);
            int temperatureIdx = cursor.getColumnIndex(COLUMN_TEMPERATURE);
            int humidityIdx = cursor.getColumnIndex(COLUMN_HUMIDITY);
            int pressureIdx = cursor.getColumnIndex(COLUMN_PRESSURE);
            int windSpeedIdx = cursor.getColumnIndex(COLUMN_WIND_SPEED);
            int descriptionIdx = cursor.getColumnIndex(COLUMN_DESCRIPTION);
            int weatherAIdIdx = cursor.getColumnIndex(COLUMN_WEATHER_ID);
            int sunriseIdx = cursor.getColumnIndex(COLUMN_SUNRISE);
            int sunsetIdx = cursor.getColumnIndex(COLUMN_SUNSET);
            int dateTimeIdx = cursor.getColumnIndex(COLUMN_DATE_TIME);

            do {

                WeatherHistoryEntryModel tempModel = new WeatherHistoryEntryModel();
                tempModel.setName(cursor.getString(nameIdx));
                tempModel.setCountry(cursor.getString(countryIdx));
                tempModel.setTemp(cursor.getFloat(temperatureIdx));
                tempModel.setHumidity(cursor.getFloat(humidityIdx));
                tempModel.setPressure(cursor.getFloat(pressureIdx));
                tempModel.setSpeed(cursor.getFloat(windSpeedIdx));
                tempModel.setDescription(cursor.getString(descriptionIdx));
                tempModel.setActualId(cursor.getInt(weatherAIdIdx));
                tempModel.setSunrise(cursor.getInt(sunriseIdx));
                tempModel.setSunset(cursor.getInt(sunsetIdx));
                tempModel.setDt(cursor.getInt(dateTimeIdx));

                result.add(tempModel);
            } while (cursor.moveToNext());
        }

        try { cursor.close(); } catch (Exception ignored) {}
        return result == null ? new ArrayList<WeatherHistoryEntryModel>(0) : result;
    }
}
