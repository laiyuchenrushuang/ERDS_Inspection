package com.tmri.size.app;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

/**
 * Created by Jack.Yan on 2017/11/14.
 */

public class PermissionsUtils {
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE=0xaa01;

    /**
     * 判断并请求 SD卡写权限，通过
     * onRequestPermissionsResult 返回
     * @param activity
     */
    public static void requestPermissionSDCard(Activity activity){
        if(android.os.Build.VERSION.SDK_INT>=23){//
            int permission= ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permission!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
    }
}
