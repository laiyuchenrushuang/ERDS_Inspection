package com.tmri.size.app;

import android.app.Application;

/**
 * Created by Jack.Yan on 2017/11/28.
 */

public class MApplication extends Application {


    private static MApplication applicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext=this;
        //CrashHandler.getInstance().init(this);
    }

    public static MApplication getMyApplicationContext(){
        return applicationContext;
    }
}
