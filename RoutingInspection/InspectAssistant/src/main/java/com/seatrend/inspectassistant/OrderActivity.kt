package com.seatrend.inspectassistant

import android.view.View
import com.seatrend.http_sdk.base.GsonUtils
import com.seatrend.inspectassistant.entity.QCodeEntity
import kotlinx.android.synthetic.main.activity_oder.*
import kotlinx.android.synthetic.main.activity_search_result.*

/**
 * Created by ly on 2020/6/30 10:54
 */
class OrderActivity :BaseActivity(){
    override fun initView() {
        setPageTitle("预约信息")
        getData()
    }

    private fun getData() {
        try {
            val entity = GsonUtils.gson(intent.getStringExtra("qcode_result"),QCodeEntity::class.java)
            name.text = entity.xm
            yw.text = entity.ywlxmc
            jg.text = entity.cyqmc
            time.text = entity.yyrq
            bh.text = entity.bh
        } catch (e: Exception) {
            done.visibility = View.GONE
        }
    }

    override fun bindEvent() {
    }

    override fun getLayout(): Int {
        return R.layout.activity_oder
    }
}