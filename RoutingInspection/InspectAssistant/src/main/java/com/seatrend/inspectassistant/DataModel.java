package com.seatrend.inspectassistant;

import android.text.method.ReplacementTransformationMethod;

import java.util.ArrayList;

/**
 * Created by ly on 2020/6/30 15:04
 */
public class DataModel {
    /**
     * 获取省份的简称
     * @return
     */
    public static ArrayList<String> getProvince(){
        ArrayList list =  new ArrayList();
        String[] jc={"京","津","冀","晋","蒙","辽","吉","黑","沪","苏","浙","皖","闽","赣","鲁",
                "豫","鄂","湘","粤","桂","琼","渝","川","黔","滇","藏","陕","甘","青","宁","新","台","港","澳"};
        for(String db : jc){
            list.add(db);
        }
        return list;
    }

    /**
     * 获取车牌A-Z
     * @return
     */
    public static ArrayList<String> getA_Z(){
        ArrayList list =  new ArrayList();
        String[] jc={"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        for(String db : jc){
            list.add(db);
        }
        return list;
    }

    //原本输入的小写字母
    public static class TransInformation extends ReplacementTransformationMethod {
        /**
         * 原本输入的小写字母
         */
        @Override
        protected char[] getOriginal() {
            return new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        }

        /**
         * 替代为大写字母
         */
        @Override
        protected char[] getReplacement() {
            return new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        }
    }
}
