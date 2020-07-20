package com.seatrend.inspectassistant;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ly on 2020/6/23 15:47
 */
public class AppUtils {

    /**
     * 获取应用版本名
     *
     * @return 成功返回版本名， 失败返回null
     */
    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.versionName;
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

    //看设备是否支持NFC
    public static String getSystemProperty() {
        try {
            Class build = Class.forName("android.os.Build");
            String customName = (String) build.getDeclaredField("PWV_CUSTOM_CUSTOM").get(null);
            return customName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 清除缓存数据
     *
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void clearAppData(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            manager.clearApplicationUserData();
        }

    }

    /**
     * 判断当前应用是否是debug状态
     */

    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否是终端PDA
     *
     * @return
     */
    public static boolean HCPDA() {
        if ("HC".equals(getSystemProperty())) {
            return true;
        }
        return false;
    }


    public static boolean isPkgInstalled(String pkgName, Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }



    /**
     * 是否联网
     * @param context
     * @return  可以用就是 true  不可用就是false
     */
    public static boolean isNetworkConnected(Context context){
        //判断上下文是不是空的
        //为啥要判断啊? 防止context是空的导致 报空指针异常
        if (context!=null){
            //获取连接管理器
            ConnectivityManager mConnectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取网络状态mConnectivityManager.getActiveNetworkInfo();
            NetworkInfo mNnetNetworkInfo=mConnectivityManager.getActiveNetworkInfo();
            if (mNnetNetworkInfo!=null){
                //判断网络是否可用//如果可以用就是 true  不可用就是false
                return mNnetNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
