package com.seatrend.routinginspection.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by ly on 2020/7/10 18:12
 */
public class ToastUtil {

    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void show(Activity context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
