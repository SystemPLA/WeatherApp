package ru.systempla.weatherapp.mvp.model.permissions;

import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

import java.lang.reflect.Array;

import ru.systempla.weatherapp.mvp.App;

public class PermissionManager implements IPermissionManager {

    @Override
    public boolean checkPermission(String permission) {
        ActivityCompat.checkSelfPermission(App.getInstance(), permission);
        return (ActivityCompat.checkSelfPermission(App.getInstance(), permission)!= PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public boolean checkPermission(String ... permissions){
        boolean flag = true;
        for (String permission : permissions ) {
            flag &= (ActivityCompat.checkSelfPermission(App.getInstance(), permission)!= PackageManager.PERMISSION_GRANTED);
        }
        return flag;
    }

    @Override
    public void getPermission(String permission) {
        (, new String[]{permission}, 100);
    }

    @Override
    public void getPermission(String ... permissions){
        ActivityCompat.requestPermissions(this, permissions,100);
    }
}
