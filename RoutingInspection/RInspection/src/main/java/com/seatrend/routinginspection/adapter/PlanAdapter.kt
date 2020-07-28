package  com.seatrend.routinginspection.adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.seatrend.http_sdk.NormalView
import com.seatrend.http_sdk.base.ConmmonResponse
import com.seatrend.http_sdk.inter.base.BaseService
import com.seatrend.routinginspection.R
import com.seatrend.routinginspection.base.Constants
import com.seatrend.routinginspection.db.DbUtils
import com.seatrend.routinginspection.db.table.JudgeTable
import com.seatrend.routinginspection.db.table.PlanTable
import com.seatrend.routinginspection.entity.JudgeTaskEntity
import com.seatrend.routinginspection.entity.PLANEntity
import com.seatrend.routinginspection.http.HttpRequest
import com.seatrend.routinginspection.model.LoginModel
import com.seatrend.routinginspection.ui.InspectActivity
import com.seatrend.routinginspection.ui.no_network.N_InspectActivity
import com.seatrend.routinginspection.utils.*

/**
 * Created by ly on 2020/6/28 13:54
 */
class PlanAdapter(var mContext: Context) : RecyclerView.Adapter<PlanAdapter.MyViewHolder>() {

    private var mData = ArrayList<PLANEntity.DataBean.TASK>()
    private var flag = 0x99  // NO_NETWORK 代表无网络环境
    private var addflag = false  // false  不是添加离线计划模式

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(mContext).inflate(R.layout.plan_item_view, parent, false)
        return MyViewHolder(view)

    }

    fun setData(data: ArrayList<PLANEntity.DataBean.TASK>) {
        this.mData = data
        notifyDataSetChanged()
    }

    fun clearData() {
        this.mData.clear()
        notifyDataSetChanged()
    }

    //无网络和有网络
    fun setAvFlag(flag: Int) {
        this.flag = flag
    }

    //离线计划模式
    fun setAddModel(flag: Boolean) {
        this.addflag = flag
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.initView(mData[position])
    }


    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        private var sName: TextView? = null
        private var sState: TextView? = null
        private var tv_station_time: TextView? = null
        private var ll_item: LinearLayout? = null
        private var cb_add: CheckBox? = null
        private var iv_to: ImageView? = null

        fun initView(station: PLANEntity.DataBean.TASK) {
            sName = itemView.findViewById(R.id.tv_station_name)
            iv_to = itemView.findViewById(R.id.iv_to)
            sState = itemView.findViewById(R.id.tv_state)
            tv_station_time = itemView.findViewById(R.id.tv_station_time)
            ll_item = itemView.findViewById(R.id.ll_item)
            cb_add = itemView.findViewById(R.id.cb_add)

            //设置数据
            sName!!.text = station.bmmc
            sState!!.text = JHStateUtils.getJhState(station.jhzt)
            if ("2" == station.jhzt) {  //已经完成了的
                sState!!.setTextColor(ContextCompat.getColor(mContext, R.color.green))
            } else {
                sState!!.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            }
            tv_station_time!!.text = StringUtils.longToStringDataNoHour(station.xzrq)
//            tv_station_time!!.text = StringUtils.longToStringData(station.xzrq)

            //离线计划添加
            if (addflag) {
                cb_add!!.visibility = View.VISIBLE

                cb_add!!.isActivated = false
                if (DbUtils.getInstance(mContext).searchPlanByLshHas(mData[adapterPosition].jhbh)) {
                    cb_add!!.text = mContext.resources.getString(R.string.add_done)
                    cb_add!!.background = ContextCompat.getDrawable(mContext, R.drawable.gray)
                    cb_add!!.isChecked = true
                    cb_add!!.isActivated = true
                    cb_add!!.isEnabled =
                        !DbUtils.getInstance(mContext).searchPlanByLshHadDone(mData[adapterPosition].jhbh)  //本地执行完成了的就不能再删除了 必须上传
//
                } else {
                    cb_add!!.text = mContext.resources.getString(R.string.add)
                    cb_add!!.background = ContextCompat.getDrawable(mContext, R.drawable.themecolor)
                    cb_add!!.isChecked = false
                    cb_add!!.isActivated = true
//                    cb_add!!.isEnabled = true
                }
                iv_to!!.visibility = View.GONE
                sState!!.visibility = View.GONE

            } else {
                cb_add!!.visibility = View.GONE
                iv_to!!.visibility = View.VISIBLE
                sState!!.visibility = View.VISIBLE
            }
            bindEvent()
        }

        private fun bindEvent() {
            ll_item!!.setOnClickListener {

                if (!addflag) {  // 不是离线计划才能点
                    if (Constants.NETWORK.NO_NETWORK.ordinal == flag) {
                        val intent = Intent(mContext, N_InspectActivity::class.java)
                        intent.putExtra(Constants.JHBH, mData[adapterPosition].jhbh)
                        intent.putExtra(Constants.JHZT, mData[adapterPosition].jhzt)
                        intent.putExtra(Constants.GLBM, mData[adapterPosition].glbm)
                        mContext.startActivity(intent)
                    } else {
                        val intent = Intent(mContext, InspectActivity::class.java)
                        intent.putExtra(Constants.JHBH, mData[adapterPosition].jhbh)
                        intent.putExtra(Constants.GLBM, mData[adapterPosition].glbm)
                        mContext.startActivity(intent)
                    }
                } else {

                }
            }

            cb_add!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if(cb_add!!.isActivated){
                    if (isChecked) {
                        cb_add!!.text = mContext.resources.getString(R.string.add_done)
                        cb_add!!.background = ContextCompat.getDrawable(mContext, R.drawable.gray)

                        if(!cb_add!!.isEnabled){
                            ToastUtil.show(mContext,mContext.resources.getString(R.string.cancel_faield))
                        }

                        LoadingDialog.getInstance().showTipDialog(
                            mContext, mContext.resources.getString(R.string.add_tip),
                            object : LoadingDialog.OnClickListener {
                                override fun btnOkClick() {
                                    val entity = mData[adapterPosition]
                                    val jhbh = entity.jhbh
                                    val glbm = entity.glbm
                                    if (DbUtils.getInstance(mContext).searchPlanByLshIsDone(jhbh)) {  //离线任务里面已经有这条数据 并且完成了的，不用再次加载
                                        ToastUtil.show(mContext, mContext.resources.getString(R.string.add_tip_1))
                                    } else {
                                        requestTask(entity)
                                    }
                                }

                                override fun btnCancelClick() {
                                    cb_add!!.text = mContext.resources.getString(R.string.add)
                                    cb_add!!.background = ContextCompat.getDrawable(mContext, R.drawable.themecolor)
                                    cb_add!!.isChecked = false
                                }

                            })
//                    cb_add!!.isEnabled = false  //是否不能点击

                    } else {
                        cb_add!!.text = mContext.resources.getString(R.string.add)
                        cb_add!!.background = ContextCompat.getDrawable(mContext, R.drawable.themecolor)

                        DbUtils.getInstance(mContext)
                            .deleteDbPlanJudge(mData[adapterPosition].jhbh) //取消添加 需要删除数据库（plan  judge）
                        ToastUtil.show(mContext, mContext.resources.getString(R.string.cancel_success))
                    }
                }

            }
        }

        private fun requestTask(task: PLANEntity.DataBean.TASK) {

            val map = HashMap<String, String>()
            map[Constants.JHBH] = task.jhbh
            map[Constants.GLBM] = task.glbm
            LoadingDialog.getInstance().showLoadDialog(mContext)
            HttpRequest.geT(Constants.URL.GET_TASK_LIST, map, BaseService::class.java, true, object : NormalView {
                override fun netWorkTaskSuccess(commonResponse: ConmmonResponse?) {
                    LoadingDialog.getInstance().dismissLoadDialog()

                    try {
                        val enity = GsonUtils.gson(commonResponse!!.responseString, JudgeTaskEntity::class.java)

                        if (enity != null && enity.data != null && enity.data.size > 0) {

                            val userBean = LoginModel.getLogin()
                            val planTable = PlanTable()
                            planTable.lsh = task.jhbh
                            planTable.userdh = userBean.applicationUser.sysUser.yhdh  //用户代号
                            planTable.xzrq = task.xzrq
                            planTable.xzlx = task.xzlx
                            planTable.bmmc = task.bmmc
                            planTable.glbm = task.glbm
                            planTable.roW_ID = task.roW_ID
                            planTable.jhzt = task.jhzt

                            DbUtils.getInstance(mContext).insert(planTable)  //插入计划列表


                            for (db in enity.data) {

                                val judgeTable = JudgeTable()
                                judgeTable.rwzxjg = db.rwzxjg
                                judgeTable.lsh = db.jhbh
                                judgeTable.rwmc = db.rwmc
                                judgeTable.rwbh = db.rwbh
                                judgeTable.rwcjr = db.rwcjr
                                judgeTable.glbm = db.glbm
                                judgeTable.rwcjsj = db.rwcjsj
                                judgeTable.rwms = db.rwms
                                judgeTable.rwxgsj = db.rwxgsj
                                judgeTable.rwzt = db.rwzt
                                judgeTable.rwzxr = userBean.applicationUser.sysUser.xm  //用户名字

                                DbUtils.getInstance(mContext).insert(judgeTable)  //插入任务列表
                            }
                            ToastUtil.show(mContext, mContext.resources.getString(R.string.add_success))


                        }
                    } catch (e: Exception) {
                        Toast.makeText(mContext, e.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun netWorkTaskfailed(commonResponse: ConmmonResponse?) {
                    LoadingDialog.getInstance().dismissLoadDialog()
                    Toast.makeText(mContext, commonResponse!!.responseString, Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}