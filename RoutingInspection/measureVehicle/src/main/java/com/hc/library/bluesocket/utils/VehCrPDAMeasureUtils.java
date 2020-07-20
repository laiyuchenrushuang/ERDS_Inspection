package com.hc.library.bluesocket.utils;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by admin on 2017/11/24.
 */

public class VehCrPDAMeasureUtils {

    /***
     * 验证测量结果
     *
     * @param cllx
     *            车辆类型
     * @param ywlxs
     *            业务类型
     * @param ycwkc
     *            原车外廓长
     * @param ycwkk
     *            原车外廓宽
     * @param ycwkg
     *            原车外廓高
     * @param yczzl
     *            原车总质量
     * @param clcwkc
     *            测量车外廓长
     * @param clcwkk
     *            测量车外廓宽
     * @param clcwkg
     *            测量车外廓高
     * @param clzzl
     *            测量总质量
     * @return
     */
    public String checkVehMeasure(String cllx, String ywlxs, int ycwkc, int ycwkk, int ycwkg, int clcwkc, int clcwkk, int clcwkg,int yczzl, int clzzl) {
        /**
         * 3|1|外廓判定|整备质量判定|轴距判定|栏板高度判定|牵引车外廓判定|牵引车整备质量判定|牵引车轴距判定
         */


        StringBuffer measureResult = new StringBuffer();
        // 车辆类型
        if (cllx == null || cllx.length() ==0 || ywlxs== null || ywlxs.length() ==0)
            return measureResult.toString();
        //外廓尺寸类型
        String strWkccLx = "";
        //整备质量类型
        String strZbzllx = "";
        String cllx1 = cllx.substring(0, 1);
        String cllx2 = cllx.substring(0, 2);

        if (ywlxs.indexOf("00") > -1) {
            // 注册登记
            // 外廓尺寸
            if ("M,N".indexOf(cllx1) > -1) {
                // 注册登记（摩托车），车外廓{0}录入与登记信息比对值超过±3%或±50mm，外廓尺寸不能判定为合格";
                strWkccLx = "001";
            } else {
                // "注册登记（其他），车外廓{0}录入与登记信息比对值超过±1%或±50mm，外廓尺寸不能判定为合格";
                strWkccLx = "002";
            }
            // 整备质量
            if ("H1,H2,Z1,Z2,Z5".indexOf(cllx2) > -1 || "B.G".indexOf(cllx1) > -1) {
                strZbzllx = "001";
            } else if ("H3,H4,Z3,Z4,Z7".indexOf(cllx2) > -1) {
                strZbzllx = "002";
            } else if ("H5".indexOf(cllx2) > -1) {
                strZbzllx = "003";
            } else if ("M".indexOf(cllx1) > -1) {
                strZbzllx = "004";
            }
        } else {
            // 在用车
            // 车外廓{0}录入与登记信息比对值超过±2%或±100mm，外廓尺寸不能判定为合格"
            // 整备质量不判定
            strWkccLx = "011";
        }
        if(strWkccLx == null || strWkccLx.length() ==0)
            return measureResult.toString();
            //车外廓尺寸判定说明
            String cwkccpdsm = "";
            CheckResult result =new CheckResult();
            result = checkVehMeasureForDetail(strWkccLx, "长", ycwkc, clcwkc);
        measureResult.append(result.getMessage());
        measureResult.append(",");
            result = checkVehMeasureForDetail(strWkccLx, "宽", ycwkk, clcwkk);
        measureResult.append(result.getMessage());
        measureResult.append(",");
             result = checkVehMeasureForDetail(strWkccLx, "高", ycwkg, clcwkg);
        measureResult.append(result.getMessage()); //ycwzbzl

        //整备质量判定
        measureResult.append("|");
        result = checkVehMeasureZbzl(strZbzllx,yczzl,clzzl);
        measureResult.append(result.getMessage());

        //轴距判定
        measureResult.append("|");


        //栏板高度判定
        measureResult.append("|");


        //牵引车外廓判定
        measureResult.append("|");



        //牵引车整备质量判定
        measureResult.append("|");


        //牵引车轴距判定
        measureResult.append("|");



        return measureResult.toString();
    }

