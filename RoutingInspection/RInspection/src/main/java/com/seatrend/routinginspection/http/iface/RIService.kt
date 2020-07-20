package com.seatrend.routinginspection.http.iface

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by ly on 2020/6/29 15:15
 */
interface RIService {
    @FormUrlEncoded
    @POST
    fun postServer(@Url url :String, @FieldMap map: Map<String, String>): Call<ResponseBody>
}