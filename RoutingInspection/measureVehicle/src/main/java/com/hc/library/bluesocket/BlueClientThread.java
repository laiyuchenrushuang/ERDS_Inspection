package com.hc.library.bluesocket;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import java.io.IOException;

/**
 * 蓝牙连接客户端连接线程
 *
 * Created by jack.yan on 2017/10/30.
 */
public class BlueClientThread extends BlueSocketBaseThread {

    private BluetoothDevice mServiceDevice;
    private BluetoothSocket mBlueSocket;

    public BlueClientThread(BluetoothDevice serviceDevice, Handler handler) {
        super(handler);

        mServiceDevice = serviceDevice;
        try {
            mBlueSocket = mServiceDevice.createRfcommSocketToServiceRecord(UUID_ANDROID_DEVICE);
        } catch (IOException e) {
            sendMessage(BlueSocketStatus.DISCONNECTION);
        }
    }


    @Override
    public void run() {
        super.run();
        if (!isRunning) return;
        try {
            sendMessage(BlueSocketStatus.CONNECTIONING);
            mBlueSocket.connect();
            sendMessage(BlueSocketStatus.ACCEPTED);
        } catch (Exception e) {
            try {
                mBlueSocket = (BluetoothSocket) mServiceDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(mServiceDevice, 1);
                if (mBlueSocket != null) {
                    mBlueSocket.connect();
                    sendMessage(BlueSocketStatus.ACCEPTED);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                sendMessage(BlueSocketStatus.DISCONNECTION);
            }
        }
    }

    @Override
    public BluetoothSocket getSocket() {
        return mBlueSocket;
    }
    @Override
    public void cancle() {
        super.cancle();

        try {
            if (mBlueSocket != null)
                mBlueSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mServiceDevice = null;
        mBlueSocket = null;
    }
}
