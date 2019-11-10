package ru.systempla.weatherapp.ui.parcel;

import java.io.Serializable;

public class SettingsParcel implements Serializable {

    private boolean pressureFlag;
    private boolean windFlag;
    private boolean humidityFlag;

    public SettingsParcel() {
        this.pressureFlag = true;
        this.windFlag = true;
        this.humidityFlag = true;
    }

    public SettingsParcel(boolean pressureFlag, boolean windFlag, boolean humidityFlag) {
        this.pressureFlag = pressureFlag;
        this.windFlag = windFlag;
        this.humidityFlag = humidityFlag;
    }

    public boolean isPressureFlag() {
        return pressureFlag;
    }

    public boolean isWindFlag() {
        return windFlag;
    }

    public boolean isHumidityFlag() {
        return humidityFlag;
    }
}
