package ru.systempla.weatherapp.mvp.model.permissions;

public interface IPermissionManager {
    boolean checkPermission(String permission);
    boolean checkPermission(String ... permissions);
    void getPermission(String permission);
    void getPermission(String ... permissions);
}
