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

    /** 是否已经执行完成 */
    private boolean done;


    /**
     * 设置请求内容类型
     *
     * @param contentType 请求内容类型
     */
    public void setContentType(String contentType) {

        header.put("content-type", Collections.singletonList(contentType));
    }

    public void addHeader(String name, String value) {
        this.header.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
    }

    public void setHeader(String name, List<String> value) {
        this.header.put(name, value);
    }

    public List<String> getHeaderList(String name) {
        return this.header.computeIfAbsent(name, k -> new ArrayList<>());
    }

    /**
     * 请求内容类型
     *
     * @return 请求内容类型
     */
    public String contentType() {

        return CollUtil.getFirst(header.get("content-type"));
    }

    public void write(byte[] bytes) {
        this.done = true;
        setPayload(bytes);

    }

    public void writeSuccess(byte[] bytes) {
        write(200, bytes);
    }

    public void write(int status, byte[] bytes) {
        this.status = 200;
        setPayload(bytes);
        this.done = true;
    }

    public void setSuccessBody(Object obj) {
        this.status = 200;
        setPayload(obj);
    }

}
