package com.seatrend.routinginspection.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.seatrend.routinginspection.adapter.DetailJugdeAdapter
import com.seatrend.routinginspection.adapter.DetailPictureAdapter
import com.seatrend.routinginspection.base.BaseFragment
import com.seatrend.routinginspection.entity.ZpdzListEntity
import kotlinx.android.synthetic.main.detail_fragment1.*
import kotlinx.android.synthetic.main.detail_fragment2.*

/**
 * Created by ly on 2020/7/16 16:35
 */
class DetailFragment2 :BaseFragment(){

    private var adapter_tp: DetailPictureAdapter? = null

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(com.seatrend.routinginspection.R.layout.detail_fragment2, container, false)
    }

    override fun initView() {
        initRecycleView()
    }

    private fun initRecycleView() {
        recycle_view_tp!!.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        adapter_tp = DetailPictureAdapter(context)
        recycle_view_tp.adapter = adapter_tp
    }

    override fun bindEvent() {
    }

    fun setPhoto(data: ArrayList<ZpdzListEntity.DataBean>) {
        adapter_tp!!.setPhoto(data)
    }
}