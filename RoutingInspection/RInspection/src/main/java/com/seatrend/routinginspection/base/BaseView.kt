package com.seatrend.routinginspection.base

/**
 * Created by ly on 2020/4/8 10:13
 */
interface BaseView {
    fun showToast(msg: Any)
    fun showLog(msg: Any)
    fun showErrorDialog(msg: String)
}