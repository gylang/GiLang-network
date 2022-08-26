package com.gilang.common.domian.http;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.gilang.common.domian.DataPackage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author gylang
 * data 2022/6/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HttpDataRequest<T> extends DataPackage<T> {

    /**
     * 请求uri
     */
    private String uri;
    /**
     * 转码后的uri
     */
    private String decodedUri;

    /**
     * url query参数
     */
    private Map<String, String> query;

    /**
     * 请求方法
     */
    private String method;

    /** 请求服务的主机/域名 */
    private String serverName;

    /** 请求服务的协议 */
    private String serverProtocol;

    /** 请求服务的端口 */
    private int serverPort;
    /** host头 */
    private String host;

    /**
     * 请求头
     */
    private Map<String, List<String>> header = new HashMap<>();

    /**
     * cookies
     */
    private Map<String, HttpCookie> cookies = new HashMap<>();

    /**
     * 远程服务ip
     */
    private String remoteHost;

    private Charset charset = StandardCharsets.UTF_8;

    /**
     * 路径参数
     */
    private Map<String, String> pathVariables = new HashMap<>();

    private HttpServiceWrapper serviceWrapper;
    /**
     * 设置请求内容类型
     *
     * @param contentType 请求内容类型
     */
    public void setContentType(String contentType) {

        header.put("content-type", Collections.singletonList(contentType));
    }

    public void setHeader(String name, String value) {
        this.header.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
    }

    public String getFirstHeader(String name) {
        return CollUtil.getFirst(this.header.get(name));
    }

    public List<String> getHeaderList(String name) {
        List<String> headers = this.header.get(name);
        return null == headers ? Collections.emptyList() : headers;
    }

    public void setCookie(HttpCookie value) {
        this.cookies.put(value.getName(), value);
    }

    public HttpDataRequest() {
    }

    public HttpDataRequest(T t) {
        this.setPayload(t);
    }


    /**
     * 请求内容类型
     *
     * @return 请求内容类型
     */
    public String contentType() {

        return CollUtil.getFirst(header.get("content-type"));
    }

    public String getPathVariable(String key) {
        return pathVariables.get(key);
    }

    public String getPathDecodeVariable(String key, String charset) {
        return URLUtil.decode(getPathVariable(key), charset);
    }

    public String getPathDecodeVariable(String key) {
        return URLUtil.decode(getPathVariable(key), StandardCharsets.UTF_8);
    }

    public void setUri(String uri) {
        this.uri = uri;
        this.decodedUri = URLDecoder.decode(uri, charset);
    }

    public void setHost(String host) {
        this.host = host;
        try {
            if (StrUtil.isNotEmpty(host)) {
                if (!host.contains("://")) {
                    host = "http://" + host;
                }
                URL url = new URL(host);
                this.serverName = url.getHost();
                this.serverProtocol = url.getProtocol();
                this.serverPort = url.getPort();
            }

        } catch (MalformedURLException ignored) {

        }
    }
}
