package com.seatrend.routinginspection.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.seatrend.routinginspection.PhotoUploadService;
import com.seatrend.routinginspection.base.Constants;

/**
 * Created by ly on 2020/7/24 11:12
 * <p>
 * 守护广播
 */
public class ProtectServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (Constants.Companion.getPROTECT_SERVICE().equals(intent.getAction())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, PhotoUploadService.class));
            } else {
                context.startService(new Intent(context, PhotoUploadService.class));
            }
        }

    }
}
