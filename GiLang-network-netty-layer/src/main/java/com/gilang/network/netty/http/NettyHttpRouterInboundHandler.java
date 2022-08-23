package com.gilang.network.netty.http;

import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.layer.app.http.HttpAppLayerInvokerAdapter;
import com.gilang.network.netty.context.NettyHttpSessionContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gylang
 * data 2022/6/16
 */
@ChannelHandler.Sharable
public class NettyHttpRouterInboundHandler extends SimpleChannelInboundHandler<HttpDataRequest> implements AfterNetWorkContextInitialized {

    private HttpAppLayerInvokerAdapter httpAppLayerInvokerAdapter;


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpDataRequest dataPackage) throws Exception {

        httpAppLayerInvokerAdapter.route(dataPackage, new NettyHttpSessionContext(channelHandlerContext));
    }

    @Override
    public void post(ServerContext serverContext) {
        this.httpAppLayerInvokerAdapter = serverContext.getBeanFactoryContext().getPrimaryBean(HttpAppLayerInvokerAdapter.class);
    }
}
