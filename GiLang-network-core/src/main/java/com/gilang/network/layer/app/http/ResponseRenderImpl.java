package com.gilang.network.layer.app.http;

import cn.hutool.core.util.StrUtil;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;

/**
 * @author gylang
 * data 2022/8/11
 */
public class ResponseRenderImpl implements ResponseRender {
    @Override
    public void render(HttpDataRequest<?> httpDataRequest, HttpDataResponse httpDataResponse) {
        if (StrUtil.isEmpty(httpDataResponse.contentType())) {
            httpDataResponse.setContentType("application/json");
        }
    }
}
