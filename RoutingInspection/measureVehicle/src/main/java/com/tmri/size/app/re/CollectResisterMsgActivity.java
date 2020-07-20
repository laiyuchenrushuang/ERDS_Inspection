package com.tmri.size.app.re;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.tmri.size.app.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollectResisterMsgActivity extends Activity {

    private EditText edCompanyName,edName,edPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_resister_msg);
        initView();
    }

    private void initView() {
        edCompanyName=findViewById(R.id.et_company_name);
        edName=findViewById(R.id.et_name);
        edPhone=findViewById(R.id.rt_phone);

        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();

            }
        });
    }

    private void  next(){
        if (!TextUtils.isEmpty(edPhone.getText().toString())){
            if (!isPhone(edPhone.getText().toString())){
                Toast.makeText(this,"请输入正确的电话号码",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Intent intent=new Intent(CollectResisterMsgActivity.this,RegisterActivity.class);
        intent.putExtra("company",edCompanyName.getText().toString());
        intent.putExtra("name",edName.getText().toString());
        intent.putExtra("phone",edPhone.getText().toString());
        startActivityForResult(intent,100);
    }

    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && resultCode==RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            setResult(RESULT_OK);
        }
        return super.onKeyDown(keyCode, event);
    }
}
