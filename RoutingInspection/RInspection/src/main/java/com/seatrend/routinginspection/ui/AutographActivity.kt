package com.seatrend.routinginspection.ui

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.base.BaseActivity
import com.seatrend.routinginspection.base.Constants
import com.seatrend.routinginspection.base.Constants.Companion.GLBM
import com.seatrend.routinginspection.base.Constants.Companion.JHBH
import com.seatrend.routinginspection.db.DbUtils
import com.seatrend.routinginspection.db.table.PictureTable
import com.seatrend.routinginspection.db.table.PlanTable
import com.seatrend.routinginspection.entity.PhotoOnlineDataBean
import com.seatrend.routinginspection.ui.no_network.N_RIPlanActivity
import com.seatrend.routinginspection.utils.BitmapUtils
import com.seatrend.xj.electricbicyclesalesystem.http.thread.ThreadPoolManager
import kotlinx.android.synthetic.main.activity_autograph.*
import kotlinx.android.synthetic.main.common_button_next.*
import java.io.File

/**
 * Created by ly on 2020/6/29 9:59
 */
class AutographActivity : BaseActivity() {

    private var filePath = ""

    override fun initView() {
        setPageTitle(resources.getString(R.string.qianming))
        btn_next.text = resources.getString(R.string.qianzi)
        ivBack!!.visibility = View.GONE

    }

    override fun bindEvent() {
        btn_clear.setOnClickListener {
            auto_view.clear()
            synchronized(filePath) {
                DbUtils.getInstance(this).deletePicture(intent.getStringExtra(Constants.JHBH), filePath)
            }
        }
        btn_next.setOnClickListener {
            if (!auto_view.touched) {
                showToast(resources.getString(R.string.qianming_tip1))
                return@setOnClickListener
            }
//            showLoadingDialog()
            saveQm()
        }
    }

    private fun saveQm() {


        if (Constants.NO_NETWORK == intent.getStringExtra(Constants.NET_MODEL)) {
            ThreadPoolManager.instance.execute(Runnable {
                filePath = BitmapUtils.saveBitmap(this, auto_view.autographBitmap, "qmzp" + System.currentTimeMillis())
                synchronized(filePath) {
                    savePhotoToDb(filePath)
                }
            })

            val entity = PlanTable()
            entity.lsh = intent.getStringExtra(Constants.JHBH)
            entity.jhzt = "2"
            DbUtils.getInstance(this).update(entity)

            startActivityToTop(N_RIPlanActivity::class.java)
        } else {
            ThreadPoolManager.instance.execute(Runnable {
                filePath = BitmapUtils.saveBitmap(this, auto_view.autographBitmap, "qmzp" + System.currentTimeMillis())
                synchronized(filePath) {
                    val GLBM = intent.getStringExtra(Constants.GLBM)
                    val JHBH = intent.getStringExtra(Constants.JHBH)
                    val entity = PhotoOnlineDataBean()
                    entity.glbm = GLBM
                    entity.jhbh = JHBH
                    entity.zpPath = filePath
                    DbUtils.getInstance(this).insert(entity)

                    //删除本地离线数据
                    DbUtils.getInstance(this).deleteDbPlanJudgePicture(JHBH)
                }
            })
            startActivityToTop(RIPlanActivity::class.java)
        }

    }


    private fun savePhotoToDb(filePath: String) {
        val entity = PictureTable()
        entity.lsh = intent.getStringExtra(Constants.JHBH)
        entity.zpPath = filePath
        entity.glbm = intent.getStringExtra(Constants.GLBM)
        DbUtils.getInstance(this).insert(entity)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        when (event!!.action) {
            KeyEvent.KEYCODE_BACK -> {
                "do nothing"
            }
        }
        return true
    }

    override fun getLayout(): Int {
        return R.layout.activity_autograph
    }
}