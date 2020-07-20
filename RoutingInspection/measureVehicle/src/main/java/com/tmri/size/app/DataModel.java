package com.tmri.size.app;

/**
 * Created by Jack.Yan on 2017/10/31.
 */

public class DataModel {
    public static final String START="1";
    public static final String STOP="2";
    public static final String RECV="3";

    /**
     * 开始测量
     * @param cylsh 查验流水号
     * @param clsbdh 车辆识别代号（车架号）
     * @param cph 车牌号
     * @param hpzl 号牌种类
     * @param cllx 车辆类型
     * @param jylb 检验类别
     * @return
     */
    public static String dataStart(final String cylsh,final String clsbdh,final String cph,final String hpzl,final String cllx,final String jylb,
                                   final String sfwk,
                                   final String sfzj,
                                   final String sflbgd,
                                   final String zbzl){

        /**
         * 1|1.流水号|2.车架号|3.车牌号|4.号牌种类|5.车辆类型|6.检验类别|7.是否外廓|8.是否轴距|9.是否栏板高度|10.是否整备质量
         * |11.是否牵引头|12.牵引车流水号|13.牵引车车架号|14.牵引车车牌号|15.牵引车号牌种类|16.牵引车车辆类型|17.牵引车检验类别
         */



        return new StringBuilder()
                .append(START).append("|")
                .append(cylsh).append("|")
                .append(clsbdh).append("|")
                .append(cph).append("|")
                .append(hpzl).append("|")
                .append(cllx).append("|")
                .append(jylb).append("|")
                .append(sfwk).append("|")
                .append(sfzj).append("|")
                .append(sflbgd).append("|")
                .append(zbzl).append("|")
                .toString();
    }

    /**
     * 开始测量 带挂车（两车）
     * @param cylsh 查验流水号
     * @param clsbdh 车辆识别代号（车架号）
     * @param cph 车牌号
     * @param hpzl 号牌种类
     * @param cllx 车辆类型
     * @param jylb 检验类别
     * @return
     */
    public static String dataStart(final String cylsh,final String clsbdh,final String cph,final String hpzl,final String cllx,final String jylb,
                                   final String sfwk,
                                   final String sfzj,
                                   final String sflbgd,
                                   final String zbzl,
                                   final String sfqyt,
                                    final String cylsh_1,final String clsbdh_1,final String cph_1,final String hpzl_1,final String cllx_1,
                                   final String jylb_1){

        /**
         * 1|1.流水号|2.车架号|3.车牌号|4.号牌种类|5.车辆类型|6.检验类别|7.是否外廓|8.是否轴距|9.是否栏板高度|10.是否整备质量
         * |11.是否牵引头|12.牵引车流水号|13.牵引车车架号|14.牵引车车牌号|15.牵引车号牌种类|16.牵引车车辆类型|17.牵引车检验类别
         */
        return new StringBuilder()
                .append(START).append("|")
                .append(cylsh).append("|")
                .append(clsbdh).append("|")
                .append(cph).append("|")
                .append(hpzl).append("|")
                .append(cllx).append("|")
                .append(jylb).append("|")
                .append(sfwk).append("|")
                .append(sfzj).append("|")
                .append(sflbgd).append("|")
                .append(zbzl).append("|")
                .append(sfqyt).append("|")//带挂车
                .append(cylsh_1).append("|")
                .append(clsbdh_1).append("|")
                .append(cph_1).append("|")
                .append(hpzl_1).append("|")
                .append(cllx_1).append("|")
                .append(jylb_1)
                .toString();
    }

    public static String dataStop() {
        return new StringBuilder()
                .append(STOP).toString();
    }
    public static String dataReceived(String name){
        return new StringBuilder()
                .append(RECV).append("|")
                .append(name)
                .toString();
    }
}
