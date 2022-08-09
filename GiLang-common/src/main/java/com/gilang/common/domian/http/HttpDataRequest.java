package com.gilang.common.domian.http;

import cn.hutool.core.collection.CollUtil;
import com.gilang.common.domian.DataPackage;
import com.gilang.common.domian.http.HttpCookie;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gylang
 * data 2022/6/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HttpDataRequest<T> extends DataPackage<T> {


    /** url query参数 */
    private Map<String, String> query;

    /** 请求方法 */
    private String method;

    /** 请求头 */
    private Map<String, List<String>> header = new HashMap<>();

    /** cookies */
    private Map<String, HttpCookie> cookies = new HashMap<>();

    /** 远程服务ip */
    private String remoteHost;

    /** 路径参数 */
    private Map<String, String> pathVariables = new HashMap<>();
    /**
     * 设置请求内容类型
     *
     * @param contentType 请求内容类型
     */
    public void setContentType(String contentType) {

        header.put("content-type", Collections.singletonList(contentType));
    }

    /**
     * 请求内容类型
     *
     * @return 请求内容类型
     */
    public String contentType() {

        return CollUtil.getFirst(header.get("content-type"));
    }
}
