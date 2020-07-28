package com.seatrend.inspectassistant

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_title.*
import com.seatrend.inspectassistant.zxing.activity.CaptureActivity


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



                }
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

}
