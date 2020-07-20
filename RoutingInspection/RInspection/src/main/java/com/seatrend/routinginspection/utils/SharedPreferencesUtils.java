package com.seatrend.routinginspection.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import com.seatrend.routinginspection.MyApplication;
import com.seatrend.routinginspection.R;
import com.seatrend.routinginspection.base.Constants;

import java.util.ArrayList;
import java.util.Objects;


@TargetApi(Build.VERSION_CODES.KITKAT)
public class SharedPreferencesUtils {

    /**
     * 获取服务地址
     * @return
     */
    public static String getNetworkAddress(){
        return   MyApplication.Companion.getMyApplicationContext().getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .getString(Constants.APP.INSTANCE.getNETADDRESS(),MyApplication.Companion.getMyApplicationContext().getResources().getString(R.string.address));
    }

    /**
     * 设置服务地址
     * @param network
     */
    public static void setNetworkAddress(String network){
        MyApplication.Companion.getMyApplicationContext().getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .edit().putString(Constants.APP.INSTANCE.getNETADDRESS(),network).apply();
    }


}
