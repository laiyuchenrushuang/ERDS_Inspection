package com.seatrend.routinginspection

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import com.seatrend.routinginspection.utils.LogUtil
import com.seatrend.routinginspection.utils.ToastUtil


/**
 * Created by ly on 2020/4/8 9:40
 */
class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        myApplicationContext = this
        val intent = Intent()
        intent.component =
            ComponentName(this.packageName, this.packageName + "." + PhotoUploadService::class.java.simpleName)
//        startService(intent)
        val isBind = bindService(intent, cnnec, Context.BIND_AUTO_CREATE)
        if (!isBind) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //android8.0以上通过startForegroundService启动service
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }
//
//        unbindService(cnnec)  //test
    }


    companion object {
        var myApplicationContext: Context? = null


        val cnnec = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                LogUtil.d("" + "服务连接取消")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                LogUtil.d("" + "服务连接成功")
            }
        }
    }
}