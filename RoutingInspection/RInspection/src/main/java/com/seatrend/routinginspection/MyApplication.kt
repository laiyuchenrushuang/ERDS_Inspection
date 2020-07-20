package com.seatrend.routinginspection

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.seatrend.routinginspection.utils.LogUtil

/**
 * Created by ly on 2020/4/8 9:40
 */
class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        myApplicationContext = this
        val intent = Intent(this,PhotoUploadService::class.java)
//        startService(intent)
        bindService(intent, cnnec, Context.BIND_AUTO_CREATE)
    }


    companion object {
        var myApplicationContext: Context? = null


        val cnnec= object :ServiceConnection{
            override fun onServiceDisconnected(name: ComponentName?) {
                LogUtil.d(""+"服务连接取消")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                LogUtil.d(""+"服务连接成功")
            }
        }
    }
}