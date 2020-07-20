package com.seatrend.routinginspection.utils;

/**
 * Created by ly on 2020/7/1 15:25
 */
public class JHStateUtils {
    public static String getJhState(String state) {

        if ("0".equals(state)) {

            return "待执行";
        }
        if ("1".equals(state)) {
            return "执行中";
        }

        return "执行完成";
    }
}
