package com.tmri.size.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hc.library.bluesocket.BlueSocketBaseThread;
import com.hc.library.bluesocket.BluetoothSppHelper;
import com.hc.library.bluesocket.message.IMessage;
import com.hc.library.bluesocket.message.ImageMessage;
import com.hc.library.bluesocket.message.StringMessage;
import com.hc.library.bluesocket.utils.APPUtil;
import com.hc.library.bluesocket.utils.TimeUtils;
import com.hc.library.bluesocket.utils.VehCrPDAMeasureUtils;
import com.hc.library.bluesocket.utils.W;
import com.tmri.size.app.entity.BaseEntity;
import com.tmri.size.app.entity.VehicleEntity;
import com.tmri.size.app.http.HttpService;
import com.tmri.size.app.re.CollectResisterMsgActivity;
import com.tmri.size.app.re.RSAUtils;
import com.tmri.size.app.re.VeritityUtil;
import com.tmri.size.app.utils.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Jack.Yan on 2017/10/31.
 */

public class RequestAction extends BaseActivity implements BluetoothSppHelper.BlueSocketListener {

    private String cylsh, clsbdh, cph, hpzl, cllx, jylb;
    private String cylsh_1, clsbdh_1, cph_1, hpzl_1, cllx_1, jylb_1;
    private boolean isTwoCar = false;//是否带挂车
    private int cyzzl;

    private static TextView measure_tv_name;
    private TextView measure_tv_status;
    private TextView measure_tv_type, measure_tv_carnum, measure_tv_length, measure_tv_width, measure_tv_height, measure_tv_space,tv_zbzl;
    private TextView measure_tv_type1, measure_tv_carnum1, measure_tv_length1, measure_tv_width1, measure_tv_height1,tv_version;
    private ImageView measure_image1,measure_image2,measure_image3,measure_image4;
    private LinearLayout measure_ll_two;
    private ListView measure_lv_list;
    private BluetoothListAdapter adapter;
    private static Button itemButton;
    private static Button measure_btn_cancel, measure_btn_ok;
    private static Button measure_btn_start, measure_btn_stop;
    private CheckBox cbZbzl,cbLbgd,cbClzj,cbWkce;
    private String zbzl="0";

    private static BluetoothSppHelper mHelper;
    private static BluetoothAdapter bluetoothAdapter;

    private List<BluetoothDevice> list = new ArrayList<>();
    private List<String> imagePathList = new ArrayList<>();
    private String[] dataArr = null;
    String testStr;
    private int REGISTER_CODE=13;

    private RadioButton rbBlueTooth,rbTcp;
    private RadioGroup rbConnectType;
    private Button btnTcpConnect,btnGetVeh,btnSaveVeh;
    private String ywlx;
    private String ycwkc;
    private String ycwkk;
    private String ycwkg;

    private TextView tvTcpIp,tvTcpStatus,tvSocketTips,tvResultTips;
    private LinearLayout tcpLl,bluetoothLl;


    private TextView tvLbgd;
    private String sfwk="1";
    private String sfzj="0";
    private String sflbgd="0";
    private String ywlxs;

