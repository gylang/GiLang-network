package com.gilang.common.domian.http;

import cn.hutool.core.collection.CollUtil;
import com.gilang.common.domian.DataPackage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

/**
 * @author gylang
 * data 2022/6/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HttpDataResponse extends DataPackage<Object> {

    /** 响应状态码 */
    private Integer status;



    /** 请求头 */
    private Map<String, List<String>> header = new HashMap<>();

    /** cookies */
    private Map<String, HttpCookie> cookies = new HashMap<>();


    /**
     * 设置请求内容类型
     *
     * @param contentType 请求内容类型
     */
    public void setContentType(String contentType) {

        header.put("content-type", Collections.singletonList(contentType));
    }

    public void setHeader(String header, String value) {
       this.header.computeIfAbsent(header, k-> new ArrayList<>()).add(value);
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
