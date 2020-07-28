package com.seatrend.routinginspection.ui.fragment

import android.text.TextUtils
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.seatrend.http_sdk.NormalView
import com.seatrend.http_sdk.base.ConmmonResponse
import com.seatrend.http_sdk.inter.base.BaseService
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.adapter.PlanAdapter
import com.seatrend.routinginspection.base.BaseFragment
import com.seatrend.routinginspection.base.Constants
import com.seatrend.routinginspection.entity.PLANEntity
import com.seatrend.routinginspection.http.HttpRequest
import com.seatrend.routinginspection.utils.DP2PX
import com.seatrend.routinginspection.utils.GsonUtils
import kotlinx.android.synthetic.main.fragment_paln1.*


/**
 * Created by ly on 2020/6/28 14:34
 */
class RIPaln1Fragment : BaseFragment() {

    private var ll: LinearLayoutManager? = null
    private var adapter: PlanAdapter? = null

    var cacheData = ArrayList<PLANEntity.DataBean.TASK>()  //动态的缓存数据

    var page = 1  //当前页
    var pagesize = 10  //个数

    var mData = ArrayList<PLANEntity.DataBean.TASK>()


    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_paln1, container, false)
    }

    override fun initView() {
        initRecycleView()
        mData.clear()

    }

    override fun onResume() {
        super.onResume()
        showLog(" plan f1 onresume ")
        getData()
    }

    private fun initRecycleView() {
        ll = LinearLayoutManager(context)
        recycle_view!!.layoutManager = ll
        adapter = PlanAdapter(context!!)
        recycle_view.adapter = adapter
    }

    private fun getData(): ArrayList<PLANEntity.DataBean.TASK> {

        val map = HashMap<String, String>()
        map["curPage"] = "" + page
        map["pageSize"] = "" + pagesize
        map["xzlx"] = "0"
        map["jhzt"] = "" + 0  //计划状态(0待执行，1执行中，2执行完成)

        showLoadingDialog()
        HttpRequest.geT(Constants.URL.GET_PALN_TASK_LIST, map, BaseService::class.java, true, object : NormalView {
            override fun netWorkTaskSuccess(commonResponse: ConmmonResponse?) {
                dismissLoadingDialog()
                try {
                    val entity = GsonUtils.gson(commonResponse!!.responseString, PLANEntity::class.java)
                    if (page == 1) {
                        shuaxin.finishRefresh()
                        mData.clear()
                        if (entity?.data != null && entity.data.list != null && entity.data.list.size > 0) {
                            mData = entity.data.list as ArrayList<PLANEntity.DataBean.TASK>
                        }
                        adapter!!.setData(mData)

                    } else {
                        shuaxin.finishLoadmore()
                        mData.addAll(entity.data.list)

                        adapter!!.setData(mData)
                    }
                } catch (e: Exception) {
                    showToast(e.message.toString())
                }
            }

            override fun netWorkTaskfailed(commonResponse: ConmmonResponse?) {
                showErrorDialog(commonResponse!!.responseString)
                dismissLoadingDialog()
            }

        })
        return mData
    }

    fun setPlanNoNetwork(flag: Boolean) {
        page = 1
        getData()
        adapter!!.setAddModel(flag)
    }

    override fun bindEvent() {
//        shuaxin.isEnableRefresh = false //取消下拉刷新
        shuaxin.setOnLoadmoreListener {
            page++
            getData()
        }
        shuaxin.setOnRefreshListener {
            page = 1
            getData()
        }

        //点击扩展
        search_view.setOnClickListener {
            search_view.onActionViewExpanded()
        }

        search_view.imeOptions = EditorInfo.IME_ACTION_DONE//键盘完成
        search_view.queryHint = resources.getString(R.string.search_tip) //搜索提示
        setUnderLinetransparent(search_view) //去掉下划线

        val argClass = search_view.javaClass
        // mSearchPlate是SearchView父布局的名字
        val ownField = argClass.getDeclaredField("mSearchSrcTextView")
        ownField.isAccessible = true
        val mView = ownField.get(search_view) as TextView

        mView.textSize = 16.5f
        mView.setOnEditorActionListener { v, _, event ->

            val query = v.text.toString()
            showLog(" 提交")
            showLog(" 提交  $query")

            if (!TextUtils.isEmpty(query)) {
                val map = HashMap<String, String>()
                map["curPage"] = "1"
                map["pageSize"] = "20"
                map["xzlx"] = "0"
                map["jhzt"] = "0" //计划状态(0待执行，1执行中，2执行完成)
                map["bmmc"] = query!! //部门名称
                HttpRequest.geT(
                    Constants.URL.GET_PALN_TASK_LIST,
                    map,
                    BaseService::class.java,
                    true,
                    object : NormalView {
                        override fun netWorkTaskSuccess(commonResponse: ConmmonResponse?) {

                            try {
                                val entity = GsonUtils.gson(commonResponse!!.responseString, PLANEntity::class.java)

                                if (entity?.data != null && entity.data.list.isNotEmpty() && entity.data.list.size > 0) {
                                    val data = entity.data.list as ArrayList<PLANEntity.DataBean.TASK>
                                    adapter!!.setData(data)
                                } else {
                                    adapter!!.clearData()
                                }

                            } catch (e: Exception) {
                                showToast(e.message.toString())
                                adapter!!.clearData()
                                showToast(resources.getString(R.string.search_null))
                            }
                            dismissLoadingDialog()
                        }

                        override fun netWorkTaskfailed(commonResponse: ConmmonResponse?) {
                            showErrorDialog(commonResponse!!.responseString)
                            showToast(resources.getString(R.string.search_null))
                            adapter!!.clearData()
                            dismissLoadingDialog()
                        }

                    })
            } else {
                page = 1 //恢复默认
                adapter!!.setData(getData())
            }
            true
        }
    }

}