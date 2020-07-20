package com.tmri.size.app.entity;

/**
 * Created by seatrend on 2018/8/21.
 */

public class BaseEntity {
    public boolean status;
    public int code;
    public String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
