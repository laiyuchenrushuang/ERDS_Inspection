package com.tmri.size.app.re;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * Created by tmri on 2018/8/29.
 */

public class VeritityUtil {

    /**
     * 获取Imei号
     */
    public static String getIMEI(Context mContext) {
        String IMEI = "";
        if (Build.VERSION.SDK_INT >= 29){
            // android 10 获取不到 imei 用 设备 id 代替
            IMEI=Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else if (Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT <29 ) {
            int checkPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            } else {
                IMEI = ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            }
        } else {
            IMEI = ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        }
        return IMEI;
    }

    public static void setRSAcode(Context context, String rsacode){
        context.getApplicationContext().getSharedPreferences("reg_data",Context.MODE_PRIVATE)
                .edit().putString("rsa_code",rsacode).commit();
    }
    public static String getRSAcode(Context context){
        String b=context.getApplicationContext().getSharedPreferences("reg_data",Context.MODE_PRIVATE)
        .getString("rsa_code","");
        return b;
    }
    public static void setRequestCode(Context context,String requestCode){
        context.getApplicationContext().getSharedPreferences("reg_data",Context.MODE_PRIVATE)
                .edit().putString("re_code",requestCode).commit();
    }
    public static String getRequestCode(Context context){
        String s = context.getApplicationContext().getSharedPreferences("reg_data", Context.MODE_PRIVATE)
                .getString("re_code", "");
        return s;
    }
    public static void setAgreeCode(Context context,String agreeCode){
        context.getApplicationContext().getSharedPreferences("reg_data",Context.MODE_PRIVATE)
                .edit().putString("ag_code",agreeCode).commit();
    }
    public static String getAgreeCode(Context context){
        String s = context.getApplicationContext().getSharedPreferences("reg_data", Context.MODE_PRIVATE)
                .getString("ag_code", "");

        return s;
    }

    /**
     * 用字符串生成二维码
     *
     * @param str
     * @return
     * @throws WriterException
     */
    public static Bitmap createQRCode(String str) throws WriterException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 2);//设置白色边框的距离
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 640, 640, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }else {
                    pixels[y * width + x] = 0xffffffff;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
