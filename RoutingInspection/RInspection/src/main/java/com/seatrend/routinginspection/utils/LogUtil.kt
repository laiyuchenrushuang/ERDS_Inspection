package com.seatrend.routinginspection.utils

import android.util.Log

/**
 * Created by ly on 2020/7/9 9:41
 */
class LogUtil {


    companion object{
        private val TAG = "[lylog]"

        fun d(s:Any){
            Log.d(TAG ,"  $s")
        }
    }
}