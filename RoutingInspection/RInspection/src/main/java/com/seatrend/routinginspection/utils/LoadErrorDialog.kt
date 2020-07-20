package com.seatrend.routinginspection.utils

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.seatrend.routinginspection.MyApplication
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.entity.MessageEntity

/**
 * Created by ly on 2020/7/9 17:28
 */
class LoadErrorDialog {


    private var mDialog: Dialog? = null

    companion object {
        var mLoadingDialog: LoadErrorDialog? = null

        private var context: Context? = null
        fun getInstance(ctx: Context): LoadErrorDialog {
            this.context = ctx
            if (mLoadingDialog == null) {
                mLoadingDialog = LoadErrorDialog()
            }
            return mLoadingDialog!!
        }
    }


    fun showErrorDialog(msg: String) {

        val tipsMsg = try {
            GsonUtils.gson(msg, MessageEntity::class.java).message
        } catch (e: java.lang.Exception) {
            msg
        }

        try {
            mDialog = Dialog(context!!)
            mDialog!!.setContentView(R.layout.dialog_error)
            mDialog!!.setCanceledOnTouchOutside(true)
            val tvMsg = mDialog!!.findViewById<TextView>(R.id.tv_msg)
            val btnOk = mDialog!!.findViewById<Button>(R.id.btn_ok)
            tvMsg.text = tipsMsg
            btnOk.setOnClickListener { mDialog!!.dismiss() }
            mDialog!!.show()
        } catch (e: Exception) {
            Toast.makeText(context, "showErrorDialog has Exception", Toast.LENGTH_SHORT)
                .show()
        }

    }

    fun dismissLoadDialog() {
        if (mDialog != null) {
            mDialog!!.dismiss()
        }
    }


}
