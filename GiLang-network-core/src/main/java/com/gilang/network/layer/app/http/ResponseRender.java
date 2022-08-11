package com.gilang.network.layer.app.http;

import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;

/**
 * @author gylang
 * data 2022/8/11
 */
public interface ResponseRender {

    /**
     * 写入基本响应信息
     *
     * @param httpDataRequest  请求
     * @param httpDataResponse 响应
     */
    void render(HttpDataRequest<?> httpDataRequest, HttpDataResponse httpDataResponse);
}
