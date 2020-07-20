package com.seatrend.routinginspection.entity

/**
 * Created by ly on 2020/6/29 16:01
 */
class TestEntity : BaseEntity() {

    var data: List<DataBean>? = null

    class DataBean {
        /**
         * jhbh : null
         * rwbh : RWBH2006291546115066123
         * rwmc : 1
         * rwcjr : 1
         * rwcjsj : 1593416771000
         * rwxgsj : null
         * rwms : 1
         * rwzt : 1
         * rwzxjg : null
         * id : null
         */

        var jhbh: Any? = null
        var rwbh: String? = null
        var rwmc: String? = null
        var rwcjr: String? = null
        var rwcjsj: Long = 0
        var rwxgsj: Any? = null
        var rwms: String? = null
        var rwzt: String? = null
        var rwzxjg: Any? = null
        var id: Any? = null
        override fun toString(): String {
            return "DataBean(jhbh=$jhbh, rwbh=$rwbh, rwmc=$rwmc, rwcjr=$rwcjr, rwcjsj=$rwcjsj, rwxgsj=$rwxgsj, rwms=$rwms, rwzt=$rwzt, rwzxjg=$rwzxjg, id=$id)"
        }
    }
}