    private static final int GET_VHE_CODE=2020;
    private static final int SAVE_VHE_CODE=2022;
    private Gson gson=new Gson();
    private VehicleEntity vehicleEntity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_measure);
            initIntentData();
            initView();
            initViewData();
            bindEvent();

        testStr=loadFromSDFile("size.txt");
        if (testStr!=null){
            measure_btn_ok.setEnabled(true);
           // downloadImagePath= Environment.getExternalStorageDirectory().getAbsolutePath()+testStr;
        }
        if(!"HC".equals(AppInfo.getSystemProperty())){

            checkRegister();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AppRequestPermissions();
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void AppRequestPermissions() {

        final List<String> permission = new ArrayList<>();
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        /*if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.CAMERA);
        }

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.READ_PHONE_STATE);
        }*/
        if (permission.size() > 0) {
            ActivityCompat.requestPermissions(this, permission.toArray(new String[permission.size()]), 1);

        }


    }
    private void bindEvent(){
        cbZbzl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    zbzl="1";
                }else {
                    zbzl="0";
                }
            }
        });

        rbConnectType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_bluetooth:
                        tcpLl.setVisibility(View.GONE);
                        bluetoothLl.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_tcp:
                        tcpLl.setVisibility(View.VISIBLE);
                        bluetoothLl.setVisibility(View.GONE);
                        break;
                }
            }
        });

        tvTcpIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showSetingTcpIpDialog();
            }
        });

        btnTcpConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                btnTcpConnect.setEnabled(false);
                SocketUtils.getInstance().connectServer(handler);
            }
        });
        cbLbgd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sflbgd="1";
                }else {
                    sflbgd="0";
                }
            }
        });

        cbClzj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sfzj="1";
                }else {
                    sfzj="0";
                }
            }
        });
        cbWkce.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sfwk="1";
                }else {
                    sfwk="0";
                }
            }
        });
    }
    private void checkRegister() {


        String rsAcode = VeritityUtil.getRSAcode(this);
        String imei = VeritityUtil.getIMEI(this);

        if (!TextUtils.isEmpty(rsAcode)){
            if (TextUtils.isEmpty(imei)){

                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("未获取到本机IMEI，是否重试?")
                        .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                checkRegister();

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                        return true;
                    }
                });

                alertDialog.show();
            }else {
                if (imei.equals(RSAUtils.decrypt(rsAcode))){
                    //已注册过
                    return;
                }else {
                    showRegisterDialog();
                }
            }


        }else {
            showRegisterDialog();

        }

    }


    private void showRegisterDialog(){
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("您还未注册授权该插件，是否注册授权 ?")
                .setPositiveButton("注册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(RequestAction.this, CollectResisterMsgActivity.class),REGISTER_CODE);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                return true;
            }
        });

        alertDialog.show();
    }
    private String loadFromSDFile(String fname) {
        fname="/TEST/"+fname;
        String result=null;
        try {
            File f=new File(Environment.getExternalStorageDirectory().getPath()+fname);
            if(f.exists())
            {
                int length=(int)f.length();
                byte[] buff=new byte[length];
                FileInputStream fin=new FileInputStream(f);
                fin.read(buff);
                fin.close();
                result=new String(buff,"UTF-8");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private void initViewData() {
        tvTcpIp.setText(SharedPreferencesUtils.getIpAddress()+":"+SharedPreferencesUtils.getPort());
        measure_tv_carnum.setText(cph);
        if(isTwoCar){
            measure_tv_type.setText(R.string.measure_tv_gua);
            measure_tv_type1.setText(R.string.measure_tv_qian);
            measure_tv_carnum1.setText(cph_1);
            measure_ll_two.setVisibility(View.VISIBLE);
        }
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter==null){
            Toast.makeText(this,R.string.measure_error_nobluetooth,Toast.LENGTH_LONG).show();
            finish();
        }
        if(!bluetoothAdapter.enable()){
            Toast.makeText(this,R.string.measure_error_notopen,Toast.LENGTH_LONG).show();
            finish();
        }
        list.clear();
        list.addAll(bluetoothAdapter.getBondedDevices());
        adapter = new BluetoothListAdapter(this, list);
        measure_lv_list.setAdapter(adapter);
        tv_version.setText(W.VERSIONTIMES+ APPUtil.getVersionName(this));
    }

    private void initView() {
        tv_version=(TextView)findViewById(R.id.tv_version);
        measure_tv_name = (TextView) findViewById(R.id.measure_tv_name);
        measure_tv_status = (TextView) findViewById(R.id.measure_tv_status);

        measure_image1 = (ImageView) findViewById(R.id.measure_image1);
        measure_image2 = (ImageView) findViewById(R.id.measure_image2);
        measure_image3 = (ImageView) findViewById(R.id.measure_image3);
        measure_image4 = (ImageView) findViewById(R.id.measure_image4);

        measure_tv_type = (TextView) findViewById(R.id.measure_tv_type);
        measure_tv_carnum = (TextView) findViewById(R.id.measure_tv_carnum);
        measure_tv_length = (TextView) findViewById(R.id.measure_tv_length);
        measure_tv_width = (TextView) findViewById(R.id.measure_tv_width);
        measure_tv_height = (TextView) findViewById(R.id.measure_tv_height);
        measure_tv_space = (TextView) findViewById(R.id.measure_tv_space);
        tv_zbzl = (TextView) findViewById(R.id.tv_zbzl);

        measure_lv_list = (ListView) findViewById(R.id.measure_lv_list);
        measure_btn_cancel = (Button) findViewById(R.id.measure_btn_cancel);
        measure_btn_ok = (Button) findViewById(R.id.measure_btn_ok);
        measure_btn_start = (Button) findViewById(R.id.measure_btn_start);
        measure_btn_stop = (Button) findViewById(R.id.measure_btn_stop);
        cbZbzl=findViewById(R.id.cb_zbzl);
        rbBlueTooth=findViewById(R.id.rb_bluetooth);
        rbTcp=findViewById(R.id.rb_tcp);
        rbConnectType=findViewById(R.id.rb_connect_type);
        tvTcpIp=findViewById(R.id.tv_tcp_ip);
        tvTcpStatus=findViewById(R.id.tv_tcp_status);
        btnTcpConnect=findViewById(R.id.btn_tcp_connect);
        tcpLl=findViewById(R.id.tcp_ll);
        bluetoothLl=findViewById(R.id.bluetooth_ll);
        tvSocketTips=findViewById(R.id.tv_socket_tips);
        cbLbgd=findViewById(R.id.cb_lbgd);
        cbClzj=findViewById(R.id.cb_clzj);
        cbWkce=findViewById(R.id.cb_wkce);
        tvResultTips=findViewById(R.id.tv_result_tips);
        tvLbgd=findViewById(R.id.tv_lbgd);
        btnGetVeh=findViewById(R.id.measure_btn_get_veh);
        btnSaveVeh=findViewById(R.id.measure_btn_save_veh);



        if(isTwoCar){
            measure_ll_two = (LinearLayout) findViewById(R.id.measure_ll_two);
            measure_tv_type1 = (TextView) findViewById(R.id.measure_tv_type1);
            measure_tv_carnum1 = (TextView) findViewById(R.id.measure_tv_carnum1);
            measure_tv_length1 = (TextView) findViewById(R.id.measure_tv_length1);
            measure_tv_width1 = (TextView) findViewById(R.id.measure_tv_width1);
            measure_tv_height1 = (TextView) findViewById(R.id.measure_tv_height1);

        }



    }

    private void initIntentData() {
        Intent intent = getIntent();
        cylsh = intent.getStringExtra("cylsh");
        clsbdh = intent.getStringExtra("clsbdh");
        cph = intent.getStringExtra("cph");
        hpzl = intent.getStringExtra("hpzl");
        cllx = intent.getStringExtra("cllx");
        jylb = intent.getStringExtra("jylb");

        ywlx = intent.getStringExtra("ywlx");
        ycwkc = intent.getStringExtra("ycwkc");
        ycwkk = intent.getStringExtra("ycwkk");
        ycwkg = intent.getStringExtra("ycwkg");
        ywlxs = getIntent().getStringExtra("ywlx");
        isTwoCar = intent.getBooleanExtra("isTwoCar", false);

        if (TextUtils.isEmpty(ywlxs) || ywlxs.trim().length() == 0){
            //ywlx 00 新车  01 在用车
            if ("00".equals(jylb)){
                ywlxs="00";
            }else {
                ywlxs="01";
            }
        }


        StringBuilder builder=new StringBuilder();
        builder
                .append("查验流水号: ").append(cylsh).append("\n")
                .append("车辆识别代号: ").append(clsbdh).append("\n")
                .append("车辆类型: ").append(cllx).append("\n")
                .append("车牌号: ").append(cph).append("\n")
                .append("号牌种类: ").append(hpzl).append("\n")
                .append("jylb: ").append(jylb).append("\n")
                .append("业务类型: ").append(ywlx).append("\n");
        Toast.makeText(this,builder.toString(),Toast.LENGTH_LONG).show();

        if (TextUtils.isEmpty(hpzl)){
            hpzl="01";
        }

        //isTwoCar = true;
        /*cylsh = "123456";
        clsbdh = "HLKSU88464213";
        cph ="川A694PU";
        hpzl = "15";
        cllx = "B10" ;
        jylb = "03";
        ywlxs = "09";

        ywlx = "04";
        ycwkc = "2363" ;
        ycwkk = "256";
        ycwkg = "289";*/





        if (isTwoCar) {//带挂车
            cylsh_1 = intent.getStringExtra("cylsh_1");
            clsbdh_1 = intent.getStringExtra("clsbdh_1");
            cph_1 = intent.getStringExtra("cph_1");
            hpzl_1 = intent.getStringExtra("hpzl_1");
            cllx_1 = intent.getStringExtra("cllx_1");
            jylb_1 = intent.getStringExtra("jylb_1");

//            cylsh_1 = "56464546";
//            clsbdh_1 = "LKHHS1223311";
//            cph_1 = "川A1316";
//            hpzl_1 = "01";
//            cllx_1 = "03";
//            jylb_1 = "00";



        }
        if (TextUtils.isEmpty(cylsh)) {
            Toast.makeText(this, R.string.mesure_intent_null, Toast.LENGTH_SHORT).show();
        }
    }

    private void setBtnEnable(boolean enableStart, boolean enableStop) {
        measure_btn_start.setEnabled(enableStart);
        measure_btn_stop.setEnabled(enableStop);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!(getIntent().getLongExtra("time",TimeUtils.getSystemTime())<TimeUtils.dateToStamp(W.TIMES)) && !"HC".equals(AppInfo.getSystemProperty())){
            Toast.makeText(this,R.string.obsolete,Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },2000);

        }else {
            /*mHelper = new BluetoothSppHelper();
            mHelper.setBlueSocketListener(this);
            IntentFilter intentFilter=new IntentFilter();
            intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
            intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mReceiver, intentFilter);//注册蓝牙查找监听和开关状态监听*/
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*unregisterReceiver(mReceiver);//注销蓝牙查找监听
        mHelper.stop();
        mHelper = null;*/
    }

    public void btnStart(View view) {//开始测量
        //Properties s=new Properties();
        /*if (rbBlueTooth.isChecked()){
            measureByBluetooth();
        }else if (rbTcp.isChecked()){
            measureByTcp();
        }*/

        if (vehicleEntity==null){
            showToast("请先获取车辆信息");
            return;
        }
        measureByTcp();
    }

    private void measureByBluetooth(){
        measure_btn_ok.setEnabled(false);
        mHelper.write(getMeasureData());
        setBtnEnable(false, true);
    }


    private void measureByTcp(){
        if (SocketUtils.getInstance().socketIsConnetServer()){
            SocketUtils.getInstance().sendMsg(getMeasureData(),true);
           // SocketUtils.getInstance().sendMsg("Haaaaaaaaaaaaaaaaaaadddddddddddddddddd");
        }else {
            showToast("socket未连接，无法发送消息");
        }

    }




    private StringMessage getMeasureData(){
        StringMessage stringMessage = new StringMessage();
        if (isTwoCar) {//带挂车
            stringMessage.setContent(DataModel.dataStart(cylsh, clsbdh, cph, hpzl, cllx, jylb,sfwk,sfzj,sflbgd,zbzl,"1",
                    cylsh_1, clsbdh_1, cph_1, hpzl_1, cllx_1, jylb_1));
        } else {//常规车
            stringMessage.setContent(DataModel.dataStart(cylsh, clsbdh, cph, hpzl, cllx, jylb,sfwk,sfzj,sflbgd,zbzl));
        }
        return stringMessage;
    }
    public void btnStop(View view) {//停止测量
        /*if (rbTcp.isChecked()){
            stopMeasureTcp();
        }else if (rbBlueTooth.isChecked()){
            stopMeasureBluetooth();
        }*/

        try {
            stopMeasureTcp();
        }catch (Exception e){
            showToast("停止异常");
        }

    }

    private void stopMeasureBluetooth(){
        measure_btn_ok.setEnabled(false);
        StringMessage stringMessage = new StringMessage();
        stringMessage.setContent(DataModel.dataStop());
        mHelper.write(stringMessage);
        setBtnEnable(true, true);
    }
    private void stopMeasureTcp(){
        StringMessage stringMessage = new StringMessage();
        stringMessage.setContent(DataModel.dataStop());
        SocketUtils.getInstance().sendMsg(stringMessage,false);

       // SocketUtils.getInstance().releaseSocket();

    }
    public void btnCal(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void btnOk(View view) {
        /**
         * 3|1.流水号|2.车架号|3.设备编号|4.长|5.宽|6.高|7.轴距|8.栏板高度|9.整备质量|10.是否头挂|11.牵引车流水号
         * |12.牵引车车架号|13.牵引车长|14.牵引车宽|15牵引车高|16牵引车轴距|17.牵引车整备质量
         */
        Intent intent = new Intent();
        intent.putExtra("cylsh", dataArr[1]);
        intent.putExtra("clsbdh", dataArr[2]);
        intent.putExtra("bh", dataArr[3]);
        intent.putExtra("cwkc", dataArr[4]);
        intent.putExtra("cwkk", dataArr[5]);
        intent.putExtra("cwkg", dataArr[6]);
        intent.putExtra("zj", dataArr[7]);
        intent.putExtra("isTwoCar", isTwoCar);
        if (getIntent().getStringExtra("keystr") != null) {
            intent.putExtra("keystr", getIntent().getStringExtra("keystr"));
        } else intent.putExtra("keystr", "");
        if (isTwoCar) {//带挂车
            intent.putExtra("cylsh_1", dataArr[9]);
            intent.putExtra("clsbdh_1", dataArr[10]);
            intent.putExtra("bh_1", dataArr[11]);
            intent.putExtra("cwkc_1", dataArr[12]);
            intent.putExtra("cwkk_1", dataArr[13]);
            intent.putExtra("cwkg_1", dataArr[14]);
            intent.putExtra("zj_1", dataArr[15]);
        }
        int imageLen = imagePathList.size();
        for (int i = 0; i < imageLen; i++) {
            intent.putExtra("image" + i, imagePathList.get(i));
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setBluetooth(String status) {
        if (status != null) {
            measure_tv_status.setText(status);
        }
    }


    public static void onItemClick(final BluetoothDevice device) {
        measure_tv_name.setText(device.getName());
        mHelper.connect(device);
    }

    @Override
    public void onBlueSocketStatusChange(BlueSocketBaseThread.BlueSocketStatus status, BluetoothDevice remoteDevice) {
        setBluetooth(handlerStatus(status));
    }

    private String handlerStatus(BlueSocketBaseThread.BlueSocketStatus status) {
        if (status == BlueSocketBaseThread.BlueSocketStatus.ACCEPTED) {
            return getString(R.string.status_accepted);
        }
        if (status == BlueSocketBaseThread.BlueSocketStatus.CONNECTIONING) {
            return getString(R.string.status_connecting);
        }
        if (status == BlueSocketBaseThread.BlueSocketStatus.CONNEDTIONED) {
            setBtnEnable(true, false);//处理按钮
            return getString(R.string.status_connected);
        }
        if (status == BlueSocketBaseThread.BlueSocketStatus.DISCONNECTION) {
            setBtnEnable(false, false);//处理按钮
            return getString(R.string.status_disconnection);
        }
        if (status == BlueSocketBaseThread.BlueSocketStatus.MESSAGERECEIVE) {
            return getString(R.string.status_messagerecive);
        }
        return null;
    }

    @Override
    public void onBlueSocketMessageReceiver(IMessage message) {
        if (message instanceof ImageMessage) {//接收到图片

            final ImageMessage imageMessage = (ImageMessage) message;
            String path=imageMessage.getContent().getAbsolutePath();
            int type=imageMessage.getType();
            imagePathList.add(path);
            setRecvImageData(path,type);

            StringMessage feedBackMsg = new StringMessage();
            feedBackMsg.setContent(DataModel.dataReceived(type + ""));
            //Toast.makeText(this,type + "",Toast.LENGTH_LONG).show();
            mHelper.write(feedBackMsg);
        } else if (message instanceof StringMessage) {//接收到数据
            /**
             * 3|1.流水号|2.车架号|3.设备编号|4.长|5.宽|6.高|7.轴距|8.栏板高度|9.整备质量|10.是否头挂|11.牵引车流水号
             * |12.牵引车车架号|13.牵引车长|14.牵引车宽|15牵引车高|16牵引车轴距|17.牵引车整备质量
             */
            final StringMessage stringMessage = (StringMessage) message;
            String data = stringMessage.getContent();
            dataArr = data.split("\\|");
            if (dataArr[0].equals(DataModel.START)) {//开始反馈
                Toast.makeText(this, R.string.mesure_stared, Toast.LENGTH_SHORT).show();
                return;
            }
            if (dataArr[0].equals(DataModel.STOP)) {//停止反馈
                Toast.makeText(this, R.string.mesure_stoped, Toast.LENGTH_SHORT).show();
                return;
            }
            if (dataArr[0].equals(DataModel.RECV)) {//接收到结果数据
                //给服务器反馈
                StringMessage feedBackMsg = new StringMessage();
                //String cllx=getIntent().getStringExtra("cllx");
                int ycwkc=s2d2i(isNulls(getIntent().getStringExtra("ycwkc")));
                int ycwkk=s2d2i(isNulls(getIntent().getStringExtra("ycwkk")));
                int ycwkg=s2d2i(isNulls(getIntent().getStringExtra("ycwkg")));
                int ycwzbzl=s2d2i(isNulls(getIntent().getStringExtra("ycwzbzl")));
                int clcwkc=0,clcwkk=0,clcwkg=0;
                int clcwkc_l=0,clcwkk_l=0,clcwkg_l=0;
                int clzzl=0,clzj=0,cllbdu=0;//测量总质量，测量轴距，测量栏板高度
                int clzzl_l=0;//挂车 测量总质量
                //轴距同一个，栏板 同一个
                if (dataArr!=null&&dataArr.length>5) { clcwkc=s2d2i(isNulls(dataArr[4]));}
                if (dataArr!=null&&dataArr.length>6) { clcwkk=s2d2i(isNulls(dataArr[5]));}
                if (dataArr!=null&&dataArr.length>7){clcwkg=s2d2i(isNulls(dataArr[6]));}
                if (dataArr!=null&&dataArr.length>8){clzj=s2d2i(isNulls(dataArr[7]));}
                if (dataArr!=null&&dataArr.length>9){cllbdu=s2d2i(isNulls(dataArr[8]));}
                if (dataArr!=null&&dataArr.length>10){clzzl=s2d2i(isNulls(dataArr[9]));}

                if (dataArr!=null&&dataArr.length>14){clcwkc_l=s2d2i(isNulls(dataArr[13]));}
                if (dataArr!=null&&dataArr.length>15){clcwkk_l=s2d2i(isNulls(dataArr[14]));}
                if (dataArr!=null&&dataArr.length>16){clcwkg_l=s2d2i(isNulls(dataArr[15]));}
                if (dataArr!=null&&dataArr.length>18){clzzl_l=s2d2i(isNulls(dataArr[17]));}


                /**
                 *  3|123456|HLKSU88464213||999|999|999|999|0|1000|1|56464546|LKHHS1223311|888|888|888|888|
                 *  反馈判定结果 3|1|外廓判定|轴距判定|栏板高度判定|整备质量判定|头挂|牵引车外廓判定|牵引车轴距判定|牵引车整备质量判定
                 */

                String str=new VehCrPDAMeasureUtils().checkVehMeasure(   cllx,  ywlxs, ycwkc, ycwkk, ycwkg,  clcwkc, clcwkk,  clcwkg,  cyzzl, 0);

                tvResultTips.setText("判定结果:     "+str);
                //feedBackMsg.setContent(DataModel.dataReceived("1|"+"长误差值42mm(≤50mm),误差比0.382 % (≤1 %),宽误差值-54mm(≤50mm),误差比0.157 % (≤1 %),高误差值15mm(≤50mm),误差比0.493 % (≤1 %)"));
                feedBackMsg.setContent(DataModel.dataReceived("1|"+str));
                mHelper.write(feedBackMsg);

                if (!isTwoCar && dataArr.length != 11) {
                    Toast.makeText(this, getString(R.string.mesure_recev_err) + ":" + data, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isTwoCar && dataArr.length != 17) {
                    Toast.makeText(this, getString(R.string.mesure_recev_err1) + ":" + data, Toast.LENGTH_SHORT).show();
                    return;
                }

                setRecvData();//更新数据到UI
                Toast.makeText(this, R.string.mesure_recev_ok, Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessageDelayed(1111, 2000);//延时断开
            }
        } else {
            Toast.makeText(this, "unknown message", Toast.LENGTH_SHORT).show();
        }
    }

    private void setRecvImageData(String path, int type) {
        if(type==2){
            Glide.with(this).load(path).into(measure_image1);
            measure_image1.setVisibility(View.VISIBLE);
            return;
        }
        if(type==3){
            Glide.with(this).load(path).into(measure_image2);
            measure_image2.setVisibility(View.VISIBLE);
            return;
        }
        if(type==4){
            Glide.with(this).load(path).into(measure_image3);
            measure_image3.setVisibility(View.VISIBLE);
            return;
        }
        if(type==5){
            Glide.with(this).load(path).into(measure_image4);
            measure_image4.setVisibility(View.VISIBLE);
            return;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            switch (msg.what) {
                case 1111:
                    mHelper.stop();
                    measure_btn_ok.setEnabled(true);
                    break;
                case SocketUtils.SOCKET_CONNECT_SUCCESS:
                    tvTcpStatus.setText("连接成功");
                    tvTcpStatus.setTextColor(getResources().getColor(R.color.blue));
                    btnTcpConnect.setEnabled(true);
                    tvSocketTips.setText(getString(R.string.connect_server_success_tips));
                    tvSocketTips.setTextColor(getResources().getColor(R.color.blue));
                    SocketUtils.getInstance().startCheckSocketConnect();
                    setBtnEnable(true,true);
                    break;
                case SocketUtils.SOCKET_CONNECT_FAIL:
                    tvTcpStatus.setText("连接失败");
                    tvTcpStatus.setTextColor(getResources().getColor(R.color.red));
                    btnTcpConnect.setEnabled(true);
                    tvSocketTips.setText(getString(R.string.connect_server_fail_tips));
                    tvSocketTips.setTextColor(getResources().getColor(R.color.red));
                    setBtnEnable(false,true);
                    break;
                case SocketUtils.SOCKET_SEND_MSG_SUCCESS:
                    tvSocketTips.setText(getString(R.string.wait_result_tips));
                    tvSocketTips.setTextColor(getResources().getColor(R.color.blue));
                    resetUI();
                    break;
                case SocketUtils.SOCKET_SEND_MSG_FAIL:
                    tvSocketTips.setText(getString(R.string.send_fail_tips));
                    tvSocketTips.setTextColor(getResources().getColor(R.color.red));
                    break;
                case SocketUtils.SOCKET_READ_SERVER_DATA:
                    IMessage message= (IMessage) obj;
                    //showToast("收到数据:   "+ obj);
                    distinguishMessage(message);
                    break;
                case SocketUtils.SOCKET_READ_ERROR:

                    showToast("socket read error");
                    break;
                case SocketUtils.SOCKET_CLOSE:


                    break;

                case SocketUtils.SOCKET_DISCONNECT:
                    tvSocketTips.setText(getString(R.string.socket_disconnect_tips));
                    tvSocketTips.setTextColor(getResources().getColor(R.color.red));
                    tvTcpStatus.setText("连接已断开");
                    tvTcpStatus.setTextColor(getResources().getColor(R.color.red));

                    break;


                case  GET_VHE_CODE:
                    btnGetVeh.setEnabled(true);

                    try {
                        vehicleEntity = gson.fromJson(msg.obj.toString(), VehicleEntity.class);
                        showToast("success    "+  msg.obj.toString());
                    }catch (Exception e){
                        showToast(msg.obj.toString());
                    }
                    break;

                case  SAVE_VHE_CODE:
                    btnSaveVeh.setEnabled(true);

                    try {
                        BaseEntity baseEntity = gson.fromJson(msg.obj.toString(), BaseEntity.class);
                        if (baseEntity.isStatus() && baseEntity.getCode() == 0){
                            showToast("上传成功  "+  baseEntity.getMessage());
                            measure_btn_ok.setEnabled(true);
                        }else {
                            showToast(baseEntity.getMessage());
                        }

                    }catch (Exception e){
                        showToast(msg.obj.toString());
                    }
                    break;



            }
        }
    };

    private void distinguishMessage(IMessage message){
        if (message instanceof ImageMessage) {//接收到图片
            final ImageMessage imageMessage = (ImageMessage) message;
            String path=imageMessage.getContent().getAbsolutePath();
            int type=imageMessage.getType();
            imagePathList.add(path);
            setRecvImageData(path,type);
            StringMessage feedBackMsg = new StringMessage();
            feedBackMsg.setContent(DataModel.dataReceived(type + ""));
            SocketUtils.getInstance().sendMsg(feedBackMsg,false);
        } else if (message instanceof StringMessage){

            /**
             * 3|1.流水号|2.车架号|3.设备编号|4.长|5.宽|6.高|7.轴距|8.栏板高度|9.整备质量|10.是否头挂|11.牵引车流水号
             * |12.牵引车车架号|13.牵引车长|14.牵引车宽|15牵引车高|16牵引车轴距|17.牵引车整备质量
             */
            final StringMessage stringMessage = (StringMessage) message;
            String data = stringMessage.getContent();
            dataArr = data.split("\\|");
            if (dataArr[0].equals(DataModel.START)) {//开始反馈
                Toast.makeText(this, R.string.mesure_stared, Toast.LENGTH_SHORT).show();
                return;
            }
            if (dataArr[0].equals(DataModel.STOP)) {//停止反馈
                Toast.makeText(this, R.string.mesure_stoped, Toast.LENGTH_SHORT).show();
                tvSocketTips.setText(getString(R.string.mesure_stoped));
                return;
            }
            if (dataArr[0].equals(DataModel.RECV)) {//接收到结果数据
                //给服务器反馈
                StringMessage feedBackMsg = new StringMessage();
                //String cllx=getIntent().getStringExtra("cllx");
                int ycwkc=s2d2i(isNulls(getIntent().getStringExtra("ycwkc")));
                int ycwkk=s2d2i(isNulls(getIntent().getStringExtra("ycwkk")));
                int ycwkg=s2d2i(isNulls(getIntent().getStringExtra("ycwkg")));
                int ycwzbzl=s2d2i(isNulls(getIntent().getStringExtra("ycwzbzl")));
                int clcwkc=0,clcwkk=0,clcwkg=0;
                int clcwkc_l=0,clcwkk_l=0,clcwkg_l=0;
                int clzzl=0,clzj=0,cllbdu=0;//测量总质量，测量轴距，测量栏板高度
                int clzzl_l=0;//挂车 测量总质量
                //轴距同一个，栏板 同一个
                if (dataArr!=null&&dataArr.length>5) { clcwkc=s2d2i(isNulls(dataArr[4]));}
                if (dataArr!=null&&dataArr.length>6) { clcwkk=s2d2i(isNulls(dataArr[5]));}
                if (dataArr!=null&&dataArr.length>7){clcwkg=s2d2i(isNulls(dataArr[6]));}
                if (dataArr!=null&&dataArr.length>8){clzj=s2d2i(isNulls(dataArr[7]));}
                if (dataArr!=null&&dataArr.length>9){cllbdu=s2d2i(isNulls(dataArr[8]));}
                if (dataArr!=null&&dataArr.length>10){clzzl=s2d2i(isNulls(dataArr[9]));}

                if (dataArr!=null&&dataArr.length>14){clcwkc_l=s2d2i(isNulls(dataArr[13]));}
                if (dataArr!=null&&dataArr.length>15){clcwkk_l=s2d2i(isNulls(dataArr[14]));}
                if (dataArr!=null&&dataArr.length>16){clcwkg_l=s2d2i(isNulls(dataArr[15]));}
                if (dataArr!=null&&dataArr.length>18){clzzl_l=s2d2i(isNulls(dataArr[17]));}


                /**
                 *  3|123456|HLKSU88464213||999|999|999|999|0|1000|1|56464546|LKHHS1223311|888|888|888|888|
                 *  反馈判定结果 3|1|外廓判定|轴距判定|栏板高度判定|整备质量判定|头挂|牵引车外廓判定|牵引车轴距判定|牵引车整备质量判定
                 */

                String str=new VehCrPDAMeasureUtils().checkVehMeasure(   cllx,  ywlxs, ycwkc, ycwkk, ycwkg,   clcwkc, clcwkk,  clcwkg,  cyzzl,clzzl);

                tvResultTips.setText("判定结果:     "+str);
                //feedBackMsg.setContent(DataModel.dataReceived("1|"+"长误差值42mm(≤50mm),误差比0.382 % (≤1 %),宽误差值-54mm(≤50mm),误差比0.157 % (≤1 %),高误差值15mm(≤50mm),误差比0.493 % (≤1 %)"));
                feedBackMsg.setContent(DataModel.dataReceived("1|"+str));
                SocketUtils.getInstance().sendMsg(feedBackMsg,false);

                if (!isTwoCar && dataArr.length != 11) {
                    Toast.makeText(this, getString(R.string.mesure_recev_err) + ":" + data, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isTwoCar && dataArr.length != 17) {
                    Toast.makeText(this, getString(R.string.mesure_recev_err1) + ":" + data, Toast.LENGTH_SHORT).show();
                    return;
                }
                measure_btn_ok.setEnabled(true);
                setRecvData();//更新数据到UI
                Toast.makeText(this, R.string.mesure_recev_ok, Toast.LENGTH_SHORT).show();

                tvSocketTips.setText(getString(R.string.mesure_recev_ok));
                tvSocketTips.setTextColor(getResources().getColor(R.color.blue));


                setVehicleResult(clcwkc,clcwkk,clcwkg,clzzl);

            }


        }
    }

    private void setVehicleResult(int clc,int clk,int clg,int clzbzl) {

        if (vehicleEntity==null || vehicleEntity.getData() == null){
            return;
        }
        if (ywlx==null){
            ywlx="00";
        }
        VehCrPDAMeasureUtils vehCrPDAMeasureUtils=new VehCrPDAMeasureUtils();
        if (clc>0){
            VehCrPDAMeasureUtils.CheckResult checkResultLength = vehCrPDAMeasureUtils.checkVehMeasureForDetail(ywlx, "长", vehicleEntity.getData().getCwkc(), clc);
            vehicleEntity.getData().setClcsfhg(String.valueOf(checkResultLength.getCode()));
            vehicleEntity.getData().setClc(String.valueOf(clc));
            vehicleEntity.getData().setClcsm(checkResultLength.getMessage());
        }
        if (clk>0){
            VehCrPDAMeasureUtils.CheckResult checkResultWide = vehCrPDAMeasureUtils.checkVehMeasureForDetail(ywlx, "宽", vehicleEntity.getData().getCwkk(), clk);
            vehicleEntity.getData().setClksfhg(String.valueOf(checkResultWide.getCode()));
            vehicleEntity.getData().setClk(String.valueOf(clk));
            vehicleEntity.getData().setClksm(checkResultWide.getMessage());
        }
        if (clg>0){
            VehCrPDAMeasureUtils.CheckResult checkResultHeight = vehCrPDAMeasureUtils.checkVehMeasureForDetail(ywlx, "高", vehicleEntity.getData().getCwkg(), clg);
            vehicleEntity.getData().setClgsfhg(String.valueOf(checkResultHeight.getCode()));
            vehicleEntity.getData().setClg(String.valueOf(clg));
            vehicleEntity.getData().setClgsm(checkResultHeight.getMessage());
        }
        if (clzbzl>0){
            VehCrPDAMeasureUtils.CheckResult checkResultWeight = vehCrPDAMeasureUtils.checkVehMeasureZbzl(ywlx, vehicleEntity.getData().getZbzl(), clzbzl);
            vehicleEntity.getData().setClzbzlsfhg(String.valueOf(checkResultWeight.getCode()));
            vehicleEntity.getData().setClzbzl(String.valueOf(clzbzl));
            vehicleEntity.getData().setClzbzlsm(checkResultWeight.getMessage());
        }





    }

    private void resetUI(){
        measure_tv_length.setText(getString(R.string.measure_tv_null));
        measure_tv_width.setText(getString(R.string.measure_tv_null));
        measure_tv_height.setText(getString(R.string.measure_tv_null));
        tv_zbzl.setText(getString(R.string.measure_tv_null));
        tvLbgd.setText(getString(R.string.measure_tv_null));
        measure_tv_space.setText(getString(R.string.measure_tv_null));
        if (measure_tv_length1!=null){
            measure_tv_length1.setText(getString(R.string.measure_tv_null));
        }
        if (measure_tv_width1!=null){
            measure_tv_width1.setText(getString(R.string.measure_tv_null));
        }
        if (measure_tv_height1!=null){
            measure_tv_height1.setText(getString(R.string.measure_tv_null));
        }
        measure_image1.setImageBitmap(null);
        measure_image2.setImageBitmap(null);
        measure_image3.setImageBitmap(null);
        measure_image4.setImageBitmap(null);
        tvResultTips.setText("");
        measure_btn_ok.setEnabled(false);

        imagePathList.clear();

    }

    private void setRecvData() {

        /**
         * 3|1.流水号|2.车架号|3.设备编号|4.长|5.宽|6.高|7.轴距|8.栏板高度|9.整备质量|10.是否头挂|11.牵引车流水号
         * |12.牵引车车架号|13.牵引车长|14.牵引车宽|15牵引车高|16牵引车轴距|17.牵引车整备质量
         */
        measure_tv_length.setText(dataArr[4]+"mm");
        measure_tv_width.setText(dataArr[5]+"mm");
        measure_tv_height.setText(dataArr[6]+"mm");

        if("1".equals(sfzj) && dataArr.length>8 ){
            measure_tv_space.setText(dataArr[7]+"mm");
        }
        if("1".equals(sflbgd) && dataArr.length>9 ){
            tvLbgd.setText(dataArr[8]+"mm");
        }

        if("1".equals(zbzl) && dataArr.length>10 ){
            tv_zbzl.setText(dataArr[9]+" kg");
        }
        if (isTwoCar && dataArr.length>16) {
            measure_tv_length1.setText(dataArr[13]+"mm");
            measure_tv_width1.setText(dataArr[14]+"mm");
            measure_tv_height1.setText(dataArr[15]+"mm");
            //measure_tv_space1.setText(dataArr[16]+"mm");
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                if(blueState==BluetoothAdapter.STATE_ON){
                    list.clear();
                    list.addAll(bluetoothAdapter.getBondedDevices());
                    adapter.updateList(list);
                }else if(blueState==BluetoothAdapter.STATE_OFF){
                    Toast.makeText(RequestAction.this,R.string.measure_error_notopen,Toast.LENGTH_LONG).show();
                    finish();
                }
            } /*else if(intent.getAction().equals(BluetoothDevice.ACTION_FOUND)){
                boolean isHas = false;
                BluetoothDevice foundedDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                for (BluetoothDevice device : list) {
                    if (device.getAddress().equalsIgnoreCase(foundedDevice.getAddress())) {
                        isHas = true;
                        break;
                    }
                }
                if (!isHas) {
                    list.add(foundedDevice);
                    adapter.updateList(list);
                }
            }*/
        }
    };

    public void btnImage1(View view) {
        showImage(0);
    }
    public void btnImage2(View view) {
        showImage(1);
    }
    public void btnImage3(View view) {
        showImage(2);
    }
    public void btnImage4(View view) {
        showImage(3);
    }
    public void showImage(int index) {
        Intent intent=new Intent(this,PhotoShowActivity.class);
        intent.putExtra("path",imagePathList.get(index));
        startActivity(intent);
    }

    @Override
    public void onChooseImage(String path) {

    }
    /**
     * 字符串--》double--》整型
     */
    public static int s2d2i(String p) {
        return (int) (Double.parseDouble(p) / 1);
    }
    /**
     * 处理空字符串
     */
    public static String isNulls(String obj) {
        String content = "0";
        if (obj != null && !obj.equals("") && !obj.equals("null"))
            content = obj.toString();
        return content;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REGISTER_CODE && resultCode == RESULT_OK){
            checkRegister();
        }
    }

    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void showSetingTcpIpDialog(){
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_seting_tcp_ip);
        dialog.setCanceledOnTouchOutside(false);
       final EditText etIp=dialog.findViewById(R.id.et_ip);
       final EditText etPort=dialog.findViewById(R.id.et_port);
        Button btnSave=dialog.findViewById(R.id.btn_save);
        Button btnSaveAndConnect=dialog.findViewById(R.id.btn_save_and_connect);
        etIp.setText(SharedPreferencesUtils.getIpAddress());
        etPort.setText(SharedPreferencesUtils.getPort());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSaveAndConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = etIp.getText().toString();
                String port = etPort.getText().toString();
                if (TextUtils.isEmpty(ip)){
                    showToast("请输入ip地址");
                    return;
                }
                if (TextUtils.isEmpty(port)){
                    showToast("请输入端口号");
                    return;
                }
                if (!port.equals(SharedPreferencesUtils.getPort()) || !ip.equals(SharedPreferencesUtils.getIpAddress())){
                    SocketUtils.getInstance().releaseSocket();
                }
                SharedPreferencesUtils.setIpAddress(ip);
                SharedPreferencesUtils.setPort(port);
                tvTcpIp.setText(ip+":"+port);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketUtils.getInstance().releaseSocket();
        handler.removeCallbacksAndMessages(null);
    }


    public void btnGetVeh(View view) {
        Map<String,String> map=new HashMap<>(3);
        //map.put("lsh",cylsh);
        map.put("lsh","1191229848395");
        btnGetVeh.setEnabled(false);
        showToast("正在获取车辆信息，请稍后...");
        HttpService.getInstance().getDataFromServer(map, Constant.GET_VHEICLE_MESSAGE,"GET",handler,GET_VHE_CODE);
    }

    public void btnSaveVeh(View view) {

        setVehicleResult(4000,1500,2000,2500);

        String s = gson.toJson(vehicleEntity.getData());
        btnSaveVeh.setEnabled(false);
        showToast("正在上传车辆外廓测量结果，请稍后...");
        HttpService.getInstance().getDataFromServerByJson(s, Constant.SAVE_VEH_MEASUER_RESULT,handler,SAVE_VHE_CODE);


    }

    public void btnSetting(View view) {
        startActivity(new Intent(this,SettingHttpActivity.class));
    }
}
