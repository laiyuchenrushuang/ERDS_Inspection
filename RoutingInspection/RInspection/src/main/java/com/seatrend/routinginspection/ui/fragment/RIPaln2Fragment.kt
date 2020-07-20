package com.seatrend.routinginspection.ui.fragment

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.seatrend.http_sdk.NormalView
import com.seatrend.http_sdk.base.ConmmonResponse
import com.seatrend.http_sdk.inter.base.BaseService
import com.seatrend.routinginspection.adapter.PlanRecorderAdapter
import com.seatrend.routinginspection.base.BaseFragment
import com.seatrend.routinginspection.base.Constants
import com.seatrend.routinginspection.entity.RecordEntity
import com.seatrend.routinginspection.http.HttpRequest
import com.seatrend.routinginspection.model.LoginModel
import com.seatrend.routinginspection.utils.GsonUtils
import kotlinx.android.synthetic.main.fragment_paln2.*
import android.app.DatePickerDialog
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.utils.StringUtils
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 * Created by ly on 2020/6/28 14:34
 */
class RIPaln2Fragment : BaseFragment() {
    private var ll: LinearLayoutManager? = null
    private var adapter: PlanRecorderAdapter? = null
    var cacheData = ArrayList<RecordEntity.DataBean.ListBean>()  //动态的缓存数据


    var page = 1  //当前页
    var pagesize = 10  //个数

