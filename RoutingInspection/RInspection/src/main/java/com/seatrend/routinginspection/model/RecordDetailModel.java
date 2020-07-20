package com.seatrend.routinginspection.model;

import com.seatrend.routinginspection.MyApplication;
import com.seatrend.routinginspection.base.Constants;
import com.seatrend.routinginspection.entity.RecordDetailEntity;
import com.seatrend.routinginspection.utils.GsonUtils;
import com.seatrend.routinginspection.utils.cache.ACache;

import java.util.ArrayList;

/**
 * Created by ly on 2020/7/9 17:06
 */
public class RecordDetailModel {
    /**
     * save detail信息
     *
     * @param msg
     */
    public static void setDetail(String msg) {
        ACache.get(MyApplication.Companion.getMyApplicationContext()).put(Constants.Companion.getRECORD_DETAIL_ENTITY(), msg);
    }

    public static RecordDetailEntity getDetail() {
        String res = ACache.get(MyApplication.Companion.getMyApplicationContext()).getAsString(Constants.Companion.getRECORD_DETAIL_ENTITY());
        if ("".equals(res)) {
            return new RecordDetailEntity();
        }
        return GsonUtils.gson(res, RecordDetailEntity.class);
    }
}
