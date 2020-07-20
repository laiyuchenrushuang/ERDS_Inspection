package com.seatrend.routinginspection.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.seatrend.http_sdk.NormalView
import com.seatrend.http_sdk.base.ConmmonResponse
import com.seatrend.http_sdk.inter.base.BaseService
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.adapter.DetailJugdeAdapter
import com.seatrend.routinginspection.adapter.DetailPictureAdapter
import com.seatrend.routinginspection.adapter.MyFragmentPagerAdapter
import com.seatrend.routinginspection.base.BaseActivity
import com.seatrend.routinginspection.base.BaseFragment
import com.seatrend.routinginspection.base.Constants
import com.seatrend.routinginspection.entity.ZpdzListEntity
import com.seatrend.routinginspection.http.HttpRequest
import com.seatrend.routinginspection.model.RecordDetailModel
import com.seatrend.routinginspection.ui.fragment.DetailFragment1
import com.seatrend.routinginspection.ui.fragment.DetailFragment2
import com.seatrend.routinginspection.ui.fragment.Inspect2Fragment
import com.seatrend.routinginspection.utils.GsonUtils
import com.seatrend.routinginspection.utils.StringUtils
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.detail_fragment1.*
import kotlinx.android.synthetic.main.detail_fragment2.*

class DetailActivity :BaseActivity(){
    private val F1 = 0
    private val F2 = 1
    var fragmentList = ArrayList<BaseFragment>()
    var fragment1 = DetailFragment1()  //巡检判定fragment
    var fragment2 = DetailFragment2()  //巡检拍照fragment
    var viewPager: ViewPager? = null

    override fun initView() {
        setPageTitle(resources.getString(R.string.details))
        initViewPager()
        setDefaultSate()
        httpGetPhoto()
    }

    private fun httpGetPhoto() {
        showLoadingDialog()
        val map = HashMap<String,String>()
        val jhbh = StringUtils.isNull(intent.getStringExtra(Constants.JHBH))
        val glbm = StringUtils.isNull(intent.getStringExtra(Constants.GLBM))

        map[Constants.JHBH] = jhbh
        map[Constants.GLBM] = glbm
        HttpRequest.geT(Constants.URL.GET_PHOTO_LIST,map,BaseService::class.java,true,object :NormalView{
            override fun netWorkTaskSuccess(commonResponse: ConmmonResponse?) {
                dismissLoadingDialog()
                val entity = GsonUtils.gson(commonResponse!!.responseString,ZpdzListEntity::class.java)
                if(entity.data.size >0){

                    fragment2.setPhoto(entity.data)
                }
            }

            override fun netWorkTaskfailed(commonResponse: ConmmonResponse?) {
                dismissLoadingDialog()
//                showErrorDialog(commonResponse!!.responseString)
            }

        })
    }


    override fun bindEvent() {
        tv_jugde.setOnClickListener {
            selection1()
            viewPager!!.setCurrentItem(F1, true)
        }

        tv_capture.setOnClickListener {
            selection2()
            viewPager!!.setCurrentItem(F2, true)
        }

        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                showLog(position)
                when (position) {
                    F1 -> {
                        selection1()
                    }
                    F2 -> {
                        selection2()
                    }

                }
            }

        })
    }

    private fun setDefaultSate() {
        selection1()
    }

    private fun initViewPager() {
        fragmentList.add(fragment1)
        fragmentList.add(fragment2)
        val myFragmentPagerAdapter = MyFragmentPagerAdapter(supportFragmentManager, fragmentList)
        viewPager = findViewById(R.id.viewpager)
        viewPager!!.adapter = myFragmentPagerAdapter
    }

    private fun selection1() {
        tv_jugde.setTextColor(getTextColor(R.color.theme_color))
        tv_select_1.setBackgroundColor(getTextColor(R.color.theme_color))

        tv_capture.setTextColor(getTextColor(R.color.black))
        tv_select_2.setBackgroundColor(getTextColor(R.color.white))
    }

    private fun selection2() {
        tv_jugde.setTextColor(getTextColor(R.color.black))
        tv_select_1.setBackgroundColor(getTextColor(R.color.white))

        tv_capture.setTextColor(getTextColor(R.color.theme_color))
        tv_select_2.setBackgroundColor(getTextColor(R.color.theme_color))
    }


    override fun getLayout(): Int {
        return R.layout.activity_details
    }

}
