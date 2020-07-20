package com.hc.library.bluesocket.message;

import android.os.Parcel;
import android.util.Log;

import com.hc.library.bluesocket.utils.TypeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by jack.yan on 2017/10/30.
 */

public class StringMessage implements IMessage<String> {
    private String mContent;
    private byte[] contentByte;
    private long mLength;
    private byte mType = TYPE_String;

    @Override
    public byte getType() {
        return mType;
    }

    @Override
    public void setType(byte type) {
        mType = type;
    }

    @Override
    public long getLength() {
        return mLength;
    }

    @Override
    public void setLength(long length) {
        mLength = length;
    }

    @Override
    public String getContent() {
        return mContent;
    }

    @Override
    public void setContent(String content) {
        mContent=content;
        contentByte = content.getBytes();
        mLength = contentByte.length;
    }

    @Override
    public void parseContent(InputStream inputStream) throws IOException {

        contentByte = new byte[(int) mLength];
        inputStream.read(contentByte,0, (int) mLength);
        mContent = new String(contentByte);
    }



    @Override
    public void writeContent(OutputStream outputStream) throws IOException {
        byte[] data=new byte[IMessage.HEADER_LEN+contentByte.length];
        System.arraycopy(getHeader(),0,data,0,IMessage.HEADER_LEN);
        System.arraycopy(contentByte,0,data,IMessage.HEADER_LEN,contentByte.length);
        Log.i("SocketUtils","getHeader() s--   "+getHeader().toString());
        Log.i("SocketUtils","getHeader() --   "+new String(getHeader()));
        Log.i("SocketUtils","消息二进制数据 --   "+new String(data));
        outputStream.write(data);
//        outputStream.write(getHeader());
//        outputStream.write(contentByte);
    }

    @Override
    public byte[] getHeader() {
        byte[] length = TypeUtils.longToBytes(getLength());
        byte[] header = new byte[IMessage.HEADER_LEN];
        header[0] = HEADER;                                         //魔数
        header[1] = getType();                                     //类型
        System.arraycopy(length, 0, header, 2, 8);     //长度
        return header;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mContent);
        dest.writeByteArray(this.contentByte);
        dest.writeLong(this.mLength);
        dest.writeByte(this.mType);
    }

    public StringMessage() {
    }

    protected StringMessage(Parcel in) {
        this.mContent = in.readString();
        this.contentByte = in.createByteArray();
        this.mLength = in.readLong();
        this.mType = in.readByte();
    }

    public static final Creator<StringMessage> CREATOR = new Creator<StringMessage>() {
        @Override
        public StringMessage createFromParcel(Parcel source) {
            return new StringMessage(source);
        }

        @Override
        public StringMessage[] newArray(int size) {
            return new StringMessage[size];
        }
    };

    @Override
    public String toString() {
        return "StringMessage{" +
                "mContent='" + mContent + '\'' +
                ", contentByte=" + Arrays.toString(contentByte) +
                ", mLength=" + mLength +
                ", mType=" + mType +
                '}';
    }
}
