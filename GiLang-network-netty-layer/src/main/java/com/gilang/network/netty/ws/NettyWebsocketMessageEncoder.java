package com.gilang.network.netty.ws;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.layer.app.socket.SocketAppLayerInvokerAdapter;
import com.gilang.network.layer.show.ProtocolUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * websocket 协议拼装
 *
 * @author gylang
 * data 2020/11/17
 */
public class NettyWebsocketMessageEncoder extends MessageToMessageEncoder<SocketDataPackage<?>> {

    private final SocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter;

    public NettyWebsocketMessageEncoder(SocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter) {
        this.socketAppLayerInvokerAdapter = socketAppLayerInvokerAdapter;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, SocketDataPackage<?> dataPackage, List<Object> list) throws Exception {
        // 判断协议类型
        byte[] bytes = socketAppLayerInvokerAdapter.toByte(dataPackage.getTranslatorType(), dataPackage.getPayload());
        String pack = dataPackage.getTranslatorType() + "," + dataPackage.getInLabel() + "," + dataPackage.getAck() + "," + dataPackage.getQos() + "," + dataPackage.getCmd() + "," + dataPackage.getMsgId() + "," + new String(bytes, StandardCharsets.UTF_8);
        list.add(new TextWebSocketFrame(pack));
    }
}
