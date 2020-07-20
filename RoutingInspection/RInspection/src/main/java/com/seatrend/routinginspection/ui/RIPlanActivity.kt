package com.seatrend.routinginspection.ui

import android.view.View
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.base.BaseActivity
import com.seatrend.routinginspection.base.BaseFragment
import com.seatrend.routinginspection.ui.fragment.RIPaln1Fragment
import com.seatrend.routinginspection.ui.fragment.RIPaln2Fragment
import kotlinx.android.synthetic.main.activity_plan.*
import kotlinx.android.synthetic.main.common_button_next.*
import androidx.viewpager.widget.ViewPager
import com.seatrend.routinginspection.adapter.MyFragmentPagerAdapter
import kotlinx.android.synthetic.main.common_title.*
import kotlinx.android.synthetic.main.common_title_my.*


/**
 * Created by ly on 2020/6/28 13:26
 */
class RIPlanActivity : BaseActivity() {

    var fragmentList = ArrayList<BaseFragment>()
    var fragment1 = RIPaln1Fragment()  //待检fragment
    var fragment2 = RIPaln2Fragment()  //巡检记录fragment
    var viewPager: ViewPager? = null

    val F1 = 0  //fragment1
    val F2 = 1  //fragment2

    override fun bindEvent() {

        cb_right.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                cb_right.setTextColor(ContextCompat.getColor(this@RIPlanActivity,R.color.sky_blue))
                fragment1.setPlanNoNetwork(isChecked)
            }else{
                cb_right.setTextColor(ContextCompat.getColor(this@RIPlanActivity,R.color.white))
                fragment1.setPlanNoNetwork(isChecked)
            }
        }
        tv_need_ri.setOnClickListener {
            selection1()
            viewPager!!.setCurrentItem(F1,true)
        }

        tv_ri_record.setOnClickListener {
            selection2()
            viewPager!!.setCurrentItem(F2,true)
        }
        viewPager!!.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                showLog(position)
                when(position){
                    F1->{
                        selection1()
                        cb_right.visibility =View.VISIBLE
                    }
                    F2->{
                        selection2()
                        cb_right.visibility =View.GONE
                    }

                }
            }

        })

    }

    override fun initView() {
        setPageTitle(resources.getString(com.seatrend.routinginspection.R.string.plan_tittle))
        btn_next.text = resources.getString(R.string.plan_bottom)
        btn_next.visibility = View.GONE

        cb_right.text = resources.getString(R.string.plan_no_network)
        cb_right.isChecked = false
        setDefaultSate()

        initViewPager()

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
        tv_need_ri.setTextColor(getTextColor(R.color.theme_color))
        tv_select_1.setBackgroundColor(getTextColor( R.color.theme_color))

        tv_ri_record.setTextColor(getTextColor( R.color.black))
        tv_select_2.setBackgroundColor(getTextColor( R.color.white))
    }

    private fun selection2() {
        tv_need_ri.setTextColor(getTextColor( R.color.black))
        tv_select_1.setBackgroundColor(getTextColor( R.color.white))

        tv_ri_record.setTextColor(getTextColor( R.color.theme_color))
        tv_select_2.setBackgroundColor(getTextColor( R.color.theme_color))
    }

    override fun getLayout(): Int {
        return R.layout.activity_plan
    }
}