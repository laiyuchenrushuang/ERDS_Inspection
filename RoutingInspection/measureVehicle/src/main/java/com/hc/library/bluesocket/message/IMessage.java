package com.hc.library.bluesocket.message;

import android.os.Parcelable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 蓝牙消息
 * Created by jack.yan on 2017/10/30.
 */
public  interface IMessage<T> extends Parcelable{
    byte TYPE_String = 1;
    byte TYPE_BYTE = 2;
    byte HEADER = 'H';
    int HEADER_LEN=10;


    /**
     * 当前消息的类型，是字符串，还是byte
     * @return
     */
    byte getType();

    void setType(byte type);

    /**
     * 消息体的内容 长度
     * @return
     */
    long getLength();

    /**
     * 设置长度
     * @param length
     */
    void setLength(long length);
    /**
     * 获取消息内容
     * @return
     */
    T getContent();



    /**
     * 设置Content 内容
     * @param content
     */
    void setContent(T content);

    /**
     * 解析content 内容
     */
    void parseContent(InputStream inputStream) throws IOException;

    /**
     * 将消息写入流
     * @param outputStream
     */
    void writeContent(OutputStream outputStream) throws IOException;

    /**
     *  创建消息Header
     *
     * header 协议，1.前1个字节为魔数，代表Header, 固定 H.getByte()
     *             2.内容类型，1个字节，
     *             3.长度, 8个字节，
     *             4.剩余，扩展消息
     *             5.结尾 \r\n
     *
     * @return
     */
    byte[] getHeader();

}
