package com.seatrend.routinginspection;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.JsonSyntaxException;
import com.seatrend.http_sdk.HttpService;
import com.seatrend.http_sdk.NormalView;
import com.seatrend.http_sdk.base.ConmmonResponse;
import com.seatrend.http_sdk.inter.base.BaseService;
import com.seatrend.routinginspection.base.Constants;
import com.seatrend.routinginspection.db.DbUtils;
import com.seatrend.routinginspection.db.table.JudgeTable;
import com.seatrend.routinginspection.db.table.PictureTable;
import com.seatrend.routinginspection.db.table.PlanTable;
import com.seatrend.routinginspection.entity.*;
import com.seatrend.routinginspection.http.HttpRequest;
import com.seatrend.routinginspection.utils.GsonUtils;
import com.seatrend.routinginspection.utils.LogUtil;
import com.seatrend.routinginspection.utils.ServiceUtils;

import java.io.File;
import java.util.*;

/**
 * Created by ly on 2020/7/9 9:32
 */
public class PhotoUploadService extends Service {
    private final Timer timer = new Timer();
    private static final int PERIOD = 10 * 1000;
    private static final int PERIOD_24 = 24 * 60 * 60 * 1000;
    private static final String PUS_TAG = "PhotoUploadService";
    private static final int CHECK_UPLOAD = 1009;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer.schedule(timerTask, 0, PERIOD);
        Log.i(PUS_TAG, "上传照片服务已创建");
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(PUS_TAG, "上传照片服务已启动");
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHECK_UPLOAD:
                    checkUpload();  //在线的数据库上传任务

                    checkLocalDB(); //离线模式的上传任务

