package com.gilang.network.layer.app.http;

import cn.hutool.core.util.StrUtil;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;

/**
 * @author gylang
 * data 2022/8/11
 */
public class ResponseRenderImpl implements ResponseRender, AfterNetWorkContextInitialized {

    private String contentType;

    @Override
    public void render(HttpDataRequest<?> httpDataRequest, HttpDataResponse httpDataResponse) {

        defaultContentType(httpDataResponse);
    }

    private void defaultContentType(HttpDataResponse httpDataResponse) {
        if (StrUtil.isEmpty(httpDataResponse.contentType())) {
            httpDataResponse.setContentType(contentType);
        }
    }

    @Override
    public void post(ServerContext serverContext) {
        contentType = serverContext.getPropertiesVisitor().getBaseTypeValue("gilang.network.http.contentType", "application/json", String.class);
    }
}
