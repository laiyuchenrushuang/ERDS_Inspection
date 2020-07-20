package com.seatrend.routinginspection.ui

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.base.BaseActivity
import kotlinx.android.synthetic.main.activity_picture.*

/**
 * Created by ly on 2020/5/8 18:14
 */
class ShowPictureActivity: BaseActivity() {
    override fun bindEvent() {
    }

    @SuppressLint("CheckResult")
    override fun initView() {
        setPageTitle("图片详情")
        if(null != intent.getStringExtra("zpmc")){
            setPageTitle(intent.getStringExtra("zpmc"))
        }
        img.enable()//启动缩放
        showLog(intent.getStringExtra("photo_path"))
        val requestOptions =  RequestOptions()
        requestOptions.error(R.mipmap.no_data)
//            .circleCrop()
            .skipMemoryCache(true)
            .diskCacheStrategy( DiskCacheStrategy.NONE );

        Glide.with(this).load(intent.getStringExtra("photo_path")).apply(requestOptions).into(img)
    }

    override fun getLayout(): Int {
        return R.layout.activity_picture
    }
}