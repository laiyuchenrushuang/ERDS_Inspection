package com.seatrend.routinginspection.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.text.InputFilter
import android.text.Spanned
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.entity.MessageEntity
import com.seatrend.routinginspection.utils.*
import java.util.ArrayList
import java.util.regex.Pattern

/**
 * Created by ly on 2020/4/8 9:44
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {

    var ivBack: ImageView? = null
    var ivRight: ImageView? = null
    var tvPageTitle: TextView? = null
    var tvRight: TextView? = null
    var rlParent: RelativeLayout? = null
    private var noDataView: View? = null

    private var mActivityJumpTag: String? = null       //activity跳转tag
    private var mClickTime: Long? = 0L  //事件间隔time
    private var LIMIT_CLICK_TIME: Long = 1000 //限制跳转界面的时间

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
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            for (i in start until end) {
                if (!Character.isLetterOrDigit(source[i])
                    && Character.toString(source[i]) != "_"
                    && Character.toString(source[i]) != "-"
                ) {
                    return ""
                }
            }
            return null
        }
    }

    //表情过滤
    val inputEmojiFilter = object : InputFilter {
        val emoji = Pattern.compile(
            "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
            Pattern.UNICODE_CASE or Pattern.CASE_INSENSITIVE
        )

        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            var emojiMatcher = emoji.matcher(source)
            if (emojiMatcher.find()) {
                return ""
            }
            return null
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//竖屏
        initCommonTitle()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor(ContextCompat.getColor(this, R.color.theme_color))
        }
        initView()
        bindEvent()
    }

    //request
    fun appPermissionReq() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            appRequestPermissions()
        }
    }

    //权限申请
    @RequiresApi(Build.VERSION_CODES.M)
    protected fun appRequestPermissions() {

        val permission = ArrayList<String>()
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (checkSelfPermission(Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.CAMERA)
        }

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) !== PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.READ_PHONE_STATE)
        }
        if (checkSelfPermission(Manifest.permission.NFC) !== PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.NFC)
        }
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) !== PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.RECORD_AUDIO)
        }

        if (permission.size > 0) {
            ActivityCompat.requestPermissions(this@BaseActivity, permission.toTypedArray(), 1)
        }
    }

    //标题格式  <      XXX     XX(image)
    @SuppressLint("WrongViewCast")
    fun initCommonTitle() {
        ivBack = findViewById(R.id.iv_back)
        ivRight = findViewById(R.id.iv_right)
        tvPageTitle = findViewById(R.id.tv_titile)
        tvRight = findViewById(R.id.tv_right)
        rlParent = findViewById(R.id.rl_parent)

        if (ivBack != null) {
            ivBack!!.setOnClickListener { finish() }
        }
    }

    //设置标题内容
    fun setPageTitle(pageTitle: String) {
        if (tvPageTitle != null) {
            tvPageTitle!!.text = pageTitle
        }
    }

    //展示没数据的界面
    fun showNoDataView(msg: String) {
        noDataView = LayoutInflater.from(this).inflate(R.layout.common_no_data, null)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val tvMsg = noDataView!!.findViewById<TextView>(R.id.tv_msg)
        tvMsg.text = msg
        addContentView(noDataView, params)
    }


    //设置浸透title
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected fun setStatusBarColor(statusColor: Int) {
        val window = window
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        //设置状态栏颜色
        window.statusBarColor = statusColor
        //设置系统状态栏处于可见状态
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        //让view不根据系统窗口来调整自己的布局
        val mContentView = window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
        val mChildView = mContentView.getChildAt(0)
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false)
            ViewCompat.requestApplyInsets(mChildView)
        }
    }

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

    //显示不完全的Textview 处理
    @SuppressLint("ClickableViewAccessibility")
    fun setScollTextView(vararg viewList: TextView) {
        for (view in viewList) {
            view.movementMethod = ScrollingMovementMethod.getInstance()
            val para = view.layoutParams
//            view.maxWidth = DP2PX.dip2px(this,180f)   //最大宽度
//            view.setPadding(0,0,DP2PX.dip2px(this,5f),0)  //padding end 5dp
            view.setOnTouchListener(onTouchListener)
        }
    }

    //显示不完全的Textview 处理(在右端的文字)
    @SuppressLint("ClickableViewAccessibility")
    fun setScollTextView(vararg viewList: TextView, maxWith: Int, paddingEndDis: Int) {
        for (view in viewList) {
            view.movementMethod = ScrollingMovementMethod.getInstance()
            val para = view.layoutParams
            view.maxWidth = DP2PX.dip2px(this, maxWith.toFloat())   //最大宽度
            view.setPadding(0, 0, DP2PX.dip2px(this, paddingEndDis.toFloat()), 0)  //padding end 5dp
            view.setOnTouchListener(onTouchListener)
        }
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

    //设置checkbox 默认值
    fun setCheckBoxDefault(vararg viewList: CheckBox, default: Boolean) {
        for (view in viewList) {
            view.isChecked = default
        }
    }

    @SuppressLint("RestrictedApi")
    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        if (checkDoubleClick(intent!!)) {
            super.startActivityForResult(intent, requestCode, options)
        }
    }

    /**
     * 检查是否重复跳转，不需要则重写方法并返回true(这样的好处是界面事件的唯一性，如果同一界面是卡时间，如果不同界面时间没限制)
     *
     * 这是一个解决方案，方案弊端如果是app只有一个activity,fragment切换不适用，方案采用第二个，查验终端的方案
     *
     * 最终两者结合
     */
    private fun checkDoubleClick(intent: Intent): Boolean {

        // 默认检查通过
        var result = true
        // 标记对象
        val tag: String?
        if (intent.component != null) { // 显式跳转
            tag = intent.component!!.className
        } else if (intent.action != null) { // 隐式跳转
            tag = intent.action
        } else {
            return true
        }

        //间隔时间太短 不能跳转  返回false
        if (mActivityJumpTag == tag && LIMIT_CLICK_TIME >= SystemClock.uptimeMillis() - mClickTime!!) {
            return false
        }

        // 间隔时间不短，但是栈里面已经start界面，属于repeat活动界面，返回false[适用于Activity 活动，但是不适用隐式跳转] ---- 堆栈存储的是Activity
//        if(intent.component != null && AppManager.getInstance().repeatActivity(intent.component.className)){
//            return false
//        }

        // 记录启动标记和时间
        mActivityJumpTag = tag
        mClickTime = SystemClock.uptimeMillis()
        return result
    }

    //隐藏虚拟按键，并且全屏
    protected fun hideBottomUIMenu() {
        if (Build.VERSION.SDK_INT in 12..18) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            val decorView = window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }
    }

    override fun showToast(msg: Any) {
        Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun showLog(msg: Any) {
        Log.d("[lylog]", "----- " + msg)
    }

    fun getTextColor(colorId: Int): Int {
        return ContextCompat.getColor(this, colorId)
    }

    fun showLoadingDialog() {
        LoadingDialog.getInstance().showLoadDialog(this)
    }

    fun dismissLoadingDialog() {
        LoadingDialog.getInstance().dismissLoadDialog()
    }


    fun <T : Any?> startActivityToTop(classT: Class<T>) {
        val intent = Intent(this, classT)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        showToast("提交完成")
        startActivity(intent)
    }

    override fun showErrorDialog(msg: String) {
        LoadErrorDialog.getInstance(this).showErrorDialog(msg)
//        val tipsMsg = try {
//            GsonUtils.gson(msg, MessageEntity::class.java).message
//        } catch (e: java.lang.Exception) {
//            msg
//        }
//
//        try {
//            val mDialog = Dialog(this)
//            mDialog.setContentView(R.layout.dialog_error)
//            mDialog.setCanceledOnTouchOutside(true)
//            val tvMsg = mDialog.findViewById<TextView>(R.id.tv_msg)
//            val btnOk = mDialog.findViewById<Button>(R.id.btn_ok)
//            tvMsg.text = tipsMsg
//            btnOk.setOnClickListener { mDialog.dismiss() }
//            mDialog.show()
//        } catch (e: Exception) {
//            showToast("showErrorDialog has Exception")
//        }

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
            mView.textSize = fl
//            val lp = mView.layoutParams
//            lp.height = ViewGroup.LayoutParams.MATCH_PARENT
//            mView.layoutParams = lp
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

    abstract fun initView()
    abstract fun bindEvent()
    abstract fun getLayout(): Int
}