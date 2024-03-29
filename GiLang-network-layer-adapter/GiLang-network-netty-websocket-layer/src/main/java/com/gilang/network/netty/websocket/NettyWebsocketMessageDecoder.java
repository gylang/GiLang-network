package com.gilang.network.netty.websocket;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.gilang.common.domian.socket.SocketDataPackage;
import com.gilang.network.socket.router.SocketSocketAppLayerInvokerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket 解码器
 *
 * @author gylang
 * data 2020/11/17
 */
@Slf4j
public class NettyWebsocketMessageDecoder extends SimpleChannelInboundHandler<Object> {

    private static final String URI = "ws://localhost:%d";

    private static final ConcurrentHashMap<String, WebSocketServerHandshaker> handShakerMap = new ConcurrentHashMap<>();
    private final SocketSocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter;

    public NettyWebsocketMessageDecoder(SocketSocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter) {
        this.socketAppLayerInvokerAdapter = socketAppLayerInvokerAdapter;
    }


    /**
     * 接收到websocket协议
     *
     * @param ctx 上下文
     * @param msg 消息体
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // http请求
        if (msg instanceof FullHttpRequest) {
            handleHandshakeRequest(ctx, (FullHttpRequest) msg);
        }
        // websocket消息
        if (msg instanceof WebSocketFrame) {
            handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }


    }

    /**
     * websocket握手
     *
     * @param ctx 上下文
     * @param req 请求对象
     */
    private void handleHandshakeRequest(ChannelHandlerContext ctx, FullHttpRequest req) {

        int port = ((InetSocketAddress) ctx.channel().localAddress()).getPort();

        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(String.format(URI, port), null, false);

        WebSocketServerHandshaker handShaker = wsFactory.newHandshaker(req);

        handShakerMap.put(ctx.channel().id().asLongText(), handShaker);
        if (log.isDebugEnabled()) {
            log.debug("[申请握手] : {}", ctx.channel().id());
        }
        handShaker.handshake(ctx.channel(), req);
    }

    /**
     * 处理websocket消息
     *
     * @param ctx   上下文
     * @param frame websocket消息体
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {

        if (frame instanceof CloseWebSocketFrame) {
            // 关闭websocket
            handlerCloseWebSocketFrame(ctx, (CloseWebSocketFrame) frame);
            return;
        }

        if (frame instanceof PingWebSocketFrame) {
            // ping
            handlerPingWebSocketFrame(ctx, (PingWebSocketFrame) frame);
        } else if (frame instanceof PongWebSocketFrame) {
            // pong
            handlerPongWebSocketFrame(ctx, (PongWebSocketFrame) frame);
        } else if (frame instanceof TextWebSocketFrame) {
            //text消息
            handlerTextWebSocketFrame(ctx, (TextWebSocketFrame) frame);
        } else if (frame instanceof BinaryWebSocketFrame) {
            // byte消息格式
            handlerBinaryWebSocketFrame(ctx, (BinaryWebSocketFrame) frame);
        }

    }

    /**
     * 处理ping指令
     *
     * @param ctx   上下文
     * @param frame 消息体
     */
    private void handlerPongWebSocketFrame(ChannelHandlerContext ctx, PongWebSocketFrame frame) {
        ctx.channel().write(new PingWebSocketFrame(frame.content().retain()));
    }

    /**
     * 处理text消息体
     *
     * @param ctx   上下文
     * @param frame 消息体
     */
    private void handlerTextWebSocketFrame(ChannelHandlerContext ctx, TextWebSocketFrame frame) {

        String content = frame.text();
        List<String> info = StrUtil.split(content, ',', 7);
        byte translator = Byte.parseByte(CollUtil.get(info, 0));
        byte inLabel = Byte.parseByte(CollUtil.get(info, 1));
        byte ack = Byte.parseByte(CollUtil.get(info, 2));
        byte qos = Byte.parseByte(CollUtil.get(info, 3));
        byte cmd = Byte.parseByte(CollUtil.get(info, 4));
        long messageId = Long.parseLong(CollUtil.get(info, 5));
        String payload = CollUtil.get(info, 6);
        Type type = socketAppLayerInvokerAdapter.resolveInvokeParamType(cmd);
        Object object = socketAppLayerInvokerAdapter.toObject(translator, StrUtil.bytes(payload, StandardCharsets.UTF_8), type);
        SocketDataPackage<Object> dataPackage = new SocketDataPackage<>(object)
                .translatorType(translator)
                .inLabel(inLabel)
                .ack(ack)
                .qos(qos)
                .msgId(messageId)
                .cmd(cmd);
        ctx.fireChannelRead(dataPackage);
    }

    /**
     * 处理socket关闭
     *
     * @param ctx   上下文
     * @param frame 消息体
     */
    private void handlerCloseWebSocketFrame(ChannelHandlerContext ctx, CloseWebSocketFrame frame) {
        WebSocketServerHandshaker handShaker = handShakerMap.get(ctx.channel().id().asLongText());
        handShaker.close(ctx.channel(), frame.retain());
        if (log.isDebugEnabled()) {
            log.debug("[申请关闭] : {}", ctx.channel().id());
        }
        handShakerMap.remove(ctx.channel().id().asLongText());
    }

    /**
     * 处理ping
     *
     * @param ctx   上下文
     * @param frame 消息体
     */
    private void handlerPingWebSocketFrame(ChannelHandlerContext ctx, PingWebSocketFrame frame) {
        ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
    }

    /**
     * 处理byte消息
     *
     * @param ctx   上下文
     * @param frame 消息体
     */
    private void handlerBinaryWebSocketFrame(ChannelHandlerContext ctx, BinaryWebSocketFrame frame) {


//        ByteBuf content = frame.content();
//        byte bit1 = content.readByte();
//        byte translator = ProtocolUtil.readTranslator(bit1);
//        byte inLabel = ProtocolUtil.readInLabel(bit1);
//        byte bit2 = content.readByte();
//        byte ack = ProtocolUtil.readAck(bit2);
//        byte qos = ProtocolUtil.readQos(bit2);
//        byte cmd = content.readByte();
//        long messageId = content.readLong();
//        short contentLength = content.readShort();
//        ByteBuf payload = content.readBytes(contentLength);
//        Type type = socketAppLayerInvokerAdapter.resolveInvokeParamType(cmd);
//        Object object = socketAppLayerInvokerAdapter.toObject(translator, payload.array(), type);
//        SocketDataPackage<Object> dataPackage = new SocketDataPackage<>(object)
//                .translatorType(translator)
//                .inLabel(inLabel)
//                .ack(ack)
//                .qos(qos)
//                .msgId(messageId)
//                .cmd(cmd);
//        ctx.fireChannelRead(dataPackage);
    }


}
