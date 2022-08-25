package com.gilang.network.netty.websocket;

import com.gilang.common.domian.socket.SocketDataPackage;
import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.socket.SocketSessionManager;
import com.gilang.network.socket.SocketSocketAppLayerInvokerAdapter;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gylang
 * data 2022/6/16
 */
@ChannelHandler.Sharable
public class NettyWebsocketRouterInboundHandler extends SimpleChannelInboundHandler<SocketDataPackage<?>> implements AfterNetWorkContextInitialized {

    private SocketSocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter;

    private SocketSessionManager socketSessionManager;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SocketDataPackage<?> dataPackage) throws Exception {

        socketAppLayerInvokerAdapter.route(dataPackage, socketSessionManager.getSessionBySessionKey(channelHandlerContext.channel().id().asLongText()));
    }

    @Override
    public void post(ServerContext serverContext) {
        this.socketAppLayerInvokerAdapter = serverContext.getBeanFactoryContext().getPrimaryBean(SocketSocketAppLayerInvokerAdapter.class);
        this.socketSessionManager = serverContext.getBeanFactoryContext().getPrimaryBean(SocketSessionManager.class);
    }
}
