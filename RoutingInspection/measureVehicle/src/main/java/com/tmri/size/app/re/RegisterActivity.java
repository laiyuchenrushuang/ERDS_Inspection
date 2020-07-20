package com.tmri.size.app.re;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tmri.size.app.R;


import org.json.JSONObject;

import java.net.URLEncoder;

public class RegisterActivity extends Activity implements View.OnClickListener{

    private ImageView ivQRcode;
    private TextView tvTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        initView();
    }

    private void initView() {
        ivQRcode=findViewById(R.id.iv_qrcode);
        tvTips=findViewById(R.id.tv_tips);
        findViewById(R.id.btn_scan).setOnClickListener(this);
        findViewById(R.id.btn_refresh).setOnClickListener(this);
        setRequestQrCode();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_refresh:
                setRequestQrCode();


                break;
            case R.id.btn_scan:

                btnScaOnClick();
                break;
        }

    }
    public void btnScaOnClick() {
        new IntentIntegrator(this)
                .setOrientationLocked(false)
                .setCaptureActivity(ScanQrAcrivityActivity.class)
                .initiateScan(); // 初始化扫描
    }
    private void setRequestQrCode(){
       String imei = VeritityUtil.getIMEI(this);

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("package","外廓尺寸测量插件");
            jsonObject.put("imei",imei);
            jsonObject.put("time",System.currentTimeMillis()+"");
            jsonObject.put("company",getIntent().getStringExtra("company")+"");
            jsonObject.put("name",getIntent().getStringExtra("name")+"");
            jsonObject.put("phone",getIntent().getStringExtra("phone")+"");
            String encode = URLEncoder.encode(jsonObject.toString(), "utf-8");
            Bitmap bitmap = VeritityUtil.createQRCode(encode);
            ivQRcode.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(imei)){
            tvTips.setText("警告，未获取到设备的IMEI号，为了保证能够顺利授权，请同意相关权限，请刷新重试");
            tvTips.setTextColor(getResources().getColor(R.color.red));
        }else {
            tvTips.setText("授权二维码正常");
            tvTips.setTextColor(getResources().getColor(R.color.blue));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                Toast.makeText(this,"内容为空",Toast.LENGTH_LONG).show();
            } else {
               // Toast.makeText(this,"扫描成功",Toast.LENGTH_LONG).show();
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                analysisData(ScanResult);
            }
        }
    }

    private void analysisData(String RSAcode){
        String decrypt = RSAUtils.decrypt(RSAcode);
        Log.i("analysisData"," --  "+decrypt);
        if (VeritityUtil.getIMEI(this).equals(decrypt)){
            VeritityUtil.setRSAcode(this,RSAcode);
            setResult(RESULT_OK);
            finish();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i].equals(Manifest.permission.READ_PHONE_STATE) && grantResults[i]==0){
                setRequestQrCode();
                break;
            }
        }
    }
}
