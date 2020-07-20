package com.hc.library.bluesocket.utils;


import android.util.Log;

import com.hc.library.bluesocket.message.IMessage;
import com.hc.library.bluesocket.message.ImageMessage;
import com.hc.library.bluesocket.message.StringMessage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jack.yan on 2017/10/30.
 */
public class TypeUtils {

    public static String getDateJPG(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss_SSS");
        return formatter.format(currentTime)+".jpg";
    }

    //byte 数组与 long 的相互转换
    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }


    /**
     * 从流里面读取
     *
     * @return
     * @throws IOException
     */
    public static IMessage readHeader(InputStream inputStream) throws IOException {
        boolean flag=true;
        int dataLen=inputStream.available();
        if(dataLen<IMessage.HEADER_LEN){
            return null;
        }
        byte[] headerData=new byte[IMessage.HEADER_LEN-1];
        byte[] header=new byte[IMessage.HEADER_LEN];
        while (flag){
            Integer temp=inputStream.read();
            if(temp==-1){//the end of the stream is reached
                flag=false;
            }
            if(temp.byteValue()==IMessage.HEADER){//读到魔数了
                inputStream.read(headerData);
                header[0]=IMessage.HEADER;
                System.arraycopy(headerData,0,header,1,9);
                return parseHeader(header);
            }
        }
        return null;
    }


    public static IMessage parseHeader(byte[] header) {
        IMessage message;
        byte tempType = header[1];

        if (tempType == IMessage.TYPE_String) {//数据流
            message = new StringMessage();
        } else {//文件流
            message = new ImageMessage();
        }
        message.setType(header[1]);
        byte[] length = new byte[8];
        System.arraycopy(header, 2, length, 0, 8);
        long mLength = TypeUtils.bytesToLong(length);
        message.setLength(mLength);
        return message;
    }
}
