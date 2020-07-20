package com.tmri.size.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by tmri on 2018/3/26.
 * 程序退出的广播
 */

public class ExitReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent != null){
            String exit = intent.getStringExtra("exit");
            if(!TextUtils.isEmpty(exit)){
                if(exit.equals("exitapp")){
                    Log.i("ExitReceiver","---------  "+exit);
                  //ActivityController.getInstance().removeAll();
                    System.exit(0);
                }
            }
        }

    }
}
