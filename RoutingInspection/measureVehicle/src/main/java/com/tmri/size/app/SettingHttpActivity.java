package com.tmri.size.app;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingHttpActivity extends BaseActivity {

    private EditText mEditText;

    private Button btnBack,btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_http);
        initView();
    }

    private void initView() {
        mEditText=findViewById(R.id.net_et);
        btnBack=findViewById(R.id.btn_back);
        btnSave=findViewById(R.id.btn_save);


        mEditText.setText(SharedPreferencesUtils.getNetWorkAddress());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = mEditText.getText().toString();
                if (TextUtils.isEmpty(s)){
                    Toast.makeText(SettingHttpActivity.this,"请输入服务器地址",Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferencesUtils.setNetWorkAddress(s);
                Toast.makeText(SettingHttpActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                finish();

            }
        });


        mEditText.setSelection(mEditText.getText().length());


    }

    @Override
    public void onChooseImage(String path) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
