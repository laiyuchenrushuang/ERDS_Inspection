package com.seatrend.routinginspection.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.seatrend.http_sdk.NormalView
import com.seatrend.http_sdk.base.ConmmonResponse
import com.seatrend.http_sdk.inter.base.BaseService
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.adapter.InspectAdapter
import com.seatrend.routinginspection.base.BaseFragment
import com.seatrend.routinginspection.base.Constants
import com.seatrend.routinginspection.entity.JudgeTaskEntity
import com.seatrend.routinginspection.http.HttpRequest
import com.seatrend.routinginspection.model.LoginModel
import com.seatrend.routinginspection.utils.GsonUtils
import kotlinx.android.synthetic.main.fragment_inspect1.*

/**
 * Created by ly on 2020/6/28 17:12
 */
class Inspect1Fragment : BaseFragment() {
    private var ll: LinearLayoutManager? = null
    private var adapter: InspectAdapter? = null

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_inspect1, container, false)
    }

    override fun initView() {
        initRecycleView()
        getData()
    }

    private fun initRecycleView() {
        ll = LinearLayoutManager(context)
        recycle_view!!.layoutManager = ll
        adapter = InspectAdapter(context!!)
        recycle_view.adapter = adapter
    }

    private fun getData() {

        val map = HashMap<String, String>()
        map[Constants.JHBH] = activity!!.intent.getStringExtra(Constants.JHBH)!!
        map[Constants.GLBM] = activity!!.intent.getStringExtra(Constants.GLBM)!!
        showLoadingDialog()
        HttpRequest.geT(Constants.URL.GET_TASK_LIST, map, BaseService::class.java, true, object : NormalView {
            override fun netWorkTaskSuccess(commonResponse: ConmmonResponse?) {
                dismissLoadingDialog()
                try {
                    val enity = GsonUtils.gson(commonResponse!!.responseString, JudgeTaskEntity::class.java)

                    if (enity != null && enity.data != null && enity.data.size > 0) {
                        adapter!!.setData(enity.data as ArrayList<JudgeTaskEntity.DataBean>)
                    }
                } catch (e: Exception) {
                    showToast(e.message.toString())
                }
            }

            override fun netWorkTaskfailed(commonResponse: ConmmonResponse?) {
                showErrorDialog(commonResponse!!.responseString.toString())
                dismissLoadingDialog()
            }

        })
    }

    //这个在线模式把username赋值进去
    fun getChangedData(): ArrayList<JudgeTaskEntity.DataBean> {
        val data = adapter!!.getChangedData()
        val entity = LoginModel.getLogin()
        val username = try {
            entity.applicationUser.seaSysuser.xm
        } catch (e: Exception) {
            "" //为空的时候 “”
        }
        if (data.size > 0) {
            for(db in data){
                db.rwzxr = username
            }
        }

        return data

    }

    fun getChangedBzData(): String {
        return ed_bz.text.toString()

    }

    override fun bindEvent() {
    }
}