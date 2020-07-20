package com.seatrend.routinginspection.base

import com.seatrend.routinginspection.MyApplication
import com.seatrend.routinginspection.base.Constants.APP.NETADDRESS
import com.seatrend.routinginspection.utils.cache.ACache

/**
 * Created by ly on 2020/6/28 11:27
 */
public interface Constants {

    object APP {

        val VERSION = "版本号：1.0.0"
        val NETADDRESS = "ip_address"
        var TOKEN = "Authorization" //token
        val USER_NAME: String = "user_name"

    }

    enum class NETWORK {
        NO_NETWORK, NETWORK
    }

    object IMAGE {
        val PATH =
            MyApplication.myApplicationContext!!.getExternalFilesDir(null).toString() + "/Inspection/CameraPhoto/baidu.png"
    }

    //URI

    companion object {

        val XZR: String = "xzr"
        val SETTING: String = "KEY_SETTING"  //sharepreference  的KEY
        val JHBH: String = "jhbh"  //巡检流水号
        val JHZT: String = "jhzt"  //巡检状态
        val GLBM: String = "glbm"
        val RWBH: String = "rwbh"  //任务编号
        val NET_MODEL: String = "net_model"
        //http://11.121.35.182:9091/
        var BASE_URL = ACache.get(MyApplication.myApplicationContext).getAsString(NETADDRESS) + "/"


        val LOGIN_ENTITY = "login_entity"
        val RECORD_DETAIL_ENTITY = "record_detail_entity"
        val NO_NETWORK = "NO_NETWORK"
        val YES_NETWORK = "YES_NETWORK"
    }

    object URL {
        val POST_TASK = "StationPatrol/Patrol/commitPlanTaskResult"
        val GET_TASK_LIST = "StationPatrol/Patrol/getPlanTaskList"
        val LOGIN = "security_check"
        val GET_PALN_TASK_LIST = "StationPatrol/Patrol/getPatrolPlanList"
        val GET_RECORD_LIST = "StationPatrol/Patrol/getPatrolPlanHistoryList"

        val POST_FILE = "StationPatrol/ftpFile/upload"
        val SAVE_FILE = "StationPatrol/ftpFile/saveFilePath"
        val GET_RECORD_DETAIL = "StationPatrol/Patrol/getPatrolPlanDetail"
        val GET_PHOTO_LIST = "StationPatrol/ftpFile/selectFilePath"
        val GET_PHOTO_FILE = "StationPatrol/ftpFile/download"

    }


}
