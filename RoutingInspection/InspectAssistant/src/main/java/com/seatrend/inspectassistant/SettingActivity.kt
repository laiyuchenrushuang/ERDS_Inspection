package com.seatrend.inspectassistant

import kotlinx.android.synthetic.main.activity_setting.*

/**
 * Created by ly on 2020/7/14 10:28
 */
class SettingActivity : BaseActivity() {
    override fun initView() {
        setPageTitle(resources.getString(R.string.setting_title))
        et_network.setText(SharedPreferencesUtils.getNetworkAddress())
    }

    override fun bindEvent() {
        btn_ok.setOnClickListener {
            SharedPreferencesUtils.setNetworkAddress(et_network.text.toString())
            showToast(getResourceString(R.string.setting_ok))
            finish()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_setting
    }

}