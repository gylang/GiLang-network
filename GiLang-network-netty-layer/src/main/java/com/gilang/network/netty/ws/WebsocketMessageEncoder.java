package com.gilang.network.netty.ws;

import com.gilang.common.domian.DataPackage;
import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.layer.app.socket.SocketAppLayerInvokerAdapter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

/**
 * websocket 协议拼装
 *
 * @author gylang
 * data 2020/11/17
 */
public class WebsocketMessageEncoder extends MessageToMessageEncoder<SocketDataPackage<?>> {

    private final SocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter;

    public WebsocketMessageEncoder(SocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter) {
        this.socketAppLayerInvokerAdapter = socketAppLayerInvokerAdapter;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, SocketDataPackage<?> messageWrap, List<Object> list) throws Exception {
        byte[] bytes = socketAppLayerInvokerAdapter.toByte(messageWrap.getTranslatorType(), messageWrap.getActBody());
        // 判断协议类型
        list.add(new BinaryWebSocketFrame(Unpooled.copiedBuffer(bytes)));
    }
}
