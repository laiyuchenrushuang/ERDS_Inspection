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
import com.seatrend.routinginspection.entity.PhotoTypeEntity
import com.seatrend.routinginspection.ui.ShowPictureActivity
import java.util.ArrayList

/**
 * Created by ly on 2020/6/28 16:04
 */
class CheckDataPhotoAdapter(private var mContext: Context? = null) :
    RecyclerView.Adapter<CheckDataPhotoAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_check_data_photo_adapter, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.initItemView(data[position])
    }


    private var data = ArrayList<PhotoTypeEntity>()
    private var JHZT = ""

    fun setPhotoType(list: ArrayList<PhotoTypeEntity>) {
        this.data = list
        notifyDataSetChanged()
    }

    fun setPhoto(position: Int, path: String) {
        data[position].zplj = path
        notifyItemChanged(position)
    }

    fun getDataList(): ArrayList<PhotoTypeEntity> {
        return this.data
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var tvType: TextView? = null
        private var ivPhoto: ImageView? = null
        private var ivDelete: ImageView? = null

        init {
            tvType = itemView.findViewById(R.id.tv_type)
            ivPhoto = itemView.findViewById(R.id.iv_photo)
            ivDelete = itemView.findViewById(R.id.iv_delete)
            ivPhoto!!.setOnClickListener {
                if (mOnClickListener != null && (data[adapterPosition].zplj == "" || data[adapterPosition].zplj == null)) {
                    mOnClickListener!!.itemOnClick(adapterPosition)
                }
                if(data[adapterPosition].zplj !="" && data[adapterPosition].zplj != null){
                    val intent = Intent()
                    intent.setClass(mContext!!, ShowPictureActivity::class.java)
                    intent.putExtra("zpmc", data.get(adapterPosition).zpmc)
                    Log.d("lylog"," photo_path =  "+data.get(adapterPosition).zplj)
                    intent.putExtra("photo_path", data.get(adapterPosition).zplj)
                    mContext!!.startActivity(intent)
                }
            }


            ivDelete!!.setOnClickListener {
                data[adapterPosition].zplj = ""
                notifyItemChanged(adapterPosition)
                mDeleteListener!!.itemdelete(adapterPosition)
            }

        }

        fun initItemView(bean: PhotoTypeEntity) {
            tvType!!.text = bean.zpmc
            tvType!!.setTextColor(Color.BLACK)
            if (bean.zplj != null && bean.zplj !="") {
                Glide.with(mContext!!).load(bean.zplj).centerCrop().error(R.mipmap.error_image).into(ivPhoto!!)
                if("2" == JHZT){
                    ivDelete!!.visibility = View.GONE
                }else{
                    ivDelete!!.visibility = View.VISIBLE
                }

            } else {
                ivPhoto!!.setImageResource(R.mipmap.take_photo)
                ivPhoto!!.scaleType = ImageView.ScaleType.CENTER
                ivDelete!!.visibility = View.GONE
            }
        }
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

    fun setJHZT(jhzt: String?) {
        this.JHZT = ""+jhzt
    }

    interface itemDeleteClickListener {
        fun itemdelete(position: Int)
    }
}