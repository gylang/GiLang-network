package com.gilang.network.netty.socket;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.layer.app.socket.SocketSocketAppLayerInvokerAdapter;
import com.gilang.network.layer.show.SocketProtocolUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * websocket 协议拼装
 *
 * @author gylang
 * data 2020/11/17
 */
public class NettySocketMessageEncoder extends MessageToMessageEncoder<SocketDataPackage<?>> {

    private final SocketSocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter;

    public NettySocketMessageEncoder(SocketSocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter) {
        this.socketAppLayerInvokerAdapter = socketAppLayerInvokerAdapter;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, SocketDataPackage<?> socketDataPackage, List<Object> list) throws Exception {
        // 判断协议类型
        byte[] bytes = socketAppLayerInvokerAdapter.toByte(socketDataPackage.getTranslatorType(), socketDataPackage.getPayload());
        byte[] pageBytes = SocketProtocolUtil.writeSocketDataPackage(socketDataPackage, bytes);
        list.add(Unpooled.copiedBuffer(pageBytes));
    }
}
