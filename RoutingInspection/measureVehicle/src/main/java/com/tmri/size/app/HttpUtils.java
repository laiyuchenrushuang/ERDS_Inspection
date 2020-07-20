package com.tmri.size.app;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by tmri on 2019/7/17.
 */

public class HttpUtils {


    public static final int SUCCESS=1000;
    public static final int FAIL=1001;


    public static void getHttps(String url, Handler handler) {
        try {
            //根据URL地址实例化一个URL对象，用于创建HttpURLConnection对象。
            URL mURL = new URL(url);
            //openConnection获得当前URL的连接
            HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
            //设置3秒的响应超时
            conn.setConnectTimeout(3000);
            //设置允许输入
            conn.setDoInput(true);
            //设置为GET方式请求数据
            conn.setRequestMethod("GET");
            //获取连接响应码，200为成功，如果为其他，均表示有问题
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                //getInputStream获取服务端返回的数据流。
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = "";
                StringBuffer sb = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                Message message = Message.obtain();
                message.what=SUCCESS;
                message.obj=sb.toString();
                handler.sendMessage(message);
                Log.i("info", responseCode + "  响应文本：\n" + sb.toString());
            }else {
                Message message = Message.obtain();
                message.what=FAIL;
                message.obj=responseCode;
                handler.sendMessage(message);
            }

        }catch (Exception e) {
            Message message = Message.obtain();
            message.what=FAIL;
            message.obj=e.getMessage();
            handler.sendMessage(message);
        }
    }


    /*
BODY体中包含一个请求数据
 */
    private void POSTHttp()  {
        try {
            //1. URL
            URL url = new URL("http://v.juhe.cn/wz/citys");
            //2. HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置3秒的响应超时
            conn.setConnectTimeout(3000);
            //3. POST
            conn.setRequestMethod("POST");
            //设置请求报文头，设定请求数据类型
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //5. 使用输出流  传递请求参数
            conn.setDoOutput(true); //开启输出流
            OutputStream os = conn.getOutputStream();
            String param = "key=相对应的KEY值";
            os.write(param.getBytes("utf-8"));
            os.flush();
            //6.  发送请求获取输入流
            InputStream is = conn.getInputStream();
            //7. 解析is  获取响应文本
            String line = "";
            StringBuffer sb = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            Log.i("info", "post响应文本:\n" + sb.toString());
        }catch ( Exception e){
            e.printStackTrace();
        }

    }

    /*
包含消息头的请求
 */
    private void postHttp2() throws Exception{
        //1. URL
        URL url = new URL("http://api.neekin.com/OBD/getRecord.php");
        //2. HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置3秒的响应超时
        conn.setConnectTimeout(3000);
        //3. POST
        conn.setRequestMethod("POST");
        //设置请求报文头，设定请求数据类型
        //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Cookie","key=dae2c334e2a1451d0a1baaf452053779tH0pt7KstpYHtHYnYHjqJHJptsDpK7JqtpaqJHY1YH0GK7Y1YHwHtHBOYHwHt9YrYPDHY7YrYHjqJHY1YHDHY9YSYH5HY9YLYH5Ht7YLtpYHY7YoYHNHY7YSYHNHYsY1YHYHy9YLtpYHtsYHtsYHY9Yots05");
        //5. 使用输出流  传递请求参数
        conn.setDoOutput(true); //开启输出流
        OutputStream os = conn.getOutputStream();
        //设置BODY体中数据
        String param = "";
        os.write(param.getBytes("utf-8"));
        os.flush();
        //6.  发送请求获取输入流
        InputStream is = conn.getInputStream();
        //7. 解析is  获取响应文本
        String line = "";
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        Log.i("info", "post2---响应文本:\n" + sb.toString());
    }

    /*
请求体中有两个数据时
 */
    private void postHttp3() throws Exception{
        //1. URL
        URL url = new URL("http://api.neekin.com/OBD/getOilPrice.php");
        //2. HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置3秒的响应超时
        conn.setConnectTimeout(3000);
        //3. POST
        conn.setRequestMethod("POST");
        //设置请求报文头，设定请求数据类型
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //5. 使用输出流  传递请求参数
        conn.setDoOutput(true); //开启输出流
        OutputStream os = conn.getOutputStream();
        //设置BODY体中数据
        String param = "city=白城"+"&"+"number=90";
        os.write(param.getBytes("utf-8"));
        os.flush();
        //6.  发送请求获取输入流
        InputStream is = conn.getInputStream();
        //7. 解析is  获取响应文本
        String line = "";
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        Log.i("info", "post3---响应文本:\n" + sb.toString());
    }

}
