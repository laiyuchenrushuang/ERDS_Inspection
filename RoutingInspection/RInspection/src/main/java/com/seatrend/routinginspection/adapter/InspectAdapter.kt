package com.seatrend.routinginspection.adapter
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.seatrend.routinginspection.entity.JudgeTaskEntity


/**
 * Created by ly on 2020/6/28 17:20
 */
class InspectAdapter(private var mContext: Context) : RecyclerView.Adapter<InspectAdapter.MyViewHolder>() {

    private var mData = ArrayList<JudgeTaskEntity.DataBean>()
    private var JHZT = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext)
            .inflate(com.seatrend.routinginspection.R.layout.inspect_jugde_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder!!.initItemView(mData[position])
    }

    fun setData(data: ArrayList<JudgeTaskEntity.DataBean>) {
        this.mData = data
        notifyDataSetChanged()
    }

    fun setJHZT(jhzt: String) {
        this.JHZT = jhzt
    }

    fun getChangedData(): ArrayList<JudgeTaskEntity.DataBean> {
        return mData
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        private var tv_item_xh: TextView? = null
        private var tv_item_mc: TextView? = null
        private var cb_jugde: CheckBox? = null
        fun initItemView(bean: JudgeTaskEntity.DataBean) {
            tv_item_xh = itemView.findViewById(com.seatrend.routinginspection.R.id.tv_item_xh)
            tv_item_mc = itemView.findViewById(com.seatrend.routinginspection.R.id.tv_item_mc)
            cb_jugde = itemView.findViewById(com.seatrend.routinginspection.R.id.cb_jugde)

            tv_item_xh!!.text = (adapterPosition + 1).toString()
            tv_item_mc!!.text = bean.rwmc
            if ("0" == bean.rwzxjg) {
                cb_jugde!!.text = mContext.resources.getString(com.seatrend.routinginspection.R.string.jugde_no)
                cb_jugde!!.isActivated = true
                mData[adapterPosition].rwzxjg = "0"

                setTextDefault(cb_jugde!!)
            } else if ("1" == bean.rwzxjg) {
                cb_jugde!!.text = mContext.resources.getString(com.seatrend.routinginspection.R.string.jugde_yes)
                cb_jugde!!.isActivated = false
                cb_jugde!!.isChecked = true
                mData[adapterPosition].rwzxjg = "1"

                setTextLeft(cb_jugde!!)

            } else {
                cb_jugde!!.text = mContext.resources.getString(com.seatrend.routinginspection.R.string.jugde_out)
                if (cb_jugde!!.isChecked) {
                    cb_jugde!!.performClick()  //未通过灰色
                }
                cb_jugde!!.isActivated = false
                cb_jugde!!.isChecked = false
                mData[adapterPosition].rwzxjg = "2"
                setTextRight(cb_jugde!!)
            }
            bindEvent()
        }

        private fun setTextDefault(cb_jugde: CheckBox) {
//           cb_jugde.setBackgroundResource(R.mipmap.ic_determine_not)

            cb_jugde.gravity = Gravity.CENTER
        }

        private fun setTextRight(cb_jugde: CheckBox) {
//            cb_jugde.setBackgroundResource(R.mipmap.ic_determine_unqualified)
            cb_jugde.gravity = Gravity.END or Gravity.CENTER_VERTICAL
            cb_jugde.setPadding(0, 0, 5, 0)
            cb_jugde!!.text = mContext.resources.getString(com.seatrend.routinginspection.R.string.jugde_out)
        }

        private fun setTextLeft(cb_jugde: CheckBox) {
//            cb_jugde.setBackgroundResource(R.mipmap.ic_determine_qualified)
            cb_jugde.gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
            cb_jugde.setPadding(15, 0, 0, 0)
            cb_jugde!!.text = mContext.resources.getString(com.seatrend.routinginspection.R.string.jugde_yes)
        }

        private fun bindEvent() {

            if ("2" == JHZT) {
                cb_jugde!!.isEnabled = false
                cb_jugde!!.setBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        com.seatrend.routinginspection.R.color.white
                    )
                )
            } else {
                cb_jugde!!.isEnabled = true
                cb_jugde!!.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
//                            cb_jugde!!.text = mContext.resources.getString(R.string.jugde_yes)
                        mData[adapterPosition].rwzxjg = "1"
                        cb_jugde!!.isActivated = false
                        setTextLeft(cb_jugde!!)
                    } else {
//                            cb_jugde!!.text = mContext.resources.getString(R.string.jugde_out)
                        mData[adapterPosition].rwzxjg = "2"
                        cb_jugde!!.isActivated = false
                        setTextRight(cb_jugde!!)
                    }
                }
            }
        }
    }
}