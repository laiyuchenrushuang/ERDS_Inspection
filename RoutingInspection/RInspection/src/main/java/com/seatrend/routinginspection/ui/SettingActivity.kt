package com.seatrend.routinginspection.ui

import android.text.TextUtils
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.base.BaseActivity
import com.seatrend.routinginspection.base.Constants
import com.seatrend.routinginspection.utils.AppUtils
import com.seatrend.routinginspection.utils.cache.ACache
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_setting.btn_ok
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by ly on 2020/6/28 11:35
 */
class SettingActivity : BaseActivity() {
    override fun initView() {
        setPageTitle(resources.getString(R.string.setting_title))
        et_network.setText(ACache.get(this).getAsString(Constants.APP.NETADDRESS))

//        if (AppUtils.isNetworkConnected(this)) {
//            showToast(resources.getString(R.string.ok_network))
//        }
    }

    override fun bindEvent() {
        btn_ok.setOnClickListener {
            ACache.get(this).put(Constants.APP.NETADDRESS, et_network.text.toString())
            Constants.BASE_URL = ACache.get(this).getAsString(Constants.APP.NETADDRESS) + "/"
            showToast(resources.getString(R.string.setting_ok))
            finish()
        }
        btn_sync.setOnClickListener {
            val map = HashMap<String, String>()
//            map["rwmc"] = "1"
//            map["rwms"] = "1"
//            map["rwbh"] = "1"
//            map["rwzt"] = "1"
//            map["cjry"] = "1"
//            HttpService.instance.default.create(RIService::class.java).getPatrolTask(map).enqueue(object :
//                Callback<TestEntity> {
//                override fun onFailure(call: Call<TestEntity>, t: Throwable) {
//
//                    showToast(t.message.toString())
//                }
//
//                override fun onResponse(call: Call<TestEntity>, response: Response<TestEntity>) {
//                    val result = response.body()
//                    showLog("result ok = ${result!!.data}")
//                }
//
//            })
        }

    }

    override fun getLayout(): Int {
        return R.layout.activity_setting
    }
}