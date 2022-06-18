package com.gilang.network.netty.socket;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.layer.app.socket.SocketAppLayerInvokerAdapter;
import com.gilang.network.layer.show.ProtocolUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

/**
 * websocket 协议拼装
 *
 * @author gylang
 * data 2020/11/17
 */
public class SocketMessageEncoder extends MessageToMessageEncoder<SocketDataPackage<?>> {

    private final SocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter;

    public SocketMessageEncoder(SocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter) {
        this.socketAppLayerInvokerAdapter = socketAppLayerInvokerAdapter;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, SocketDataPackage<?> socketDataPackage, List<Object> list) throws Exception {
        // 判断协议类型
        byte[] bytes = socketAppLayerInvokerAdapter.toByte(socketDataPackage.getTranslatorType(), socketDataPackage.getPayload());
        byte[] pageBytes = ProtocolUtil.writeSocketDataPackage(socketDataPackage, bytes);
        list.add(Unpooled.copiedBuffer(pageBytes));
    }
}
