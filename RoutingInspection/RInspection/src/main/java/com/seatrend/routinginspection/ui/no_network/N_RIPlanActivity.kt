package com.seatrend.routinginspection.ui.no_network

import android.annotation.SuppressLint
import android.graphics.Rect
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.adapter.PlanAdapter
import com.seatrend.routinginspection.adapter.SlideAdapter
import com.seatrend.routinginspection.base.BaseActivity
import com.seatrend.routinginspection.base.Constants
import com.seatrend.routinginspection.db.DbUtils
import com.seatrend.routinginspection.db.table.JudgeTable
import com.seatrend.routinginspection.db.table.PlanTable
import com.seatrend.routinginspection.entity.PLANEntity
import com.seatrend.routinginspection.utils.GsonUtils
import com.seatrend.routinginspection.utils.StringUtils
import kotlinx.android.synthetic.main.activity_n_riplan.*

/**
 * Created by ly on 2020/7/7 14:31
 */
class N_RIPlanActivity : BaseActivity() {

    private var ll: LinearLayoutManager? = null
    private var adapter: SlideAdapter? = null
    private var mDbController: DbUtils? = null

    var cacheData = ArrayList<PLANEntity.DataBean.TASK>()  //动态的缓存数据


    override fun initView() {
        setPageTitle(resources.getString(com.seatrend.routinginspection.R.string.plan_tittle))
        mDbController = DbUtils.getInstance(this)
        initRecycleView()

    }

    override fun onResume() {
        super.onResume()
        getDatadefault()
    }

    private fun getDatadefault() {



        val list = mDbController!!.searchPlanTableAll() //数据库的数据

        showLog(GsonUtils.toJson(list))

        val list2 = ArrayList<PLANEntity.DataBean.TASK>()  //list 展示的数据
        for (db in list) {
            val entity = PLANEntity.DataBean.TASK()
            entity.jhbh = db.lsh
            entity.xzrq = db.xzrq
            entity.bmmc = db.bmmc
            entity.jhzt = db.jhzt
            entity.glbm = db.glbm
            list2.add(entity)
        }

        adapter!!.setData(list2)
    }


    private fun getData(): ArrayList<PLANEntity.DataBean.TASK> {

        val list = mDbController!!.searchPlanTableAll()
        val list2 = ArrayList<PLANEntity.DataBean.TASK>()
        list2.clear()
        for (db in list) {
            val entity = PLANEntity.DataBean.TASK()
            entity.jhbh = db.lsh
            entity.xzrq = db.xzrq
            entity.bmmc = db.bmmc
            entity.jhzt = db.jhzt
            list2.add(entity)
        }

        return list2
    }

    @SuppressLint("WrongConstant")
    private fun initRecycleView() {
//        ll = LinearLayoutManager(this)
//        recycle_view!!.layoutManager = ll
//        adapter = PlanAdapter(this)
        adapter = SlideAdapter(this)
//        adapter!!.setAvFlag(Constants.NETWORK.NO_NETWORK.ordinal)
        rv_slide.adapter = adapter

        rv_slide.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_slide.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(@NonNull outRect: Rect, @NonNull view: View, @NonNull parent: RecyclerView, @NonNull state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
//                showToast("滑动了")
                outRect.top = 2
                outRect.bottom = 2
            }
        })
    }


    override fun bindEvent() {

        //点击扩展
        search_view.setOnClickListener {
            search_view.onActionViewExpanded()
        }

        search_view.imeOptions = EditorInfo.IME_ACTION_DONE//键盘完成
        search_view.queryHint = resources.getString(R.string.search_tip) //搜索提示
        setUnderLinetransparent(search_view) //去掉下划线
        setSearchviewTextsize(search_view,17f)

        //模糊查询
        search_view.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (!TextUtils.isEmpty(newText)) {
                    cacheData.clear()
                    val dData = getData()
                    for (db in dData) {

                        if (db.bmmc.contains(newText!!)) {
                            cacheData.add(db)
                        }

                    }
                    adapter!!.setData(cacheData)
                    if (cacheData.size < 1) {
                        adapter!!.setData(getData())
                    }
                } else {
                    adapter!!.setData(getData())
                }

                return false
            }

        })
    }

    override fun getLayout(): Int {
        return R.layout.activity_n_riplan
    }
}
