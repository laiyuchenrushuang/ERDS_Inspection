package com.seatrend.http_sdk.inter;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by ly on 2020/6/30 16:28
 */
public interface IRoutingService {

    @Streaming
    @GET
    Call<ResponseBody> download();
}
