package com.seatrend.routinginspection.ui

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.view.View
import com.seatrend.http_sdk.NormalView
import com.seatrend.http_sdk.base.ConmmonResponse
import com.seatrend.http_sdk.inter.base.BaseService
import com.seatrend.routinginspection.MainActivity
import com.seatrend.routinginspection.MyApplication
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.base.BaseActivity
import com.seatrend.routinginspection.base.ConfigInfo
import com.seatrend.routinginspection.base.Constants
import com.seatrend.routinginspection.entity.LoginEntity
import com.seatrend.routinginspection.http.HttpRequest
import com.seatrend.routinginspection.ui.no_network.N_RIPlanActivity
import com.seatrend.routinginspection.utils.AppUtils
import com.seatrend.routinginspection.utils.Base64Utils
import com.seatrend.routinginspection.utils.GsonUtils
import com.seatrend.routinginspection.utils.SharedPreferencesUtils
import com.seatrend.routinginspection.utils.cache.ACache
import kotlinx.android.synthetic.main.activity_login.*
import android.view.WindowManager
import android.view.Window
import android.graphics.Color
import com.seatrend.routinginspection.TestActivity


/**
 * Created by ly on 2020/6/28 10:31
 */
class LoginActivity : BaseActivity() {

    override fun initView() {
        setVersion()
        setUserName()
        appPermissionReq()
        initNetAddress()

        fullScreen()

        if(AppUtils.isApkInDebug(this)){
            user_name.setText( "system1")
            user_psw.setText( "123456")
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun fullScreen() {

        setStatusBarColor(Color.TRANSPARENT)
    }

    private fun initNetAddress() {
        if(TextUtils.isEmpty(ACache.get(this).getAsString(Constants.APP.NETADDRESS))){
            showLog(SharedPreferencesUtils.getNetworkAddress())
            ACache.get(this).put(Constants.APP.NETADDRESS,SharedPreferencesUtils.getNetworkAddress())
            Constants.BASE_URL = SharedPreferencesUtils.getNetworkAddress()+"/"
        }
    }

    private fun setUserName() {
        user_name.setText(ACache.get(this).getAsString(Constants.APP.USER_NAME))
    }

    override fun onResume() {
        super.onResume()
        if (AppUtils.isNetworkConnected(this)) {
            ll_network_state.visibility = View.GONE
        } else {
            ll_network_state.visibility = View.VISIBLE
        }
    }

    override fun bindEvent() {
        btn_setting.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SettingActivity::class.java))
//            startActivity(Intent(this@LoginActivity, TestActivity::class.java))
        }
        btn_no_network.setOnClickListener {
            startActivity(Intent(this@LoginActivity, N_RIPlanActivity::class.java))
        }
        btn_login.setOnClickListener {

            try {
                if (TextUtils.isEmpty(ACache.get(this).getAsString(Constants.APP.NETADDRESS))) {
                    showToast(resources.getString(com.seatrend.routinginspection.R.string.setting_tips))
                    return@setOnClickListener
                }
                showLoadingDialog()
                val map = HashMap<String, String>()
                map["username"] = Base64Utils.base64Encode(user_name.text.toString(), "UTF-8").replace("\n", "")
                map["password"] = Base64Utils.base64Encode(user_psw.text.toString(), "UTF-8").replace("\n", "")
                HttpRequest.posT(Constants.URL.LOGIN, map, BaseService::class.java, false, object : NormalView {
                    override fun netWorkTaskSuccess(commonResponse: ConmmonResponse?) {
                        //保存username
                        dismissLoadingDialog()
                        saveUsermsg()
                        val entity = GsonUtils.gson(commonResponse!!.responseString, LoginEntity::class.java)

                        ACache.get(this@LoginActivity).put(Constants.LOGIN_ENTITY, commonResponse.responseString)

                        showLog(" LOGIN = "+ commonResponse.responseString)
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        ConfigInfo.TOKEN = entity.authorization
                        dismissLoadingDialog()

                    }

                    override fun netWorkTaskfailed(commonResponse: ConmmonResponse?) {
                        showErrorDialog(commonResponse!!.responseString)
                        dismissLoadingDialog()
                    }
                })
            } catch (e: Exception) {
                showToast(e.message.toString())
                dismissLoadingDialog()
            }
        }
    }

    private fun saveUsermsg() {
        ACache.get(this@LoginActivity).put(Constants.APP.USER_NAME, user_name.text.toString())
    }

    private fun setVersion() {
        app_version.text = getString(com.seatrend.routinginspection.R.string.add_version,AppUtils.getVersionName(this))
    }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }
}