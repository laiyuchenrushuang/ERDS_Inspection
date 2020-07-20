package com.seatrend.inspectassistant;

import com.seatrend.inspectassistant.entity.LshEntity;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * Created by ly on 2020/7/17 18:43
 */
public interface QuestService {
    @GET()
    Call<LshEntity> getServer(@Url String url, @QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST()
    Call<ResponseBody> postServer( @Url String url, @Body RequestBody requestBody) ;

}
