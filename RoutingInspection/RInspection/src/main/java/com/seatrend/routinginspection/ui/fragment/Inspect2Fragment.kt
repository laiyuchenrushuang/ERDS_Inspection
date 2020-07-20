package com.seatrend.routinginspection.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.seatrend.routinginspection.adapter.CheckDataPhotoAdapter
import com.seatrend.routinginspection.base.BaseFragment
import com.seatrend.routinginspection.entity.JudgeTaskEntity
import com.seatrend.routinginspection.entity.PhotoTypeEntity
import com.seatrend.routinginspection.utils.PermissionUtil
import kotlinx.android.synthetic.main.recyclerview.*
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import com.seatrend.xj.electricbicyclesalesystem.http.thread.ThreadPoolManager


/**
 * Created by ly on 2020/6/28 17:12
 */
class Inspect2Fragment : BaseFragment(), CheckDataPhotoAdapter.itemOnClickListener,
    CheckDataPhotoAdapter.itemDeleteClickListener {


    var allPhoto = ArrayList<PhotoTypeEntity>()
    private var photoPosition = 0
    private var imgFile: File? = null
    private val CAMERA_REQUEST_CODE = 20
    private var mCheckDataPhotoAdapter: CheckDataPhotoAdapter? = null

    override fun itemdelete(position: Int) {

        showLog(allPhoto[position].zplj)
        if (!TextUtils.isEmpty(allPhoto[position].zplj) || allPhoto[position].zplj != "") {
            val file = File(allPhoto[position].zplj)
            if (file.exists()) {
                file.delete()
            }
        }
        allPhoto.removeAt(position)
        mCheckDataPhotoAdapter!!.setPhotoType(allPhoto)
    }

    override fun itemOnClick(position: Int) {
        photoPosition = position
        if (allPhoto[position].zplj == ""|| allPhoto[position].zplj == null) {

            if (PermissionUtil.checkPermissions(activity, PermissionUtil.GetPermission {
                    getPicFromCamera()
                }, Manifest.permission.CAMERA)) {
            }

        }
    }

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(com.seatrend.routinginspection.R.layout.fragment_inspect2, container, false)
    }

    override fun initView() {
        //text data
        getDefaultPhoto()

        initRecycleview()
    }

    private fun initRecycleview() {
        m_recycler_view.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        mCheckDataPhotoAdapter = CheckDataPhotoAdapter(context)
        mCheckDataPhotoAdapter!!.setPhotoType(allPhoto)
        mCheckDataPhotoAdapter!!.setItemOnClick(this)
        mCheckDataPhotoAdapter!!.setItemdeleteClick(this)
        m_recycler_view.adapter = mCheckDataPhotoAdapter
    }

    private fun getDefaultPhoto() {
        val entity = PhotoTypeEntity()
        allPhoto.add(entity)
    }


    private fun getPicFromCamera() {
        //用于保存调用相机拍照后所生成的文件
        val tempFile = File(context!!.getExternalFilesDir(null).toString() + "/Inspection/CameraPhoto/")//

        showLog(context!!.getExternalFilesDir(null).toString() + "/Inspection/CameraPhoto/")
        val imageUri: Uri
        if (!tempFile.exists()) {
            tempFile.mkdirs()
        }
        imgFile = File(tempFile, System.currentTimeMillis().toString() + ".jpg")
        if (Build.VERSION.SDK_INT >= 24) {//判断版本
            imageUri = FileProvider.getUriForFile(
                context!!,
                getString(com.seatrend.routinginspection.R.string.authority),
                imgFile!!
            )
        } else {
            imageUri = Uri.fromFile(imgFile)
        }
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            synchronized(photoPosition) {
                ThreadPoolManager.instance.execute(Runnable {
                    Luban.with(context).load(imgFile)
                        .ignoreBy(100)
                        .filter { path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")) }
                        .setTargetDir(imgFile!!.parentFile.path)
                        .setCompressListener(object : OnCompressListener {
                            override fun onSuccess(file: File?) {
                                if (imgFile!!.exists()) {
                                    imgFile!!.delete()
                                }
                                imgFile = file
                                showLog("压缩成功 file = " + file!!.path)
                                allPhoto[photoPosition].zplj = imgFile!!.path

                                mCheckDataPhotoAdapter!!.setPhoto(photoPosition, imgFile!!.path)
                                allPhoto.add(PhotoTypeEntity())
                                mCheckDataPhotoAdapter!!.setPhotoType(allPhoto)
                                dismissLoadingDialog()
                            }

                            override fun onError(e: Throwable?) {
                                showLog("压缩失败")
                                dismissLoadingDialog()
                            }

                            override fun onStart() {
                                showLoadingDialog()
                            }

                        }).launch()

                })
            }

        }
    }

    fun getChangedZplist():ArrayList<PhotoTypeEntity>{
        return mCheckDataPhotoAdapter!!.getDataList()
    }

    override fun bindEvent() {
    }
}