                    break;
            }
        }
    };

    private void checkLocalDB() {

        //上传判定内容(先删除PlanTable的表格  why?  不要让它进去查看，因为数据上传会删除)
        upLoadJudge();

        //上传本地数据库图片
        upLoadLocalPhotos();

    }

    private void upLoadLocalPhotos() {

        //查询本地数据库照片数据
        List<PictureTable> list = DbUtils.getInstance(this).searchPictureTableAll();
        synchronized (list) {
            int listSize = list.size();
            if (listSize > 0) {
                Log.d(PUS_TAG, "[离线] 图片数据大小 = " + listSize);
                Log.d(PUS_TAG, "[离线] 图片数据内容 = " + GsonUtils.toJson(list));
                for (int i = 0; i < listSize; i++) {
                    if (TextUtils.isEmpty(list.get(i).getZpId())) { //服务器没返回zpdz 需要请求
                        //无照片地址 需要file上传
                        File file = new File(list.get(i).getZpPath());
                        if (file.exists()) {  //保证每次上传的file都存在
                            fileupload(list.get(i));
                        } else {
                            DbUtils.getInstance(PhotoUploadService.this).deletePhotoBean(list.get(i).getId());
                        }
                    } else {
                        savePhotoMsgToServer(list.get(i));
                    }
                }

            } else {
                Log.d(PUS_TAG, "[离线] 图片列表为空");
            }
        }
    }


    private void upLoadJudge() {
        ArrayList<JudgeTaskEntity.DataBean> list = new ArrayList<>();

        //第一步  查询plan表格的计划编号的list
        List<PlanTable> listPlan = DbUtils.getInstance(this).searchPlanTableAllIsDone();

        Log.d(PUS_TAG, "[离线] 判定内容提交列表 = " + GsonUtils.toJson(listPlan));
        //如果没数据
        if (listPlan == null || listPlan.size() <= 0) {
            Log.d(PUS_TAG, "[离线] 判定内容提交列表为空");
            return;
        }

        //如果有计划列表
        for (int i = 0; i < listPlan.size(); i++) {

            String jhbh = listPlan.get(i).getLsh(); // 计划编号
            String jhbz = listPlan.get(i).getJhbz(); // 计划备注
            String yhdh = listPlan.get(i).getUserdh(); // 用户代号

            //查询JudeTable 是否有计划任务判定项的存储
            List<JudgeTable> judgeTables = DbUtils.getInstance(this).searchJudgeByWhere(jhbh);

            List<JudgeTaskEntity.DataBean> postJudgetaskEntityList = new ArrayList<>();

            //如果有判定项数据
            if (judgeTables != null && judgeTables.size() > 0) {
                for (int j = 0; j < judgeTables.size(); j++) {
                    JudgeTaskEntity.DataBean jEntity = new JudgeTaskEntity.DataBean();
                    jEntity.setJhbh(judgeTables.get(j).getLsh());  //计划编号
                    jEntity.setGlbm(judgeTables.get(j).getGlbm()); //管理部门
                    jEntity.setRwbh(judgeTables.get(j).getRwbh()); //任务ID
                    jEntity.setRwzxjg(judgeTables.get(j).getRwzxjg()); //任务结果
                    jEntity.setRwzxr(judgeTables.get(j).getRwzxr()); //任务执行人
                    jEntity.setRwzxrid(yhdh); //用户代号id
                    postJudgetaskEntityList.add(jEntity);
                }
            }
            String taskJsonList = GsonUtils.toJson(postJudgetaskEntityList);

            HashMap<String, String> map = new HashMap<>();
            map.put("taskJsonList", taskJsonList);
            map.put("bz", jhbz);
            map.put("sjly", "0");//数据来源(0手机端，1电脑端)
            //一个计划列表 对应一个上传请求
            HttpRequest.INSTANCE.posT(Constants.URL.INSTANCE.getPOST_TASK(), map, BaseService.class, true, new NormalView() {

                @Override
                public void netWorkTaskSuccess(ConmmonResponse commonResponse) {
                    try {
                        JudgePostCallBack entity = GsonUtils.gson(commonResponse.responseString, JudgePostCallBack.class);
                        String jhbh = entity.getData();
                        if (!TextUtils.isEmpty(jhbh)) {
                            //上传成功 需要删除planTable  和judgeTable里面对应jhbh（lsh）的值
                            DbUtils.getInstance(PhotoUploadService.this).deleteDbPlanJudge(jhbh);
                            Log.d(PUS_TAG, "[离线] 判定内容提交成功 JHBH = " + jhbh);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(PUS_TAG, "[!离线] 判定内容提交发生解析异常 ");
                    }
                }

                @Override
                public void netWorkTaskfailed(ConmmonResponse commonResponse) {
                    Log.d(PUS_TAG, "[!离线]上传判定项失败 ");
                }
            });


        }


    }

    private final TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(CHECK_UPLOAD);
            // 定时 发送 照片 轮循 广播，也就是上传完一次，发送一次，当然也存在无照片上传的情况，但不影响
//            sendPhotoUploadBroadcast();
        }
    };

    private void checkUpload() {

        List<PhotoOnlineDataBean> list = DbUtils.getInstance(this).searchPhotoOnlineDataBeanAll();
        synchronized (list) {
            int listSize = list.size();
            if (listSize > 0) {

                Log.d(PUS_TAG, "[在线] 图片数据大小 = " + listSize);
                Log.d(PUS_TAG, "[在线] 图片数据内容 = " + GsonUtils.toJson(list));


                for (int i = 0; i < listSize; i++) {
                    if (TextUtils.isEmpty(list.get(i).getZpdz())) {

                        //无照片地址 需要file上传
                        File file = new File(list.get(i).getZpPath());
                        if (file.exists()) {  //保证每次上传的file都存在
                            fileupload(list.get(i));
                        } else {
                            DbUtils.getInstance(PhotoUploadService.this).deletePhotoOnlineBean(list.get(i).getId());
                        }
                    } else {
                        savePhotoMsgToServer(list.get(i));
                    }
                }


            } else {
                Log.d(PUS_TAG, "[在线] 图片列表为空");
            }
        }


    }

    //文件保存服务器[在线]
    private void savePhotoMsgToServer(final PhotoOnlineDataBean entity) {

        HashMap<String, String> map = new HashMap<>();
        map.put("jhbh", entity.getJhbh());
        map.put("glbm", entity.getGlbm());
        map.put("zpdz", entity.getZpdz());

        map.put("scbj", entity.getZpPath()); //上传标记 这个地方用zp路径 文件统一加上系统时间 表示唯一 [与后台的约定]

        HttpRequest.INSTANCE.posT(Constants.URL.INSTANCE.getSAVE_FILE(), map, BaseService.class, true, new NormalView() {

            @Override
            public void netWorkTaskSuccess(ConmmonResponse commonResponse) {
                Log.d(PUS_TAG, "[在线] 保存照片信息 成功 = " + commonResponse.getResponseString());


                try {
                    PhotoSaveBackEntity entity = GsonUtils.gson(commonResponse.responseString, PhotoSaveBackEntity.class);
                    String jhbh = entity.getData().getJhbh();
                    String scbj = entity.getData().getScbj();

                    //保存成功 就去删除
                    DbUtils.getInstance(PhotoUploadService.this).deletePhotoOnlineBean(jhbh, scbj);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void netWorkTaskfailed(ConmmonResponse commonResponse) {

                Log.d(PUS_TAG, "[!在线] 保存照片信息 失败 = " + commonResponse.getResponseString());
            }
        });

    }

    //ftp文件上传[在线]
    private void fileupload(final PhotoOnlineDataBean entity) {

        HashMap<String, String> map = new HashMap<>();
        map.put("type", entity.getZpPath());

        HttpRequest.INSTANCE.postOneFileAndMap(Constants.URL.INSTANCE.getPOST_FILE(),
                new File(entity.getZpPath()), map,
                BaseService.class,
                true, new NormalView() {
                    @Override
                    public void netWorkTaskSuccess(ConmmonResponse commonResponse) {
                        Log.d(PUS_TAG, "[在线] 文件上传  成功  = " + commonResponse.getResponseString());
                        if (!TextUtils.isEmpty(commonResponse.getResponseString())) {
                            try {
                                FileUploadEntity fileUploadEntity = GsonUtils.gson(commonResponse.responseString, FileUploadEntity.class);
                                if (fileUploadEntity != null
                                        && fileUploadEntity.getData() != null
                                        && !"".equals(fileUploadEntity.getData())
                                        && !"".equals(fileUploadEntity.getData().getPath())
                                        && null != (fileUploadEntity.getData().getPath()
                                )) {
                                    String zpdz = fileUploadEntity.getData().getPath();  //照片地址[服务器返回]
                                    String type = fileUploadEntity.getData().getType().replace("\"", ""); //标记的那个照片zpPath
//                                    Log.d(PUS_TAG," zpdz =  "+zpdz);
//                                    Log.d(PUS_TAG," type =  "+type);
                                    if (!TextUtils.isEmpty(zpdz)) {
                                        //存储zpdz
                                        DbUtils.getInstance(PhotoUploadService.this).updatePhotoOnlineBean(entity.getJhbh(), type, zpdz);
                                    }
                                }
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void netWorkTaskfailed(ConmmonResponse commonResponse) {

                        Log.d(PUS_TAG, "[!在线] 文件上传  失败  = " + commonResponse.getResponseString());
                    }
                });
    }


    //文件保存服务器[离线]
    private void savePhotoMsgToServer(PictureTable entity) {
        HashMap<String, String> map = new HashMap<>();
        map.put("jhbh", entity.getLsh());
        map.put("glbm", entity.getGlbm());
        map.put("zpdz", entity.getZpId());
        map.put("scbj", entity.getZpPath()); //上传标记 这个地方用zp路径 文件统一加上系统时间 表示唯一 [与后台的约定]

        HttpRequest.INSTANCE.posT(Constants.URL.INSTANCE.getSAVE_FILE(), map, BaseService.class, true, new NormalView() {

            @Override
            public void netWorkTaskSuccess(ConmmonResponse commonResponse) {
                Log.d(PUS_TAG, "[离线] 保存照片信息 成功 = " + commonResponse.getResponseString());


                try {
                    PhotoSaveBackEntity entity = GsonUtils.gson(commonResponse.responseString, PhotoSaveBackEntity.class);
                    String jhbh = entity.getData().getJhbh();
                    String scbj = entity.getData().getScbj();

                    //保存成功 就去删除
                    DbUtils.getInstance(PhotoUploadService.this).deletePictureBean(jhbh, scbj);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void netWorkTaskfailed(ConmmonResponse commonResponse) {

                Log.d(PUS_TAG, "[!离线] 保存照片信息 失败 = " + commonResponse.getResponseString());
            }
        });

    }


    //ftp文件上传[离线]
    private void fileupload(final PictureTable entity) {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", entity.getZpPath());
        HttpRequest.INSTANCE.postOneFileAndMap(Constants.URL.INSTANCE.getPOST_FILE(),
                new File(entity.getZpPath()), map,
                BaseService.class,
                true, new NormalView() {
                    @Override
                    public void netWorkTaskSuccess(ConmmonResponse commonResponse) {
                        Log.d(PUS_TAG, "[离线] 文件上传  成功  = " + commonResponse.getResponseString());
                        if (!TextUtils.isEmpty(commonResponse.getResponseString())) {
                            try {
                                FileUploadEntity fileUploadEntity = GsonUtils.gson(commonResponse.responseString, FileUploadEntity.class);
                                if (fileUploadEntity != null
                                        && fileUploadEntity.getData() != null
                                        && !"".equals(fileUploadEntity.getData())
                                        && !"".equals(fileUploadEntity.getData().getPath())
                                        && null != (fileUploadEntity.getData().getPath()
                                )) {
                                    String zpdz = fileUploadEntity.getData().getPath();  //照片地址[服务器返回]
                                    String type = fileUploadEntity.getData().getType().replace("\"", ""); //标记的那个照片zpPath
//                                    Log.d(PUS_TAG," zpdz =  "+zpdz);
//                                    Log.d(PUS_TAG," type =  "+type);
                                    if (!TextUtils.isEmpty(zpdz)) {
                                        //存储zpdz
                                        DbUtils.getInstance(PhotoUploadService.this).updatePictureBean(entity.getLsh(), type, zpdz);
                                    }
                                }
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void netWorkTaskfailed(ConmmonResponse commonResponse) {

                        Log.d(PUS_TAG, "[!离线] 文件上传  失败  = " + commonResponse.getResponseString());
                    }
                });
    }

    @Override
    public void onDestroy() {


        if (!ServiceUtils.isRunService(this, this.getPackageName() + "." + this.getClass().getSimpleName())) {
            LogUtil.Companion.d(PUS_TAG + " ================================================================");
            Log.d(PUS_TAG, "服务重启 ： " + this.getPackageName() + "." + this.getClass().getSimpleName());
            LogUtil.Companion.d(PUS_TAG + " ================================================================");
            Intent intent = new Intent(Constants.Companion.getPROTECT_SERVICE());
            this.sendBroadcast(intent);
            timer.cancel();
            timerTask.cancel();
        }
        super.onDestroy();
    }
}
