package com.seatrend.routinginspection

import android.app.Application
import android.content.Context

/**
 * Created by ly on 2020/4/8 9:40
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        myApplicationContext = this
    }

    companion object {
        var myApplicationContext: Context? = null
    }
}