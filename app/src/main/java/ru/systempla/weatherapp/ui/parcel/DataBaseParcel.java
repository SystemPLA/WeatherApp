package ru.systempla.weatherapp.ui.parcel;

import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;

public class DataBaseParcel implements Serializable {

    private final SQLiteDatabase database;

    public DataBaseParcel(SQLiteDatabase database) {
        this.database = database;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
