package com.tmri.size.app;

import android.content.Context;


/**
 * Created by tmri on 2018/8/22.
 */

public class SharedPreferencesUtils {

    private static final String SETTING="setting";

    public static void setIpAddress(String ip){
        MApplication.getMyApplicationContext().getSharedPreferences(SETTING, Context.MODE_PRIVATE)
                .edit().putString("ip",ip).apply();
    }
    public static String getIpAddress(){
      return   MApplication.getMyApplicationContext().getSharedPreferences(SETTING, Context.MODE_PRIVATE)
               .getString("ip","");
    }
    public static void setPort(String port){
        MApplication.getMyApplicationContext().getSharedPreferences(SETTING, Context.MODE_PRIVATE)
                .edit().putString("port",port).apply();
    }
    public static String getPort(){
        return   MApplication.getMyApplicationContext().getSharedPreferences(SETTING, Context.MODE_PRIVATE)
                .getString("port","8080");
    }



    public static void setNetWorkAddress(String address){
        MApplication.getMyApplicationContext().getSharedPreferences(SETTING, Context.MODE_PRIVATE)
                .edit().putString("service_address",address).apply();
    }
    public static String getNetWorkAddress(){
        return   MApplication.getMyApplicationContext().getSharedPreferences(SETTING, Context.MODE_PRIVATE)
                .getString("service_address","http://11.121.35.182:9091/vehicleCheckAsistant");
    }
}
