package com.seatrend.routinginspection.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by ly on 2020/6/28 15:19
 */
class MyFragmentPagerAdapter(fm: FragmentManager, private val mfragmentList: List<Fragment>) :
    FragmentPagerAdapter(fm) {

    //获取集合中的某个项
    override fun getItem(position: Int): Fragment {
        return mfragmentList[position]
    }

    //返回绘制项的数目
    override fun getCount(): Int {
        return mfragmentList.size
    }
}
