package com.seatrend.routinginspection.ui.fragment

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.seatrend.routinginspection.adapter.DetailJugdeAdapter
import com.seatrend.routinginspection.adapter.DetailPictureAdapter
import com.seatrend.routinginspection.base.BaseFragment
import com.seatrend.routinginspection.base.Constants
import com.seatrend.routinginspection.model.RecordDetailModel
import com.seatrend.routinginspection.utils.StringUtils
import kotlinx.android.synthetic.main.detail_fragment1.*
import kotlinx.android.synthetic.main.detail_fragment2.*

/**
 * Created by ly on 2020/7/16 16:35
 */
class DetailFragment1:BaseFragment() {

    private var ll: LinearLayoutManager? = null
    private var adapter_pd: DetailJugdeAdapter? = null

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(com.seatrend.routinginspection.R.layout.detail_fragment1, container, false)
    }

    override fun initView() {
        initRecycleView()
        getData()
    }


    private fun initRecycleView() {
        ll = LinearLayoutManager(context)
        recycle_view_pd!!.layoutManager = ll
        adapter_pd = DetailJugdeAdapter(context!!)
        recycle_view_pd.adapter = adapter_pd
    }


    private fun getData() {
        try {
            val recordData = RecordDetailModel.getDetail()
            adapter_pd!!.setData(recordData.data.planTaskListObject)
            tv_bz.text = recordData.data.stationPlanList[0].jhwcbz
            if(TextUtils.isEmpty(recordData.data.stationPlanList[0].jhwcbz)){
                tv_bz.visibility = View.INVISIBLE
            }
            tv_jh_date.text = StringUtils.longToStringDataNoHour(recordData.data.jhcjrq)
            tv_xj_date.text = StringUtils.longToStringData(recordData.data.stationPlanList[0].jhwcsj)
            tv_xj_xzr.text = activity!!.intent.getStringExtra(Constants.XZR)
        } catch (e: Exception) {
            showToast(e.message.toString())

        }

    }

    override fun bindEvent() {
    }
}