package com.seatrend.inspectassistant

import android.view.View
import com.seatrend.http_sdk.base.GsonUtils
import com.seatrend.inspectassistant.entity.QCodeEntity
import kotlinx.android.synthetic.main.activity_oder.*

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

            if("1" == entity.zt){
                name.text = StringUtils.isNulls(entity.xm)
                yw.text = StringUtils.isNulls(entity.ywlxmc)
                jg.text = StringUtils.isNulls(entity.cyqmc)
                time.text = StringUtils.isNulls(entity.yyrqmc)
                bh.text = StringUtils.isNulls(entity.bh)
                jkc.text = if("1" == entity.gcjk) "国产" else "进口"

                if("2" == entity.gcjk && "A" == entity.dmsm2){ //进口车
                    ll_zcjk.visibility = View.VISIBLE
                    clsbdh.text = StringUtils.isNulls(entity.clsbdh)
                    fdjh.text = StringUtils.isNulls(entity.fdjh)
                    ccrq.text = StringUtils.isNulls(entity.ccrq)
                    qfrq.text = StringUtils.isNulls(entity.qfrq)
                }

                if("D" == entity.dmsm2 || "B" == entity.dmsm2){
                    ll_zy.visibility = View.VISIBLE
                    cph.text = StringUtils.isNulls(entity.hphm)
                    djzsbh.text = StringUtils.isNulls(entity.djzsbh)
                }
            }else{
                ll_data.visibility = View.GONE
                ll_error.visibility = View.VISIBLE
            }


        } catch (e: Exception) {
            ll_data.visibility = View.GONE
            ll_error.visibility = View.VISIBLE
        }
    }

    override fun bindEvent() {
    }

    override fun getLayout(): Int {
        return R.layout.activity_oder
    }
}