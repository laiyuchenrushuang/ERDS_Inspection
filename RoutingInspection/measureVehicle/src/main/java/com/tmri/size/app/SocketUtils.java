package com.tmri.size.app;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.hc.library.bluesocket.message.IMessage;
import com.hc.library.bluesocket.message.StringMessage;
import com.hc.library.bluesocket.utils.TypeUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tmri on 2019/7/17.
 */

public class SocketUtils {


    private static  String TAG="SocketUtils";
    private Socket mSocket;
    private BufferedReader mBufferedReader;
    private BufferedWriter mBufferedWriter;
    private static final int PORT=5464;
    private static final String IP="192.168.0.160"; //跑在电脑上的 用 10.0.2.2 访问 本机电脑 IP

    private static SocketUtils mSocketUtils=null;

    public static final int SOCKET_CONNECT_SUCCESS=2000;
    public static final int SOCKET_CONNECT_FAIL=2001;
    public static final int SOCKET_SEND_MSG_SUCCESS=2002;
    public static final int SOCKET_SEND_MSG_FAIL=2003;
    public static final int SOCKET_ERROR=2004;
    public static final int SOCKET_READ_SERVER_DATA=2005;
    public static final int SOCKET_READ_ERROR=2006;
    public static final int SOCKET_CLOSE=2007;
    public static final int SOCKET_DISCONNECT=2008;
    private InputStream inputStream;
    private PrintWriter writer;
    private OutputStream outputStream;
    private boolean isRuning=true;

    private SocketUtils(){}

    private Timer timer=null;

    private Handler mHandler;
    public synchronized static SocketUtils getInstance(){//
        if (mSocketUtils == null){
            synchronized (SocketUtils.class){
                if (mSocketUtils==null){
                    mSocketUtils=new SocketUtils();
                }
            }

        }
        return mSocketUtils;
    }


    public void startCheckSocketConnect(){
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                //利用不停的发送消息来监听服务是否关闭
                try {
                    outputStream.write("0".getBytes());
                    //writer.write(msg);
                    outputStream.flush();
                } catch (Exception e) {
                    mHandler.sendEmptyMessage(SOCKET_DISCONNECT);
                    cancel();

                }
            }
        },1000,2000);
    }


    public boolean socketIsConnetServer(){
        if (mSocket!=null && mSocket.isConnected()){
            return true;
        }
        return false;
    }

    public void connectServer(Handler handler) {
        this.mHandler=handler;
       final String ip = SharedPreferencesUtils.getIpAddress();
       final String port = SharedPreferencesUtils.getPort();
        Log.i(TAG, "连接服务端 ip "+ip  +" 端口 "+port);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket(ip, Integer.valueOf(port));
                    outputStream = mSocket.getOutputStream();
                    writer = new PrintWriter(new OutputStreamWriter(mSocket.getOutputStream(), "utf-8"));
                    inputStream=mSocket.getInputStream();
                    mHandler.sendEmptyMessage(SOCKET_CONNECT_SUCCESS);
                    Log.i(TAG, "连接服务端成功");
                    readServerMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i(TAG, "连接服务端失败 "+e.getMessage());
                    mHandler.sendEmptyMessage(SOCKET_CONNECT_FAIL);

                }
            }
        }).start();
    }

    public void sendMsg(final StringMessage msg,final boolean needCallback) {
        // 如果mSocket为null有可能两种情况：
        // 1.还在尝试连接服务端
        // 2.连接失败
        if (mSocket == null || mSocket.isClosed()){
            mHandler.sendEmptyMessage(SOCKET_SEND_MSG_FAIL);
            return;
        }
        Log.i(TAG, "发送到服务端数据:  " + msg);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    msg.writeContent(outputStream);
                    //writer.write(msg);
                    outputStream.flush();
                    if (needCallback){
                        mHandler.sendEmptyMessage(SOCKET_SEND_MSG_SUCCESS);
                    }

                } catch (Exception e) {
                    if (needCallback){
                        mHandler.sendEmptyMessage(SOCKET_SEND_MSG_FAIL);
                    }

                }
            }
        }).start();

    }

    /**
     * 读取服务器发来的信息，并通过Handler发给UI线程
     */
    public void readServerMessage() {
        try {
            /*int i=0;
            byte[] bytes = new byte[1024];
            while ((i = inputStream.read(bytes))!=-1){
               // String str = new String(bytes,0,i,"utf-8");
                IMessage iMessage = TypeUtils.readHeader(inputStream);
                Message message = Message.obtain();
                message.what=SOCKET_READ_SERVER_DATA;
                message.obj=iMessage;
                mHandler.sendMessage(message);
            }*/
            isRuning=true;
            while (isRuning){
                // String str = new String(bytes,0,i,"utf-8");
                IMessage miMessage = TypeUtils.readHeader(inputStream);
                if (miMessage!=null){
                    miMessage.parseContent(inputStream);
                    Log.i(TAG, "iMessage  "+miMessage.toString());
                    Message message = Message.obtain();
                    message.what=SOCKET_READ_SERVER_DATA;
                    message.obj=miMessage;
                    mHandler.sendMessage(message);
                }

            }
        } catch (Exception e) {
            isRuning=false;
            Log.i(TAG, "Exception: " +e.getMessage());
            mHandler.sendEmptyMessage(SOCKET_ERROR);
        }
    }


    public void  releaseSocket(){
        try {
            if(mSocket != null){
                isRuning=false;
                mSocket.shutdownInput();
                mSocket.shutdownOutput();
                mSocket.close();
                if (mHandler!=null){
                    mHandler.sendEmptyMessage(SOCKET_CLOSE);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
