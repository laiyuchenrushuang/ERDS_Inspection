package com.seatrend.routinginspection.ui

import android.content.Intent
import androidx.viewpager.widget.ViewPager
import com.seatrend.http_sdk.NormalView
import com.seatrend.http_sdk.base.ConmmonResponse
import com.seatrend.http_sdk.inter.base.BaseService
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.adapter.MyFragmentPagerAdapter
import com.seatrend.routinginspection.base.BaseActivity
import com.seatrend.routinginspection.base.BaseFragment
import com.seatrend.routinginspection.base.Constants
import com.seatrend.routinginspection.db.DbUtils
import com.seatrend.routinginspection.entity.PhotoOnlineDataBean
import com.seatrend.routinginspection.http.HttpRequest
import com.seatrend.routinginspection.ui.fragment.Inspect1Fragment
import com.seatrend.routinginspection.ui.fragment.Inspect2Fragment
import com.seatrend.routinginspection.utils.GsonUtils
import kotlinx.android.synthetic.main.activity_inspect.*
import kotlinx.android.synthetic.main.common_button_next.*

/**
 * Created by ly on 2020/6/28 16:52
 */
class InspectActivity : BaseActivity() {


    private val F1 = 0
    private val F2 = 1

    var fragmentList = ArrayList<BaseFragment>()
    var fragment1 = Inspect1Fragment()  //巡检判定fragment
    var fragment2 = Inspect2Fragment()  //巡检拍照fragment
    var viewPager: ViewPager? = null

    override fun initView() {
        setPageTitle(resources.getString(R.string.inspect_title))
//        btn_next.text = resources.getString(R.string.qianming)
        setDefaultSate()
        initViewPager()
    }

    override fun bindEvent() {

        tv_jugde.setOnClickListener {
            selection1()
            viewPager!!.setCurrentItem(F1, true)
        }

        tv_capture.setOnClickListener {
            selection2()
            viewPager!!.setCurrentItem(F2, true)
        }

        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                showLog(position)
                when (position) {
                    F1 -> {
                        selection1()
                    }
                    F2 -> {
                        selection2()
                    }

                }
            }

        })

        btn_next.setOnClickListener {


            val list = fragment1.getChangedData()
            for(db in list){
                for(db in list){
                    if("0" == db.rwzxjg){
                        showToast(getString(R.string.add_tip_2,db.rwmc))
                        return@setOnClickListener
                    }
                }

            }


            val zpList = fragment2.getChangedZplist()
            showLog("zpListss = "+GsonUtils.toJson(zpList))
            showLog("pdListss = "+GsonUtils.toJson(list))


            val GLBM = intent.getStringExtra(Constants.GLBM)
            val JHBH = intent.getStringExtra(Constants.JHBH)

            if(zpList.size>0){

                for(db in zpList){
                    if(db.zplj != null && db.zplj != ""){ //排除那个为空采集照片项
                        val entity = PhotoOnlineDataBean()
                        entity.glbm = GLBM
                        entity.jhbh = JHBH
                        entity.zpPath = db.zplj
                        DbUtils.getInstance(this).insert(entity)
                    }
                }
            }



            val map = HashMap<String, String>()
            map["taskJsonList"] = GsonUtils.toJson(list)
            map["bz"] = fragment1.getChangedBzData()
            map["sjly"] = "0" //数据来源(0手机端，1电脑端)
            showLoadingDialog()
            HttpRequest.posT(Constants.URL.POST_TASK, map, BaseService::class.java, true, object : NormalView {
                override fun netWorkTaskSuccess(commonResponse: ConmmonResponse?) {
                    dismissLoadingDialog()
//                    val GLBM = intent.getStringExtra(Constants.GLBM)
//                    val JHBH = intent.getStringExtra(Constants.JHBH)
                    intent.setClass(this@InspectActivity,AutographActivity::class.java)
//                    intent.putExtra(Constants.JHBH,JHBH)
//                    intent.putExtra(Constants.GLBM,GLBM)
                    intent.putExtra(Constants.NET_MODEL,Constants.YES_NETWORK)
                    startActivity(intent)
                }

                override fun netWorkTaskfailed(commonResponse: ConmmonResponse?) {
                    dismissLoadingDialog()
                    showErrorDialog(commonResponse!!.responseString)
                }
            })
        }

    }

    private fun initViewPager() {
        fragmentList.add(fragment1)
        fragmentList.add(fragment2)
        val myFragmentPagerAdapter = MyFragmentPagerAdapter(supportFragmentManager, fragmentList)
        viewPager = findViewById(R.id.viewpager)
        viewPager!!.adapter = myFragmentPagerAdapter
    }

    private fun setDefaultSate() {
        selection1()

    }

    private fun selection1() {
        tv_jugde.setTextColor(getTextColor(R.color.theme_color))
        tv_select_1.setBackgroundColor(getTextColor(R.color.theme_color))

        tv_capture.setTextColor(getTextColor(R.color.black))
        tv_select_2.setBackgroundColor(getTextColor(R.color.white))
    }

    private fun selection2() {
        tv_jugde.setTextColor(getTextColor(R.color.black))
        tv_select_1.setBackgroundColor(getTextColor(R.color.white))

        tv_capture.setTextColor(getTextColor(R.color.theme_color))
        tv_select_2.setBackgroundColor(getTextColor(R.color.theme_color))
    }


    override fun getLayout(): Int {
        return R.layout.activity_inspect
    }


}