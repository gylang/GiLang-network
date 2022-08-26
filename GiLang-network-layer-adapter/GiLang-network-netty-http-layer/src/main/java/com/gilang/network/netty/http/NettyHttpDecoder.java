package com.gilang.network.netty.http;

import com.gilang.common.domian.http.HttpCookie;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpServiceWrapper;
import com.gilang.network.http.router.HttpAppLayerInvokerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.cookie.ClientCookieDecoder;
import io.netty.handler.codec.http.cookie.Cookie;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

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


        HttpHeaders headers = httpRequest.headers();
        String contentType = headers.get(HttpHeaderNames.CONTENT_TYPE);
        HttpDataRequest<Object> dataRequest = new HttpDataRequest<>();
        dataRequest.setUri(httpRequest.uri());
        dataRequest.setHost(httpRequest.headers().get(HttpHeaderNames.HOST));
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
        HttpServiceWrapper httpServiceWrapper = httpAppLayerInvokerAdapter.searchServiceWrapper(dataRequest);
        byte[] data = new byte[httpRequest.content().writerIndex()];
        dataRequest.setServiceWrapper(httpServiceWrapper);
        httpRequest.content().readBytes(data);
        Object object = httpAppLayerInvokerAdapter.toObject(contentType, data, httpServiceWrapper.getType(), dataRequest);
        dataRequest.setPayload(object);
        list.add(dataRequest);
    }
}
