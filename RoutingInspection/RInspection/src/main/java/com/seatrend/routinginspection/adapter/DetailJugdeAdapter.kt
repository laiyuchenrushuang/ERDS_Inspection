package com.seatrend.routinginspection.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.entity.JudgeTaskEntity
import com.seatrend.routinginspection.entity.RecordDetailEntity

/**
 * Created by ly on 2020/7/6 10:07
 */
class DetailJugdeAdapter(var mContext: Context):RecyclerView.Adapter<DetailJugdeAdapter.MyViewHolder>() {
    private var mData = ArrayList<RecordDetailEntity.DataBean.PlanTaskListObjectBean>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.fragment_detail_judge,parent,false)
        return MyViewHolder(view)
    }



    fun setData(data: ArrayList<RecordDetailEntity.DataBean.PlanTaskListObjectBean>) {
        this.mData = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.initItemView(mData[position])
    }


    inner class MyViewHolder(view: View) :RecyclerView.ViewHolder(view){


        private var tv_item_xh: TextView? = null
        private var tv_item_mc: TextView? = null
        private var cb_jugde: TextView? = null
        fun initItemView(s: RecordDetailEntity.DataBean.PlanTaskListObjectBean) {
            tv_item_xh = itemView.findViewById(R.id.tv_item_xh)
            tv_item_mc = itemView.findViewById(R.id.tv_item_mc)
            cb_jugde = itemView.findViewById(R.id.cb_jugde)

            tv_item_xh!!.text = ""+(adapterPosition+1)
            tv_item_mc!!.text = s.rwmc
            cb_jugde!!.text = if ("0" == s.rwzxjg) {
                mContext.resources.getString(R.string.jugde_no)
            } else if("1" == s.rwzxjg){
                mContext.resources.getString(R.string.jugde_yes)
            }else{
                mContext.resources.getString(R.string.jugde_out)
            }

            if("2" == s.rwzxjg){
                cb_jugde!!.setTextColor(ContextCompat.getColor(mContext,R.color.red))
            }

        }

    }
}