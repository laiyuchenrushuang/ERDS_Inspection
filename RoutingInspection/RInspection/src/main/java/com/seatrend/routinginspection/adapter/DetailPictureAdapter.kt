package com.seatrend.routinginspection.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.base.Constants
import com.seatrend.routinginspection.entity.ZpdzListEntity
import com.seatrend.routinginspection.ui.ShowPictureActivity
import com.seatrend.routinginspection.utils.LogUtil
import java.util.ArrayList

/**
 * Created by ly on 2020/7/6 10:07
 */
class DetailPictureAdapter(private var mContext: Context? = null) :
    RecyclerView.Adapter<DetailPictureAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_check_data_photo_adapter, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.initItemView(data[position])
    }


    private var data = ArrayList<ZpdzListEntity.DataBean>()

    fun setPhoto(list: ArrayList<ZpdzListEntity.DataBean>) {
        this.data = list
        notifyDataSetChanged()
    }

    fun getDataList(): ArrayList<ZpdzListEntity.DataBean> {
        return this.data
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var tvType: TextView? = null
        private var ivPhoto: ImageView? = null

        init {
            tvType = itemView.findViewById(R.id.tv_type)
            ivPhoto = itemView.findViewById(R.id.iv_photo)
            ivPhoto!!.setOnClickListener {
                if (mOnClickListener != null && (data[adapterPosition].zpdz == "" || data[adapterPosition].zpdz == null)) {
                    mOnClickListener!!.itemOnClick(adapterPosition)
                }
                if (data[adapterPosition].zpdz != "" && data[adapterPosition].zpdz != null) {
                    val intent = Intent()
                    intent.setClass(mContext!!, ShowPictureActivity::class.java)
                    intent.putExtra("photo_path", loadUrl(data[adapterPosition].zpdz))
                    mContext!!.startActivity(intent)
                }
            }


        }

        fun initItemView(bean: ZpdzListEntity.DataBean) {
            tvType!!.setTextColor(Color.BLACK)
//            tvType!!.visibility = View.GONE
//            LogUtil.d(loadUrl(bean.zpdz))
            Glide.with(mContext!!)
                .load(loadUrl(bean.zpdz)).centerCrop()
                .error(R.mipmap.error_image).into(ivPhoto!!)
        }
    }


    //http://192.168.0.218:8080/vehicleCheckAsistant/StationPatrol/ftpFile/download?filePath=/home/ftpadmin/patrol/1594794433683183.jpeg
    private fun loadUrl(zpdz: String?): String {
        return Constants.BASE_URL + Constants.URL.GET_PHOTO_FILE + "?" + "filePath=" + zpdz
    }

    private var mOnClickListener: itemOnClickListener? = null

    fun setItemOnClick(listener: itemOnClickListener) {
        mOnClickListener = listener
    }

    interface itemOnClickListener {
        fun itemOnClick(position: Int)
    }

    private var mDeleteListener: itemDeleteClickListener? = null

    fun setItemdeleteClick(listen: itemDeleteClickListener) {
        mDeleteListener = listen
    }

    interface itemDeleteClickListener {
        fun itemdelete(position: Int)
    }
}
