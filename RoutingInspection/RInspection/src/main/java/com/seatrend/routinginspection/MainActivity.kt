package com.seatrend.routinginspection

import android.content.Intent
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.seatrend.routinginspection.base.BaseActivity
import com.seatrend.routinginspection.ui.RIPlanActivity
import com.seatrend.routinginspection.ui.no_network.N_RIPlanActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {

        setPageTitle(resources.getString(R.string.main_title))
        ivBack!!.visibility = View.GONE

        setStatusBarColor(ContextCompat.getColor(this, R.color.theme_color_1))
    }


    override fun bindEvent() {
        ll_cyczjh.setOnClickListener {
            intent.setClass(this, RIPlanActivity::class.java)
            startActivity(intent)
        }
        ll_no_network.setOnClickListener {
            startActivity(Intent(this,N_RIPlanActivity::class.java))
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

}
