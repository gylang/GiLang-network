package com.gilang.network.netty.http;

import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.network.layer.app.http.HttpAppLayerInvokerAdapter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.*;

import java.util.List;
import java.util.Map;

/**
 * websocket 协议拼装
 *
 * @author gylang
 * data 2020/11/17
 */
public class NettyHttpEncoder extends MessageToMessageEncoder<HttpDataResponse> {

    private final HttpAppLayerInvokerAdapter httpAppLayerInvokerAdapter;

    public NettyHttpEncoder(HttpAppLayerInvokerAdapter httpAppLayerInvokerAdapter) {
        this.httpAppLayerInvokerAdapter = httpAppLayerInvokerAdapter;
    }


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, HttpDataResponse response, List<Object> list) throws Exception {
        byte[] bytes = httpAppLayerInvokerAdapter.toByte(response);
        DefaultFullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.valueOf(response.getStatus()), Unpooled.copiedBuffer(bytes));
        HttpHeaders headers = fullHttpResponse.headers();
        headers.add(HttpHeaderNames.CONTENT_LENGTH, bytes.length);
        for (Map.Entry<String, List<String>> entry : response.getHeader().entrySet()) {
            for (String value : entry.getValue()) {
                headers.add(entry.getKey(), value);
            }
        }
        list.add(fullHttpResponse);
    }
}
