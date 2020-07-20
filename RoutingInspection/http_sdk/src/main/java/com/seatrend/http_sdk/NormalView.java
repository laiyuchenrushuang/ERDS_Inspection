package com.seatrend.http_sdk;

import com.seatrend.http_sdk.base.ConmmonResponse;

/**
 * Created by ly on 2020/6/30 17:21
 */
public interface NormalView {
    void netWorkTaskSuccess(ConmmonResponse commonResponse);
    void netWorkTaskfailed(ConmmonResponse commonResponse);
}
