package com.hc.library.bluesocket.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2018/1/17.
 */

public class APPUtil {

    public static String getVersionName(Context context) {
        if (getPackageInfo(context) != null) {
            return getPackageInfo(context).versionName;
        }

        return null;
    }
    /**
     * @param context 上下文信息
     * @return 获取包信息
     * getPackageName()是当前类的包名，0代表获取版本信息
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
