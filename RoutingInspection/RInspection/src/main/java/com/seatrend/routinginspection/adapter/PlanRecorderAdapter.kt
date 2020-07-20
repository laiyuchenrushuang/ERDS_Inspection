package com.seatrend.routinginspection.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.seatrend.http_sdk.NormalView
import com.seatrend.http_sdk.base.ConmmonResponse
import com.seatrend.http_sdk.inter.base.BaseService
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.base.Constants
import com.seatrend.routinginspection.entity.RecordDetailEntity
import com.seatrend.routinginspection.entity.RecordEntity
import com.seatrend.routinginspection.http.HttpRequest
import com.seatrend.routinginspection.model.RecordDetailModel
import com.seatrend.routinginspection.ui.DetailActivity
import com.seatrend.routinginspection.ui.InspectActivity
import com.seatrend.routinginspection.utils.GsonUtils
import com.seatrend.routinginspection.utils.JHStateUtils
import com.seatrend.routinginspection.utils.LoadingDialog
import com.seatrend.routinginspection.utils.StringUtils

/**
 * Created by ly on 2020/6/29 13:21
 */
class PlanRecorderAdapter(var mContext: Context) : RecyclerView.Adapter<PlanRecorderAdapter.MyViewHolder>() {

    private var mData = ArrayList<RecordEntity.DataBean.ListBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(mContext).inflate(R.layout.plan_recorder_item_view, parent, false)
        return MyViewHolder(view)

    }

    fun setData(data: ArrayList<RecordEntity.DataBean.ListBean>) {
        this.mData = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PlanRecorderAdapter.MyViewHolder, position: Int) {
        holder.initView(mData[position])
    }


    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        private var sName: TextView? = null
        private var sState: TextView? = null
        private var tv_station_time: TextView? = null
        private var ll_item: LinearLayout? = null

        fun initView(station: RecordEntity.DataBean.ListBean) {
            sName = itemView.findViewById(R.id.tv_station_name)
            sState = itemView.findViewById(R.id.tv_state)
            tv_station_time = itemView.findViewById(R.id.tv_station_time)
            ll_item = itemView.findViewById(R.id.ll_item)

            //设置数据
            sName!!.text = station.bmmc
            sState!!.text = "详情"
            tv_station_time!!.text = "巡站完成日期："+StringUtils.longToStringDataNoHour(station.xzrq)
//            if ("2".equals(station.jhzt)) {
//                sState!!.setTextColor(ContextCompat.getColor(mContext, R.color.green))
//            } else {
//                sState!!.setTextColor(ContextCompat.getColor(mContext, R.color.black))
//            }
            bindEvent()
        }

        private fun bindEvent() {
            ll_item!!.setOnClickListener {

                val map = HashMap<String, String>()
                map[Constants.JHBH] = mData[adapterPosition].jhbh
                map[Constants.GLBM] = mData[adapterPosition].glbm
                LoadingDialog.getInstance().dismissLoadDialog()
                HttpRequest.geT(
                    Constants.URL.GET_RECORD_DETAIL,
                    map,
                    BaseService::class.java,
                    true,
                    object : NormalView {
                        override fun netWorkTaskSuccess(commonResponse: ConmmonResponse?) {

                            try {
                                val entity =
                                    GsonUtils.gson(commonResponse!!.responseString, RecordDetailEntity::class.java);

                                if (entity.data.planTaskListObject != null && entity.data.planTaskListObject.size > 0) {

                                    RecordDetailModel.setDetail(commonResponse.responseString)


                                    val intent = Intent(mContext, DetailActivity::class.java)
                                    intent.putExtra(Constants.JHBH, mData[adapterPosition].jhbh)
                                    intent.putExtra(Constants.GLBM, mData[adapterPosition].glbm)
                                    intent.putExtra(Constants.XZR, mData[adapterPosition].jhzxr)
                                    mContext.startActivity(intent)
                                }

                            } catch (e: Exception) {
                                Toast.makeText(mContext, e.message.toString(), Toast.LENGTH_SHORT).show()

                            }
                        }

                        override fun netWorkTaskfailed(commonResponse: ConmmonResponse?) {
                            LoadingDialog.getInstance().dialogShowing()
                            Toast.makeText(mContext, commonResponse!!.responseString, Toast.LENGTH_SHORT).show()
                        }

                    })

            }
        }
    }
}