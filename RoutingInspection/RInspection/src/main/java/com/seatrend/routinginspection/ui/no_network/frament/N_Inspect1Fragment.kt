package com.seatrend.routinginspection.ui.no_network.frament

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.adapter.InspectAdapter
import com.seatrend.routinginspection.base.BaseFragment
import com.seatrend.routinginspection.base.Constants
import com.seatrend.routinginspection.db.DbUtils
import com.seatrend.routinginspection.entity.JudgeTaskEntity
import com.seatrend.routinginspection.ui.no_network.N_InspectActivity.Companion.JHZT
import com.seatrend.routinginspection.utils.GsonUtils
import kotlinx.android.synthetic.main.fragment_inspect1.*

/**
 * Created by ly on 2020/7/7 17:59
 */
class N_Inspect1Fragment : BaseFragment() {

    private var ll: LinearLayoutManager? = null
    private var adapter: InspectAdapter? = null

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_inspect1, container, false)
    }

    override fun initView() {
        initRecycleView()
        getData()
    }

    private fun getData() {
        showLoadingDialog()

        val mData = getChangedTypeFromDb()


        adapter!!.setData(mData)
        //备注
        val planEntity = DbUtils.getInstance(context).searchPlanByLsh(activity!!.intent.getStringExtra(Constants.JHBH))
        showLog(" N_Inspect1Fragment list" + GsonUtils.toJson(planEntity))
        if (planEntity != null) {
            ed_bz.setText(planEntity.jhbz)
        }

        dismissLoadingDialog()
    }


    //s数据库的实体转为list的实体
    private fun getChangedTypeFromDb(): ArrayList<JudgeTaskEntity.DataBean> {
        val listJudge =
            DbUtils.getInstance(context).searchJudgeByWhere(activity!!.intent.getStringExtra(Constants.JHBH))
        //为什么这样 目的是脱离开
        val data = ArrayList<JudgeTaskEntity.DataBean>()
        for (db in listJudge) {
            synchronized(db) {
                val entity = JudgeTaskEntity.DataBean()
                entity.rwzxjg = db.rwzxjg
                entity.jhbh = db.lsh
                entity.rwmc = db.rwmc
                entity.rwbh = db.rwbh
                entity.rwcjr = db.rwcjr
                entity.glbm = db.glbm
                entity.rwcjsj = db.rwcjsj
                entity.rwms = db.rwms
                entity.rwxgsj = db.rwxgsj
                entity.rwzt = db.rwzt
                entity.rwzxr = db.rwzxr
                data.add(entity)
            }

        }
        return data
    }

    private fun initRecycleView() {
        ll = LinearLayoutManager(context)
        recycle_view!!.layoutManager = ll
        adapter = InspectAdapter(context!!)
        adapter!!.setJHZT(JHZT!!)
        recycle_view.adapter = adapter
    }

    fun getChangedBzData(): String {
        return ed_bz.text.toString()

    }

    fun getChangedData(): ArrayList<JudgeTaskEntity.DataBean> {
        return adapter!!.getChangedData()
    }

    override fun bindEvent() {

    }
}