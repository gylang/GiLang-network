package com.gilang.network.netty.websocket;

import com.gilang.network.context.ServerContext;
import com.gilang.network.socket.context.SocketSessionManager;
import com.gilang.network.websocket.WebsocketConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static io.netty.handler.timeout.IdleState.ALL_IDLE;

/**
 * 心跳监控 发送消息 用户业务对心跳检测和业务处理
 *
 * @author gylang
 * data 2020/11/3
 * @version v0.0.1
 */
@Slf4j
public class NettyWebsocketHeartCheckHandler extends ChannelInboundHandlerAdapter {

    private final ServerContext serverContext;
    private final WebsocketConfig websocketConfig;
    private final SocketSessionManager socketSessionManager;

    /**
     * 重连次数
     */
    private int unRecPingTimes;

    public NettyWebsocketHeartCheckHandler(ServerContext serverContext) {
        this.websocketConfig = serverContext.getBeanFactoryContext().getBean(WebsocketConfig.class);
        this.serverContext = serverContext;
        this.socketSessionManager = serverContext.getBeanFactoryContext().getPrimaryBean(SocketSessionManager.class);
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            String eventType = null;

            switch (event.state()) {
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";

                    break;
                default:
                    eventType = "";
            }
            log.info("客户触发心跳事件: {}", eventType);
            int retry = websocketConfig.getLostConnectRetryNum();
            if (ALL_IDLE.equals(event.state())) {
                if (unRecPingTimes >= retry) {
                    // 连续超过N次未收到client的ping消息，那么关闭该通道，等待client重连
                    ctx.channel().close();
                } else {
                    // 失败计数器加1
                    unRecPingTimes++;
                }
            }

        }
    }


    /**
     * 在服务器端不使用心跳检测的情况下，如果客户端突然拔掉网线断网（注意这里不是客户度程序关闭，而仅是异常断网）
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        // todo 这里的异常不一定是那啥异常
        if (cause instanceof IOException) {
            log.info("server " + ctx.channel().remoteAddress() + "关闭连接");
        }
//        eventProvider.sendEvent(EventTypeConst.CLOSE_CONNECT, new GIMSession(ctx.channel()));
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

        NettyWebsocketSocketSessionContext sessionContext = new NettyWebsocketSocketSessionContext(ctx);
        String sessionKey = ctx.channel().id().asLongText();
        sessionContext.setId(sessionKey);
       socketSessionManager.register(sessionKey, sessionContext);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        String sessionKey = ctx.channel().id().asLongText();
        socketSessionManager.remove(sessionKey);
    }

}
