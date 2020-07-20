package com.seatrend.routinginspection.model;

import android.text.TextUtils;
import com.google.gson.JsonSyntaxException;
import com.seatrend.routinginspection.MyApplication;
import com.seatrend.routinginspection.base.Constants;
import com.seatrend.routinginspection.entity.LoginEntity;
import com.seatrend.routinginspection.utils.GsonUtils;
import com.seatrend.routinginspection.utils.cache.ACache;

/**
 * Created by ly on 2020/7/1 13:17
 */
public class LoginModel {

    /**
     * 存 login信息
     *
     * @param msg
     */
    public static void setLogin(String msg) {
        ACache.get(MyApplication.Companion.getMyApplicationContext()).put(Constants.Companion.getLOGIN_ENTITY(), msg);
    }

    /**
     * 获取Login信息
     *
     * @return
     */
    public static LoginEntity getLogin() {
        String res = ACache.get(MyApplication.Companion.getMyApplicationContext()).getAsString(Constants.Companion.getLOGIN_ENTITY());
        if (res == null || TextUtils.isEmpty(res)) {
            return new LoginEntity();
        }
        LoginEntity entity = null;
        try {
            entity = GsonUtils.gson(res, LoginEntity.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return new LoginEntity();
        }
        return entity;
    }
}
