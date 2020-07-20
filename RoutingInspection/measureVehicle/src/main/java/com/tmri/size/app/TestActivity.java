package com.tmri.size.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends Activity {
    private EditText lsh, cph, cph1;
    private Switch isTwoCar;
    private int requstCode_measure = 111;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_test);
        initView();
    }

    private void initView() {
        lsh = (EditText) findViewById(R.id.lsh);
        cph = (EditText) findViewById(R.id.lsh);
        cph1 = (EditText) findViewById(R.id.cph1);
        isTwoCar = (Switch) findViewById(R.id.isTwoCar);
        result = (TextView) findViewById(R.id.result);
    }

    public void btnGo(View view) {
        if (checkData()) {
            Intent intent = new Intent(this, RequestAction.class);
            intent.putExtra("cylsh", lsh.getText().toString().trim());
            intent.putExtra("clsbdh", "333");
            intent.putExtra("cph", cph.getText().toString().trim());
            if (isTwoCar.isChecked()) {
                intent.putExtra("hpzl", "15");
                intent.putExtra("cllx", "B18");
            } else {
                intent.putExtra("hpzl", "01");
                intent.putExtra("cllx", "H11");
            }
            intent.putExtra("jylb", "00");
            intent.putExtra("isTwoCar", isTwoCar.isChecked());
            if (isTwoCar.isChecked()) {
                intent.putExtra("cylsh_1", lsh.getText().toString().trim());
                intent.putExtra("clsbdh_1", "3334");
                intent.putExtra("cph_1", cph1.getText().toString().trim());
                intent.putExtra("hpzl_1", "01");
                intent.putExtra("cllx_1", "H11");
                intent.putExtra("jylb_1", "00");
            }
            startActivityForResult(intent, requstCode_measure);
        }
    }

    private boolean checkData() {
        if (TextUtils.isEmpty(lsh.getText().toString().trim())) {
            Toast.makeText(this, "流水号不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(cph.getText().toString().trim())) {
            Toast.makeText(this, "车牌号不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (isTwoCar.isChecked() && TextUtils.isEmpty(cph1.getText().toString().trim())) {
            Toast.makeText(this, "挂车牌号不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == requstCode_measure) {
                result.setText("");
                result.append(data.getStringExtra("cylsh") + ",");
                result.append(data.getStringExtra("clsbdh") + ",");
                result.append(data.getStringExtra("bh") + ",");
                result.append(data.getStringExtra("cwkc") + ",");
                result.append(data.getStringExtra("cwkk") + ",");
                result.append(data.getStringExtra("cwkg") + ",");
                result.append(data.getStringExtra("zj") + ",");
                boolean has2Car = data.getBooleanExtra("isTwoCar", false);
                result.append(has2Car ? "带挂车" : "不带挂车" + ",");
                if (has2Car) {
                    result.append(data.getStringExtra("cylsh_1") + ",");
                    result.append(data.getStringExtra("clsbdh_1") + ",");
                    result.append(data.getStringExtra("bh_1") + ",");
                    result.append(data.getStringExtra("cwkc_1") + ",");
                    result.append(data.getStringExtra("cwkk_1") + ",");
                    result.append(data.getStringExtra("cwkg_1") + ",");
                    result.append(data.getStringExtra("zj_1") + ",");
                }
                result.append(data.getStringExtra("image0") + ",");
                result.append(data.getStringExtra("image1") + ",");
                result.append(data.getStringExtra("image2") + ",");
                result.append(data.getStringExtra("image3"));
            }
        }

    }
}
