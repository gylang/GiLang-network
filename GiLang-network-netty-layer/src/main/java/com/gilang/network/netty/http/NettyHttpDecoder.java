package com.gilang.network.netty.http;

import com.gilang.common.domian.http.HttpCookie;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.network.layer.app.http.HttpAppLayerInvokerAdapter;
import com.gilang.network.netty.context.NettyHttpSessionContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.cookie.ClientCookieDecoder;
import io.netty.handler.codec.http.cookie.ClientCookieEncoder;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.CookieDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * websocket 解码器
 *
 * @author gylang
 * data 2020/11/17
 */
@Slf4j
public class NettyHttpDecoder extends MessageToMessageDecoder<FullHttpRequest> {


    private final HttpAppLayerInvokerAdapter httpAppLayerInvokerAdapter;

    public NettyHttpDecoder(HttpAppLayerInvokerAdapter httpAppLayerInvokerAdapter) {
        this.httpAppLayerInvokerAdapter = httpAppLayerInvokerAdapter;
    }


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, FullHttpRequest httpRequest, List<Object> list) throws Exception {

        Class<?> type = httpAppLayerInvokerAdapter.resolveInvokeParamType(httpRequest.method().name(), httpRequest.uri());
        HttpHeaders headers = httpRequest.headers();
        String contentType = headers.get(HttpHeaderNames.CONTENT_TYPE);
        Object object = httpAppLayerInvokerAdapter.toObject(contentType, httpRequest.content().array(), type);
        HttpDataRequest<Object> dataRequest = new HttpDataRequest<>(object);
        dataRequest.setUri(httpRequest.uri());
        dataRequest.setMethod(httpRequest.method().name());
        dataRequest.setContentType(contentType);

        for (Map.Entry<String, String> header : headers) {
            dataRequest.setHeader(header.getKey(), header.getValue());
            if (HttpHeaderNames.COOKIE.toString().equals(header.getKey())) {
                Cookie cookie = ClientCookieDecoder.LAX.decode(header.getValue());
                if (null != cookie) {
                    HttpCookie httpCookie = new HttpCookie(cookie.name());
                    httpCookie.setHttpOnly(cookie.isHttpOnly());
                    httpCookie.setDomain(cookie.domain());
                    httpCookie.setMaxAge(cookie.maxAge());
                    httpCookie.setPath(cookie.path());
                    httpCookie.setValue(cookie.value());
                    httpCookie.setSecure(cookie.isSecure());
                    httpCookie.setWrap(cookie.wrap());
                    dataRequest.setCookie(httpCookie);
                }
            }
        }
        dataRequest.setRemoteHost(channelHandlerContext.channel().remoteAddress().toString());
        list.add(dataRequest);
    }
}
