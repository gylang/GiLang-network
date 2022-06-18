package com.gilang.network.netty.socket;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.layer.app.socket.SocketAppLayerInvokerAdapter;
import com.gilang.network.layer.show.ProtocolUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket 解码器
 *
 * @author gylang
 * data 2020/11/17
 */
@Slf4j
public class SocketMessageDecoder extends ByteToMessageDecoder {


    private final SocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter;

    public SocketMessageDecoder(SocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter) {
        this.socketAppLayerInvokerAdapter = socketAppLayerInvokerAdapter;
    }




    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte bit1 = byteBuf.readByte();
        byte translator = ProtocolUtil.readTranslator(bit1);
        byte inLabel = ProtocolUtil.readInLabel(bit1);
        byte cmd = byteBuf.readByte();
        byte bit3 = byteBuf.readByte();
        byte ack = ProtocolUtil.readAck(bit3);
        byte qos = ProtocolUtil.readQos(bit3);
        long messageId = byteBuf.readLong();
        short contentLength = byteBuf.readShort();
        ByteBuf bytes = byteBuf.readBytes(contentLength);
        Type type = socketAppLayerInvokerAdapter.resolveInvokeParamType(cmd);
        Object object = socketAppLayerInvokerAdapter.toObject(translator, bytes.array(), type);
        SocketDataPackage<Object> dataPackage = new SocketDataPackage<>(object)
                .translatorType(translator)
                .inLabel(inLabel)
                .ack(ack)
                .qos(qos)
                .msgId(messageId)
                .cmd(cmd);
        list.add(dataPackage);
    }
}
