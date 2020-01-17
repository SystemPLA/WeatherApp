package ru.systempla.weatherapp.mvp.model.permissions;

public class PermissionManager implements IPermissionManager {

    @Override
    public boolean checkPermission(String permission) {
        return false;
    }

    public boolean checkPermission(String ... permissions){

    }

    @Override
    public void getPermission(String permission) {

    }

    public void getPermission(String ... permissions){
        
    }
}
