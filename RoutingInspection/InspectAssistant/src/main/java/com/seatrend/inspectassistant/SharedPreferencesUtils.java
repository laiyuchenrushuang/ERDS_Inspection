package com.seatrend.inspectassistant;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;


@TargetApi(Build.VERSION_CODES.KITKAT)
public class SharedPreferencesUtils {

    private static final String ADDRESS = "address";

    /**
     * 获取服务地址
     * @return
     */
    public static String getNetworkAddress(){
        return   MyApplication.Companion.getMyApplicationContext().getSharedPreferences("setting", Context.MODE_PRIVATE)
                .getString(ADDRESS,MyApplication.Companion.getMyApplicationContext().getResources().getString(R.string.address));
    }

    /**
     * 设置服务地址
     * @param network
     */
    public static void setNetworkAddress(String network){
        MyApplication.Companion.getMyApplicationContext().getSharedPreferences("setting", Context.MODE_PRIVATE)
                .edit().putString(ADDRESS,network).apply();
    }


}
