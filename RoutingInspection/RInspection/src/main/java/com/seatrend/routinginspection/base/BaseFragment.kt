package com.seatrend.routinginspection.base

import android.app.Dialog
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView


import com.seatrend.routinginspection.utils.CarHphmUtils
import com.seatrend.routinginspection.utils.LoadingDialog
import android.graphics.Color
import android.text.Layout
import android.view.*
import androidx.core.content.ContextCompat
import com.seatrend.routinginspection.R


/**
 * Created by seatrend on 2018/10/8.
 */

abstract class BaseFragment : Fragment(), BaseView {

    private var llNoData: LinearLayout? = null
    private var tvNoDataMsg: TextView? = null
    private var rootView: View? = null

    //TextView 和 ScollView 冲突监听器
    val onTouchListener = View.OnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            //父节点不拦截子节点
            v.parent.requestDisallowInterceptTouchEvent(true)
        } else if (event.action == MotionEvent.ACTION_MOVE) {
            //父节点不拦截子节点
            v.parent.requestDisallowInterceptTouchEvent(true)
        } else if (event.action == MotionEvent.ACTION_UP) {
            //父节点拦截子节点
            v.parent.requestDisallowInterceptTouchEvent(false)
        }
        false
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (null != rootView) {
            val parent: ViewGroup? = container
            if (null != parent) {
                parent.removeView(parent)
            }
        } else {
            rootView = getLayoutView(inflater, container)
        }

        llNoData = rootView!!.findViewById(com.seatrend.routinginspection.R.id.ll_no_data)
        tvNoDataMsg = rootView!!.findViewById(com.seatrend.routinginspection.R.id.tv_msg)
        return rootView
    }

    //输入进行过滤 只能输入汉字，字母，英文

    val inputFilter = object : InputFilter {

//        var pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_]")
//        override fun filter(charSequence: CharSequence, i: Int, i1: Int, spanned: Spanned, i2: Int, i3: Int): CharSequence? {
//            val matcher = pattern.matcher(charSequence)
//            if (!matcher.find()) {
//                return null
//            } else {
//                showToast("只能输入汉字,英文，数字")
//                return ""
//            }
//        }

        //保留“-“ 方便门牌输入
        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
            for (i in start until end) {
                if (!Character.isLetterOrDigit(source[i])
                        && Character.toString(source[i]) != "_"
                        && Character.toString(source[i]) != "-") {
                    return ""
                }
            }
            return null
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        bindEvent()
    }

    abstract fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View
    abstract fun initView()
    abstract fun bindEvent()

    override fun showToast(msg: Any) {
        Toast.makeText(context, msg.toString(), Toast.LENGTH_SHORT).show()

    }

    //显示不完全的Textview 处理
    fun setLongTextview(text: TextView?) {
        text!!.movementMethod = ScrollingMovementMethod.getInstance()
        text.setOnTouchListener(onTouchListener)
    }

    //输入表情屏蔽
    fun setEditNoEmoj(vararg editList: EditText) {
        for (view in editList) {
            view.filters = arrayOf(inputFilter)
        }
    }

    //切换大写
    fun setEditUppercase(vararg editList: EditText) {
        for (view in editList) {
            view.transformationMethod = CarHphmUtils.TransInformation()
        }
    }

    override fun showErrorDialog(msg: String) {
        try {
            val mDialog = Dialog(activity!!)
            mDialog.setContentView(com.seatrend.routinginspection.R.layout.dialog_error)
            mDialog.setCanceledOnTouchOutside(true)
            val tvMsg = mDialog.findViewById<TextView>(com.seatrend.routinginspection.R.id.tv_msg)
            val btnOk = mDialog.findViewById<Button>(com.seatrend.routinginspection.R.id.btn_ok)
            tvMsg.text = msg
            btnOk.setOnClickListener { mDialog.dismiss() }
            mDialog.show()
        } catch (e: Exception) {

        }

    }

    fun showNoDataView(msg: String) {
        llNoData!!.visibility = View.VISIBLE
        tvNoDataMsg!!.text = msg
    }

    fun hideNoDataView() {
        llNoData!!.visibility = View.GONE

    }

    override fun showLog(s: Any) {
        Log.d("lylog", s.toString())
    }

    fun showLoadingDialog() {
        showLog("show")
        LoadingDialog.getInstance().showLoadDialog(context)
    }

    fun dismissLoadingDialog() {
        showLog("dismiss")
        LoadingDialog.getInstance().dismissLoadDialog()
    }

    /**设置SearchView下划线透明 */
     fun setUnderLinetransparent(searchView: SearchView) {
        try {
            val argClass = searchView.javaClass
            // mSearchPlate是SearchView父布局的名字
            val ownField = argClass.getDeclaredField("mSearchPlate")
            ownField.setAccessible(true)
            val mView = ownField.get(searchView) as View
            mView.setBackgroundColor(Color.TRANSPARENT)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }
    /**设置SearchView下划线透明 */
     fun setSearchviewTextsize(searchView: SearchView, fl: Float) {
        try {
            val argClass = searchView.javaClass
            // mSearchPlate是SearchView父布局的名字
            val ownField = argClass.getDeclaredField("mSearchSrcTextView")
            ownField.setAccessible(true)
            val mView = ownField.get(searchView) as TextView
//            mView.textSize = fl
//            mView.gravity = Gravity.FILL_VERTICAL
//            val lp = mView.layoutParams
//            lp.height = ViewGroup.LayoutParams.MATCH_PARENT
//            mView.layoutParams = lp
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }
}
