package com.seatrend.inspectassistant;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by seatrend on 2018/8/21.
 */

public class StringUtils {

    /**
     * 时间戳转换为字符串类型
     *
     * @return
     */
    public static String longToStringData(long date) {
        if (0 == date) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA); // "yyyy-MM-dd HH:mm:ss"
            return sdf.format(new Date(date));
        } catch (Exception e) {
            return null;
        }
    }

    public static String longToStringDataNoHour(Object date) {
        if(date instanceof Long){
            if (0 == (Long)date) {
                return null;
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA); // "yyyy-MM-dd HH:mm:ss"
                return sdf.format(new Date((Long) date));
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static long dateToStamp(String s) {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date date = simpleDateFormat.parse(s);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static long date2Stamp(String s) {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Date date = simpleDateFormat.parse(s);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 处理空字符串
     */
    public static String isNull(Object obj) {
        String content = "";

        try {
            if (obj != null && !obj.toString().equals("") && !obj.toString().equals("null"))
                content = obj.toString();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return content;
    }

    /**
     * 处理空字符串
     */
    public static String isNulls(String obj) {
        String content = "--";
        if (obj != null && !obj.equals("") && !obj.equals("null"))
            content = obj;
        return content;
    }

    public static String getProcessingResultsByCode(int code) {
        switch (code) {
            case 0:
                return "未处理";
            case 1:
                return "已处理";
            default:
                return "未知状态";
        }

    }

    /**
     * 带有*号的字符
     *
     * @param s      处理字符串
     * @param start  开始的下标
     * @param number *号的个数
     * @return
     */
    public static String StringShowStar(String s, int start, int number) {
        try {
            char[] chars = s.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < chars.length; i++) {
                if (i >= start && i < start + number) {
                    builder.append("*");
                } else {
                    builder.append(chars[i]);
                }
            }
            return builder.toString();
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public static String getKskmByNumber(String number) {
        switch (number) {
            case "1":
                return "科目一";
            case "2":
                return "科目二";
            case "3":
                return "科目三";
            case "4":
                return "科目四";
            default:
                return "未知";
        }
    }

    public static String getNumberBykskm(String kskm) {
        switch (kskm) {
            case "科目一":
                return "1";
            case "科目二":
                return "2";
            case "科目三":
                return "3";
            case "科目四":
                return "4";
            default:
                return "1";
        }
    }

    public static String getToDayTime() {
        String toDay = longToStringDataNoHour(System.currentTimeMillis());
        //String toDay ="2018-09-07";

        return toDay;

    }



    //是否是手机号码
    public static boolean isPhoneNumber(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    //是否是email
    public static boolean isEmailAddress(String phone) {
        String regex = "^^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        return m.matches();
    }


    /**
     * 判断字符串是否包含重复字符
     *
     * @return true 有重复的  false  没得
     * @paramstr
     */
    public static boolean containRepeatChar(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        char[] elements = str.toCharArray();
        for (char e : elements) {
            if (str.indexOf(e) != str.lastIndexOf(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 给字符串中间加“字符”  例如“ABCDEFG” ->“A,B,C,D,E,F,G”
     *
     * @param originalStr 原本字符串
     * @param insertStr   插入啥
     * @param dis         插入的间隔
     * @return 返回新字符串
     */

    public static String insertCharToString(String originalStr, String insertStr, int dis) {

        if (originalStr.equals("")) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        char[] array = originalStr.toCharArray();

        for (int i = 0; i < array.length; i += dis) {

            if (i < array.length - dis) {
                sb.append(originalStr, i, i + dis).append(insertStr);
                continue;
            }
            sb.append(originalStr, i, array.length);
        }
        return sb.toString();
    }

    public  static String insertXingLxdh(String originalStr){

        if(originalStr == null){
            return "";
        }
        if(originalStr.length() == 11){
            return originalStr.substring(0,3)+"****"+originalStr.substring(7,11);
        }

        return "";
    }

}
