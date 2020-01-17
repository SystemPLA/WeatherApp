package ru.systempla.weatherapp.mvp.model.permissions;

import android.app.Activity;

public interface IPermissionManager {
    boolean checkPermission(String permission);
    boolean checkPermission(String ... permissions);
    void getPermission(Activity activity, String permission);
    void getPermission(Activity activity, String ... permissions);
}
