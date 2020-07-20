package com.tmri.size.app.http;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.tmri.size.app.SharedPreferencesUtils;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by seatrend on 2018/8/20.
 */

public class HttpService {

    private static HttpService mHttpService;


    private final  int SUCCESS_CODE=0;
    private final  int FAILED_CODE=1;
    private final  int PREGRESS_CODE=2;

    private final int TIME_OUT=60*1000;


    private OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .connectTimeout(20*1000, TimeUnit.MILLISECONDS)
            .build();


    public static HttpService getInstance() {
        if (mHttpService == null) {
            synchronized (HttpService.class) {
                if (mHttpService == null) {
                    mHttpService = new HttpService();
                }
            }
        }

        return mHttpService;
    }

    private Handler mHandler;

//Request request = addHeaders().url(requestUrl).build();
    public void getDataFromServer(Map<String, String> map, final String url, String method,Handler handler,final int code) {

        this.mHandler=handler;

        final String finalUrl = SharedPreferencesUtils.getNetWorkAddress()+url;
        Request request=null;
        try {

            if(method.equals("GET")){
                StringBuffer buffer=new StringBuffer();
                buffer.append("?");
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if (TextUtils.isEmpty(entry.getValue()) || "null".equals(entry.getValue())){
                        continue;
                    }
                    buffer.append(entry.getKey().trim()+"="+entry.getValue().trim()+"&");
                }
                String s = buffer.toString();
                String parameter=s.substring(0,s.length()-1);
                request = new Request.Builder()
                        .url(finalUrl+parameter)
                        .get()
                        .build();

                Log.i("HttpService",finalUrl+parameter);
            }else {

                FormBody.Builder builder = new FormBody.Builder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if (TextUtils.isEmpty(entry.getValue()) || "null".equals(entry.getValue())){
                        continue;
                    }
                    builder.add(entry.getKey().trim(), entry.getValue().trim());

                }

                RequestBody requestBody = builder.build();

                request = new Request.Builder()
                        .url(finalUrl)
                        .post(requestBody)
                        .build();
                Log.i("HttpService",finalUrl);
            }
        }catch (Exception e){
            Message message = Message.obtain();
            message.what=code;
            message.obj=e.getMessage();
            mHandler.sendMessage(message);
            return;
        }



        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what=code;
                message.obj=e.getMessage();
                mHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = Message.obtain();
                String resp = response.body().string();
                if(TextUtils.isEmpty(resp)){
                    message.what=code;
                    message.obj="服务器响应内容为空";
                    mHandler.sendMessage(message);
                    return;
                }
                message.what=code;
                message.obj=resp;

                mHandler.sendMessage(message);

            }
        });

    }

    public void getDataFromServerByJson(String json, final String url,Handler handler,final int code) {

        this.mHandler=handler;
        final String finalUrl = SharedPreferencesUtils.getNetWorkAddress()+url;

        Request request;
        try {
                MediaType mjson = MediaType.parse("application/json; charset=utf-8");
                RequestBody  requestBody= RequestBody.create(mjson,json);

                  request = new Request.Builder()
                        .url(finalUrl)
                        .post(requestBody)
                        .build();
                Log.i("HttpService",finalUrl);
        }catch (Exception e){
            Message message = Message.obtain();
            message.what=code;
            message.obj=e.getMessage();
            mHandler.sendMessage(message);
            return;
        }



        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what=code;
                message.obj=e.getMessage();
                mHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = Message.obtain();
                String resp = response.body().string();
                if(TextUtils.isEmpty(resp)){
                    message.what=code;
                    message.obj="服务器响应内容为空";
                    mHandler.sendMessage(message);
                    return;
                }
                message.what=code;
                message.obj=resp;
                mHandler.sendMessage(message);
            }
        });

    }




}
