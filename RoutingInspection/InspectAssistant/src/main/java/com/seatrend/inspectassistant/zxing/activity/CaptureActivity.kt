package com.seatrend.inspectassistant.zxing.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.Vibrator
import android.text.TextUtils
import android.view.SurfaceHolder
import android.view.SurfaceHolder.Callback
import android.view.SurfaceView
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity

import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.seatrend.http_sdk.HttpService
import com.seatrend.http_sdk.NormalView
import com.seatrend.http_sdk.base.ConmmonResponse
import com.seatrend.http_sdk.base.GsonUtils
import com.seatrend.http_sdk.inter.base.BaseService
import com.seatrend.inspectassistant.*
import com.seatrend.inspectassistant.entity.QCodeEntity
import com.seatrend.inspectassistant.zxing.camera.CameraManager
import com.seatrend.inspectassistant.zxing.decoding.CaptureActivityHandler
import com.seatrend.inspectassistant.zxing.decoding.InactivityTimer
import com.seatrend.inspectassistant.zxing.view.ViewfinderView
import kotlinx.android.synthetic.main.activity_scanner.*

import java.io.IOException
import java.util.Vector


/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
class CaptureActivity : AppCompatActivity(), Callback {


    private var handler: CaptureActivityHandler? = null
    var viewfinderView: ViewfinderView? = null
    private var back: ImageView? = null
    private var hasSurface: Boolean = false
    private var decodeFormats: Vector<BarcodeFormat>? = null
    private var characterSet: String? = null
    private var inactivityTimer: InactivityTimer? = null
    private var mediaPlayer: MediaPlayer? = null
    private var playBeep: Boolean = false
    private var vibrate: Boolean = false

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                View.VISIBLE -> {
                }
                View.INVISIBLE -> {
                }
            }
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private val beepListener = OnCompletionListener { mediaPlayer -> mediaPlayer.seekTo(0) }
    var surfaceHolder: SurfaceHolder? = null
    override fun onResume() {
        super.onResume()
        val surfaceView = findViewById<View>(R.id.scanner_view) as SurfaceView
        surfaceHolder = surfaceView.holder
        if (hasSurface) {
            initCamera(surfaceHolder)
        } else {
            surfaceHolder!!.addCallback(this)
            surfaceHolder!!.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
        }
        decodeFormats = null
        characterSet = null

        playBeep = true
        val audioService = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (audioService.ringerMode != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false
        }
        initBeepSound()
        vibrate = true
    }

    override fun onPause() {
        super.onPause()
        if (handler != null) {
            handler!!.quitSynchronously()
            handler = null
        }
        CameraManager.get().closeDriver()
    }

    override fun onDestroy() {
        inactivityTimer!!.shutdown()
        super.onDestroy()
    }

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    fun handleDecode(result: Result, barcode: Bitmap) {
        inactivityTimer!!.onActivity()
        playBeepSoundAndVibrate()
        val resultString = result.text
        val msg = Message()
        msg.what = View.VISIBLE
        mHandler.sendMessage(msg)

        //FIXME
        if (TextUtils.isEmpty(resultString)) {
            initCamera(surfaceHolder)
            return
        } else {
            getDataSync(resultString)
        }
    }

    private fun getDataSync(result: String) {

        try {
            LoadingDialog.getInstance().showLoadDialog(this)
            val resultStr = result
            val postStr = Base64Utils.base64Decode(resultStr, "utf-8")

            val entity = GsonUtils.gson(postStr, QCodeEntity::class.java)

            if ("1" != entity.zt) {  //只有1 才是可以预约
                val intent = Intent(this@CaptureActivity, OrderActivity::class.java)
                intent.putExtra("qcode_result", postStr)
                startActivity(intent)
            } else {
                HttpService.getInstance().postJson(
                    SharedPreferencesUtils.getNetworkAddress() + "/",
                    Constants.POST_QCODE,
                    postStr, BaseService::class.java,
                    false, object : NormalView {
                        override fun netWorkTaskSuccess(commonResponse: ConmmonResponse?) {
                            LoadingDialog.getInstance().dismissLoadDialog()

                            val intent = Intent(this@CaptureActivity, OrderActivity::class.java)
                            intent.putExtra("qcode_result", postStr)
                            startActivity(intent)
                        }

                        override fun netWorkTaskfailed(commonResponse: ConmmonResponse?) {
                            LoadingDialog.getInstance().dismissLoadDialog()
                            showToast(commonResponse!!.responseString)
                        }

                    })
            }
        } catch (e: Exception) {
            LoadingDialog.getInstance().dismissLoadDialog()
            val intent = Intent(this@CaptureActivity, OrderActivity::class.java)
            intent.putExtra("qcode_result", "")
            startActivity(intent)
        }

        finish()
    }

    private fun showToast(responseString: String?) {
        Toast.makeText(this, responseString, Toast.LENGTH_SHORT).show()
    }

    private fun initCamera(surfaceHolder: SurfaceHolder?) {
        try {
            CameraManager.get().openDriver(surfaceHolder)
        } catch (ioe: IOException) {
            return
        } catch (e: RuntimeException) {
            return
        }

//        if (handler == null) {
        handler = CaptureActivityHandler(
            this, decodeFormats,
            characterSet
        )
//        }
    }

    override fun surfaceChanged(
        holder: SurfaceHolder, format: Int, width: Int,
        height: Int
    ) {

    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (!hasSurface) {
            hasSurface = true
            initCamera(holder)
        }

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        hasSurface = false

    }

    fun getHandler(): Handler? {
        return handler
    }

    fun drawViewfinder() {
        viewfinderView!!.drawViewfinder()

    }

    private fun initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            volumeControlStream = AudioManager.STREAM_MUSIC
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer!!.setOnCompletionListener(beepListener)

            val file = resources.openRawResourceFd(
                R.raw.beep
            )
            try {
                mediaPlayer!!.setDataSource(
                    file.fileDescriptor,
                    file.startOffset, file.length
                )
                file.close()
                mediaPlayer!!.setVolume(BEEP_VOLUME, BEEP_VOLUME)
                mediaPlayer!!.prepare()
            } catch (e: IOException) {
                mediaPlayer = null
            }

        }
    }

    private fun playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer!!.start()
        }
        if (vibrate) {
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(VIBRATE_DURATION)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        initView()
    }

    fun initView() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //申请权限，CEMERA_OK是自定义的常量
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                1
            )
        }
        CameraManager.init(application)
        viewfinderView = findViewById<View>(R.id.viewfinder_content) as ViewfinderView

        hasSurface = false
        inactivityTimer = InactivityTimer(this)


        iv_back.setOnClickListener {
            finish()
        }
    }


    companion object {

        private val REQUEST_CODE_SCAN_GALLERY = 100
        private val BEEP_VOLUME = 0.10f

        private val VIBRATE_DURATION = 200L
    }
}