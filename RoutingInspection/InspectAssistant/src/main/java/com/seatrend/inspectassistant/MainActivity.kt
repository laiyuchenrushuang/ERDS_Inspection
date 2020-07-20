package com.seatrend.inspectassistant

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_title.*
import android.text.TextUtils
import com.google.zxing.client.android.CaptureActivity
import com.seatrend.http_sdk.HttpService
import com.seatrend.http_sdk.NormalView
import com.seatrend.http_sdk.base.ConmmonResponse
import com.seatrend.http_sdk.inter.base.BaseService
import okhttp3.MediaType
import okhttp3.RequestBody


class MainActivity : BaseActivity() {
    private val REQUEST = 0
    private val QCODE_RESULT = "qcode_result"

    override fun initView() {
        setPageTitle(getResourceString(R.string.title))
        ivBack!!.visibility = View.INVISIBLE
        tv_right.text = getResourceString(R.string.setting)

        appPermissionReq()

        setTitleColorByImage()

    }

    @SuppressLint("ResourceAsColor")
    private fun setTitleColorByImage() {
//       rlParent!!.setBackgroundColor(R.color.picture_bg)
    }

    override fun bindEvent() {

        ll_yyxx.setOnClickListener {
            val intent = Intent(this@MainActivity, CaptureActivity::class.java)
            startActivityForResult(intent, REQUEST)
        }
        ll_lscx.setOnClickListener {
            startActivity(Intent(this, SerialActivity::class.java))

        }
        ll_cjzt.setOnClickListener {
            startActivity(Intent(this, PlugStateActivity::class.java))

        }

        tv_right.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST -> {

                    if (data != null && !TextUtils.isEmpty(data.getStringExtra("qcode_result"))) {
                        LoadingDialog.getInstance().showLoadDialog(this)
                        HttpService.getInstance().postJson(SharedPreferencesUtils.getNetworkAddress() + "/",
                            Constants.POST_QCODE,
                            data.getStringExtra("qcode_result"), BaseService::class.java,
                            false, object : NormalView {
                                override fun netWorkTaskSuccess(commonResponse: ConmmonResponse?) {
                                    LoadingDialog.getInstance().dismissLoadDialog()

                                    val intent = Intent(this@MainActivity, OrderActivity::class.java)
                                    intent.putExtra("qcode_result",data.getStringExtra("qcode_result"))
                                    startActivity(intent)
                                }

                                override fun netWorkTaskfailed(commonResponse: ConmmonResponse?) {
                                    LoadingDialog.getInstance().dismissLoadDialog()
                                    showErrorDialog(commonResponse!!.responseString)
                                }

                            });

                    }

                }
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

}