    var mData = ArrayList<RecordEntity.DataBean.ListBean>()

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(com.seatrend.routinginspection.R.layout.fragment_paln2, container, false)
    }

    override fun initView() {
//        ll_nodata.visibility = View.VISIBLE
        initRecycleView()
        mData.clear()
    }

    override fun onResume() {
        super.onResume()
        showLog(" plan f2 onresume ")
        getData(StringUtils.longToStringDataNoHour(System.currentTimeMillis()))
    }

    private fun initRecycleView() {
        ll = LinearLayoutManager(context)
        recycle_view!!.layoutManager = ll!!
        adapter = PlanRecorderAdapter(context!!)
        recycle_view.adapter = adapter
    }

    private fun getData(time: String): ArrayList<RecordEntity.DataBean.ListBean> {
        val loginEntity = LoginModel.getLogin()
        val map = HashMap<String, String>()
        map["curPage"] = "" + page
        map["pageSize"] = "" + pagesize
        map["xzlx"] = "0"
        map["jhzt"] = "2" //计划状态(0待执行，1执行中，2执行完成)

        if ("1" != loginEntity.applicationUser.sysUser.managered) {
            map["jhzxr"] = loginEntity.applicationUser.sysUser.xm
        }

        if (!TextUtils.isEmpty(time)) {
            map["jhwcsj"] = time
        }


//        showLoadingDialog()
        HttpRequest.geT(Constants.URL.GET_RECORD_LIST, map, BaseService::class.java, true, object : NormalView {
            override fun netWorkTaskSuccess(commonResponse: ConmmonResponse?) {
                try {
                    val entity = GsonUtils.gson(commonResponse!!.responseString, RecordEntity::class.java)
                    if (page == 1) {
                        shuaxin.finishRefresh()
                        if (entity?.data != null && entity.data.list.isNotEmpty() && entity.data.list.size > 0) {
                            mData = entity.data.list as ArrayList<RecordEntity.DataBean.ListBean>
                        }
//                        else{
//                            showToast(resources.getString(R.string.no_data))
//                        }
                        adapter!!.setData(mData)
                    } else {
                        shuaxin.finishLoadmore()
                        mData.addAll(entity.data.list)

                        adapter!!.setData(mData)
                    }
                } catch (e: Exception) {
                    showToast(e.message.toString())
                }
                dismissLoadingDialog()
            }

            override fun netWorkTaskfailed(commonResponse: ConmmonResponse?) {
                showErrorDialog(commonResponse!!.responseString)
                dismissLoadingDialog()
            }

        })
        return mData

    }

    override fun bindEvent() {
//        shuaxin.isEnableRefresh = false //取消下拉刷新
        shuaxin.setOnLoadmoreListener {
            page++
            showToast(search_view.text.toString())
            getData(search_view.text.toString())
        }
        shuaxin.setOnRefreshListener {
            page = 1
            getData(StringUtils.longToStringDataNoHour(System.currentTimeMillis()))
        }

        search_view.setOnClickListener {
            val calender = Calendar.getInstance()
            val dialog = DatePickerDialog(
                context!!,
                R.style.MyDatePickerDialogTheme,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    var mouth1: String
                    var day1: String
                    if (monthOfYear < 9) {
                        mouth1 = "0" + (monthOfYear + 1)
                    } else {
                        mouth1 = (monthOfYear + 1).toString()
                    }
                    if (dayOfMonth <= 9) {
                        day1 = "0$dayOfMonth"
                    } else {
                        day1 = dayOfMonth.toString()
                    }

                    val dateStr = "$year-$mouth1-$day1"
                    search_view.text = dateStr
                    page = 1
                    getData(search_view.text.toString())
                }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }
//
//        //点击扩展
//        search_view.setOnClickListener {
//            search_view.onActionViewExpanded()
//        }
//
//        search_view.imeOptions = EditorInfo.IME_ACTION_DONE//键盘完成
//        search_view.queryHint = resources.getString(R.string.search_tip) //搜索提示
//        setUnderLinetransparent(search_view) //去掉下划线
//        setSearchviewTextsize(search_view,17f)
//
//        //模糊查询
//        search_view.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                showLog(" 提交")
//                showLog(" 提交  $query")
//
//
//
//                if (!TextUtils.isEmpty(query)) {
//                    val map = HashMap<String, String>()
//                    map["curPage"] = "1"
//                    map["pageSize"] = "20"
//                    map["xzlx"] = "0"
//                    map["jhzt"] = "2" //计划状态(0待执行，1执行中，2执行完成)
//                    map["bmmc"] = query!! //部门名称
//                    //        map["glbm"] = loginEntity.applicationUser.sysUser.glbm
//                    HttpRequest.geT(
//                        Constants.URL.GET_PALN_TASK_LIST,
//                        map,
//                        BaseService::class.java,
//                        true,
//                        object : NormalView {
//                            override fun netWorkTaskSuccess(commonResponse: ConmmonResponse?) {
//
//                                try {
//                                    val entity = GsonUtils.gson(commonResponse!!.responseString, RecordEntity::class.java)
//
//                                    if (entity?.data != null && entity.data.list.isNotEmpty() && entity.data.list.size > 0) {
//                                        val data = entity.data.list as ArrayList<RecordEntity.DataBean.ListBean>
//                                        adapter!!.setData(data)
//                                    }
//
//                                } catch (e: Exception) {
//                                    showToast(e.message.toString())
//                                    showToast(resources.getString(R.string.search_null))
//                                }
//                                dismissLoadingDialog()
//                            }
//
//                            override fun netWorkTaskfailed(commonResponse: ConmmonResponse?) {
//                                showErrorDialog(commonResponse!!.responseString)
//                                showToast(resources.getString(R.string.search_null))
//                                dismissLoadingDialog()
//                            }
//
//                        })
//                } else {
//                    page = 1 //恢复默认
//                    adapter!!.setData(getData())
//                }
//
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//
//        })

//        //模糊查询
//        search_view.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//                if (!TextUtils.isEmpty(newText)) {
//                    cacheData.clear()
//                    val dData = getData()
//                    for (db in dData) {
//
//                        if (db.bmmc.contains(newText!!)) {
//                            cacheData.add(db)
//                        }
//
//                    }
//                    adapter!!.setData(cacheData)
//                    if (cacheData.size < 1) {
//                        adapter!!.setData(getData())
//                    }
//                } else {
//                    adapter!!.setData(getData())
//                }
//
//                return false
//            }
//
//        })
    }
}