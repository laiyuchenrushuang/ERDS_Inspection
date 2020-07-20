package com.seatrend.routinginspection.ui.no_network

import android.content.Intent
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.adapter.MyFragmentPagerAdapter
import com.seatrend.routinginspection.base.BaseActivity
import com.seatrend.routinginspection.base.BaseFragment
import com.seatrend.routinginspection.base.Constants
import com.seatrend.routinginspection.base.Constants.Companion.JHZT
import com.seatrend.routinginspection.db.DbUtils
import com.seatrend.routinginspection.db.table.PlanTable
import com.seatrend.routinginspection.ui.AutographActivity
import com.seatrend.routinginspection.ui.no_network.frament.N_Inspect1Fragment
import com.seatrend.routinginspection.ui.no_network.frament.N_Inspect2Fragment
import com.seatrend.routinginspection.utils.GsonUtils
import kotlinx.android.synthetic.main.activity_n_inspect.*
import kotlinx.android.synthetic.main.common_button_next.*

/**
 * Created by ly on 2020/7/7 17:51
 */
class N_InspectActivity : BaseActivity() {
    private val F1 = 0
    private val F2 = 1

    var fragmentList = ArrayList<BaseFragment>()
    var fragment1 = N_Inspect1Fragment()  //巡检判定fragment
    var fragment2 = N_Inspect2Fragment()  //巡检拍照fragment
    var viewPager: ViewPager? = null

    companion object {
        var JHZT: String? = null  //  2是执行完成  1 执行中   0待执行
    }

    override fun initView() {
        JHZT = intent.getStringExtra(Constants.JHZT)  //初始化
        setPageTitle(resources.getString(R.string.inspect_title))
//        btn_next.text = resources.getString(R.string.qianming)
        setDefaultSate()
        initViewPager()


        initUIbyJHZT()
    }

    //计划状态的不同  展示的内容也不同
    private fun initUIbyJHZT() {
        if ("2" == JHZT) {
            btn_next.visibility = View.GONE  // 下一步没有
        }
    }

    override fun bindEvent() {

        btn_next.setOnClickListener {
            saveDataToDb()

        }

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
    }

    private fun saveDataToDb() {

        //存备注信息
        val jhbz = fragment1.getChangedBzData()
        val entity = PlanTable()
        entity.lsh = intent.getStringExtra(Constants.JHBH)
        entity.jhbz = jhbz
        DbUtils.getInstance(this).update(entity)

        showLog(GsonUtils.toJson(DbUtils.getInstance(this).searchPlanByLsh(intent.getStringExtra(Constants.JHBH))))

        //存判定改变结果信息
        val changeList = fragment1.getChangedData()
        for(db in changeList){
            if("0" == db.rwzxjg){
                showToast(getString(R.string.add_tip_2,db.rwmc))
                return
            }
        }

        for(db in changeList){
            synchronized(db){
                showLog(GsonUtils.toJson(db))
                DbUtils.getInstance(this).updateJudge(db.jhbh,db.rwbh,db.rwzxjg)
            }
        }




        intent.setClass(this, AutographActivity::class.java)
        intent.putExtra(Constants.NET_MODEL, Constants.NO_NETWORK)
        startActivity(intent)

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
        return R.layout.activity_n_inspect
    }
}