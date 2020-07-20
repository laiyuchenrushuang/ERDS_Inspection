package com.seatrend.inspectassistant

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_plugin.*

/**
 * Created by ly on 2020/6/30 10:55
 */
class PlugStateActivity : BaseActivity() {
    val OK = 0
    val NO = 1

    val ZFJLY = "com.tmri.enforcement.app" //执法记录仪
    val OBD = "com.tmri.obd.app"  //OBD
    val YLR = "com.tmri.scanmanager.app"  //预录入
    val WK = "com.tmri.size.app"  //外廓


    override fun initView() {
        setPageTitle(getResourceString(R.string.cjzt))

        checkPackage(ZFJLY, tv_zfjly)
        checkPackage(OBD, tv_obd)
        checkPackage(YLR, tv_ylr)
        checkPackage(WK, tv_wkcl)

    }

    private fun checkPackage(packageName: String, tView: TextView?) {
        if (AppUtils.isPkgInstalled(packageName, this)) {
            setText(tView, OK)
        } else {
            setText(tView, NO)
        }
    }

    private fun setText(text: TextView?, flag: Int) {

        when (flag) {
            OK -> {
                text!!.text = getResourceString(R.string.yaz)
                text.setTextColor(ContextCompat.getColor(this,R.color.green))
            }
            NO -> {
                text!!.text = getResourceString(R.string.waz)
                text.setTextColor(ContextCompat.getColor(this,R.color.gray))
            }
        }
    }

    override fun bindEvent() {
    }

    override fun getLayout(): Int {
        return R.layout.activity_plugin
    }
}