    // 外廓尺寸判定
    public CheckResult checkVehMeasureForDetail(String strLx, String strSm, float inDjz, float inClz) {
        CheckResult check =new CheckResult();
        double rate = 0.0300; // 误差比例
        int value = 50; // 误差值
        if (strLx.equals("002")) {
            rate = 0.0100;
        } else if (strLx.equals("011")) {
            rate = 0.0200;
            value = 100;
        }
        double rate2 = formatDouble((inDjz - inClz) / inDjz);
        float value2 = inDjz - inClz;

        StringBuffer strMessage = new StringBuffer();
        // 两个都超标时，才为不合格
        if ((Math.abs(rate2) > rate) && (Math.abs(value2) > value)) {
            check.setCode(0);
            strMessage.append(strSm);
            strMessage.append("误差值");
            strMessage.append((int) value2);
            strMessage.append("mm(>");
            strMessage.append((int) value);
            strMessage.append("mm),误差比");
            strMessage.append(Float.parseFloat((rate2 * 100)+""));
            strMessage.append("%(>");
            strMessage.append(Float.parseFloat((rate * 100)+""));
            strMessage.append("%)");

            check.setMessage(strMessage.toString());
            return check;
        }
        check.setCode(1);
        check.setMessage(strSm + "误差值" + (int) value2 + "mm(≤" + (int) value + "mm),误差比" + Float.parseFloat((rate2 * 100)+"") + "%(≤"
                + Float.parseFloat((rate * 100)+"") + "%)");

        return check;
    }

    public CheckResult checkVehMeasureZbzl(String strLx, float inDjz, float inClz) {
        CheckResult returnInfo = new CheckResult();
        double rate = 0; // 误差比例
        int value = 100; // 误差值
        if (strLx.equals("001")) {
            rate = 0.03;
            value = 500;
        } else if (strLx.equals("002")) {
            rate = 0.03;
            value = 100;
        } else if (strLx.equals("003")) {
            rate = 0.05;
            value = 100;
        }
        double rate2 = formatDouble((inDjz - inClz) / inDjz);
        float value2 = inDjz - inClz;
        String message = "";
        // 两个都超标时，才为不合格
        if ((Math.abs(rate2) > rate) && (Math.abs(value2) > value)) {
            returnInfo.setCode(0);
            // 摩托车不显示比例
            if (strLx.equals("004")) {
                message = "整备质量误差值" + (int) value2 + "kg(>" + (int) value + "kg)";
            } else {
                message = "整备质量误差值" + (int) value2 + "kg(>" + (int) value + "kg),误差比" + (int) (rate2 * 100) + "%(>" + rate * 100
                        + "%)";
            }
            returnInfo.setMessage(message);
            return returnInfo;
        }
        // 摩托车不显示比例
        if (strLx.equals("004")) {
            message = "整备质量误差值" + (int) value2 + "kg(≤" + (int) value + "kg)";
        } else {
            message = "整备质量误差值" + (int) value2 + "kg(≤" + (int) value + "kg),误差比" + rate2 * 100 + "%(≤" + rate * 100
                    + "%)";
        }
        returnInfo.setCode(1);
        returnInfo.setMessage(message);
        return returnInfo;

    }

    // 保留小数点后4位
    public static double formatDouble(double indouble) {
        try {
            BigDecimal bigDecimal = new BigDecimal(indouble).setScale(4, RoundingMode.DOWN);
            return bigDecimal.doubleValue();
        }catch (NumberFormatException e){
            return 0.0000;
        }
    }

    // String转int
    public static int formatStringToInt(String iString) {
        if (iString == null || iString.equals("") || iString.equals("null")) {
            return 0;
        }
        return Integer.parseInt(iString);
    }

    public class CheckResult{
        private int code;

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

        private String message;
    }
}
