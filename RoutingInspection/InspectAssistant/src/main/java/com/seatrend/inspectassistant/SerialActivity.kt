package com.seatrend.inspectassistant

import android.content.Intent
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.seatrend.http_sdk.HttpService
import com.seatrend.inspectassistant.entity.LshEntity
import kotlinx.android.synthetic.main.activity_serial.*
import kotlinx.android.synthetic.main.common_button_next.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by ly on 2020/6/30 10:52
 */
class SerialActivity : BaseActivity() {
    var index = 0
    val ERDS = "蒙"

    override fun initView() {
        setPageTitle(getResourceString(R.string.serial_title))
        btn_next.text = getResourceString(R.string.serial_search)

        initData()
    }

    private fun initData() {
        initSpinerData(cs_hpzl)
        initSpinerData(cs_hphm)

        ed_clsbdh.transformationMethod = DataModel.TransInformation()
        ed_hphm.transformationMethod = DataModel.TransInformation()


        ed_hphm.setText("A")
        cs_hphm.setSelection(index)
    }

    private fun initSpinerData(spinner: Spinner?) {
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(R.layout.item_spinner__down_common)
        when (spinner!!.id) {
            R.id.cs_hpzl -> {

                adapter.add("01:大型汽车")
                adapter.add("02:小型汽车")
                adapter.add("03:使馆汽车")
                adapter.add("04:领馆汽车")
                adapter.add("05:境外汽车")
                adapter.add("06:外籍汽车")
                adapter.add("07:普通摩托车")
                adapter.add("08:轻便摩托车")
                adapter.add("09:使馆摩托车")
                adapter.add("10:领馆摩托车")
                adapter.add("11:境外摩托车")
                adapter.add("12:外籍摩托车")
                adapter.add("13:低速车")
                adapter.add("14:拖拉机")
                adapter.add("15:挂车")
                adapter.add("16:教练汽车")
                adapter.add("17:教练摩托车")
                adapter.add("18:试验汽车")
                adapter.add("19:试验摩托车")
                adapter.add("20:临时入境汽车")
                adapter.add("21:临时入境摩托车")
                adapter.add("22:临时行驶车")
                adapter.add("23:警用汽车")
                adapter.add("24:警用摩托")
                adapter.add("25:原农机号牌")
                adapter.add("26:香港入出境车")
                adapter.add("27:澳门入出境车")
                adapter.add("28:武警号牌")
                adapter.add("32:军队号牌")
                adapter.add("41:无号牌")
                adapter.add("42:假号牌")
                adapter.add("43:挪用号牌")
                adapter.add("51:大型新能源汽车")
                adapter.add("52:小型新能源汽车")
                adapter.add("99:其他号牌")
                spinner.adapter = adapter
            }
            R.id.cs_hphm -> {
                val list = DataModel.getProvince()
                for (index in 0 until list.size) {

                    if (ERDS == list[index]) {
                        this.index = index
                    }
                    adapter.add(list.get(index))
                }
                spinner.adapter = adapter
            }
        }
    }

    override fun bindEvent() {
        btn_next.setOnClickListener {


            val map = HashMap<String, String>()
            map["lsh"] = if (!TextUtils.isEmpty(ed_lsh.text.toString())) ed_lsh.text.toString() else ""

            map["hphm"] = if (!TextUtils.isEmpty(ed_hphm.text.toString())) ed_hphm.text.toString().toUpperCase() else ""
            map["clsbdh"] =
                if (!TextUtils.isEmpty(ed_clsbdh.text.toString())) ed_clsbdh.text.toString().toUpperCase() else ""

            LoadingDialog.getInstance().showLoadDialog(this)
            val service = HttpService.getInstance()
                .getApiService(SharedPreferencesUtils.getNetworkAddress() + "/", QuestService::class.java, false)

            val call = service.getServer(Constants.GET_LSH, map)
            call.enqueue(object : Callback<LshEntity> {
                override fun onFailure(call: Call<LshEntity>, t: Throwable) {
                    LoadingDialog.getInstance().dismissLoadDialog()
                }

                override fun onResponse(call: Call<LshEntity>, response: Response<LshEntity>) {
                    LoadingDialog.getInstance().dismissLoadDialog()
                    val result = response.body()

                    if (result != null && result.data != null) {
                        intent.setClass(this@SerialActivity, SearchResultActivity::class.java)
                        intent.putExtra("result", result)
                        startActivity(intent)
                    } else {
                        showToast("查询信息为空")
                    }

                }

            })

        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_serial
    }
}