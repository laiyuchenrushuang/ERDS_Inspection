package com.seatrend.inspectassistant

import com.seatrend.inspectassistant.entity.LshEntity
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity : BaseActivity() {
    var lshentity: LshEntity? = null
    override fun initView() {
        setPageTitle(getResourceString(R.string.cs_result))
        lshentity = intent.getSerializableExtra("result") as LshEntity

        getData()
    }

    private fun getData() {
        lsh.text = lshentity!!.data.lsh
        clsbdh.text = lshentity!!.data.clsbdh
        cllx.text = lshentity!!.data.cllx

        cyy.text = lshentity!!.data.cyry
        lszt.text = lshentity!!.data.lszt

        shy.text = lshentity!!.data.syr
        syr.text = lshentity!!.data.syr
        lczt.text = lshentity!!.data.lczt
        cyrq.text = StringUtils.longToStringDataNoHour(lshentity!!.data.cyrq)
    }

    override fun bindEvent() {
    }

    override fun getLayout(): Int {
        return R.layout.activity_search_result
    }

}
