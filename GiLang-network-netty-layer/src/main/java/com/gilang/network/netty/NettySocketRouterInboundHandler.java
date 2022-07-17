package com.gilang.network.netty;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.layer.app.socket.SocketAppLayerInvokerAdapter;
import com.gilang.network.layer.session.SessionManager;
import com.gilang.network.netty.context.NettySessionContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gylang
 * data 2022/6/16
 */
@ChannelHandler.Sharable
public class NettySocketRouterInboundHandler extends SimpleChannelInboundHandler<SocketDataPackage<?>> implements AfterNetWorkContextInitialized {

    private SocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter;

    private SessionManager sessionManager;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SocketDataPackage<?> dataPackage) throws Exception {

        socketAppLayerInvokerAdapter.route(dataPackage, sessionManager.getSessionBySessionKey(channelHandlerContext.channel().id().asLongText()));
    }

    @Override
    public void post(ServerContext serverContext) {
        this.socketAppLayerInvokerAdapter = serverContext.getBeanFactoryContext().getPrimaryBean(SocketAppLayerInvokerAdapter.class);
        this.sessionManager = serverContext.getSessionManager();
    }
}
