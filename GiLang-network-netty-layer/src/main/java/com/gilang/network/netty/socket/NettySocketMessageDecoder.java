package com.gilang.network.netty.socket;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.layer.app.socket.SocketSocketAppLayerInvokerAdapter;
import com.gilang.network.layer.show.SocketProtocolUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.List;

/**
 * websocket 解码器
 *
 * @author gylang
 * data 2020/11/17
 */
@Slf4j
public class NettySocketMessageDecoder extends ByteToMessageDecoder {


    private final SocketSocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter;

    public NettySocketMessageDecoder(SocketSocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter) {
        this.socketAppLayerInvokerAdapter = socketAppLayerInvokerAdapter;
    }




    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte bit1 = byteBuf.readByte();
        byte translator = SocketProtocolUtil.readTranslator(bit1);
        byte inLabel = SocketProtocolUtil.readInLabel(bit1);
        byte cmd = byteBuf.readByte();
        byte bit3 = byteBuf.readByte();
        byte ack = SocketProtocolUtil.readAck(bit3);
        byte qos = SocketProtocolUtil.readQos(bit3);
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
