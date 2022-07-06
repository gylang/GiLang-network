package com.gilang.network.netty;

import com.gilang.network.context.ServerContext;
import com.gilang.network.layer.access.ServerRunner;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;

/**
 * @author gylang
 * data 2022/7/6
 */
public abstract class NettyBaseServerRunner implements ServerRunner {

    protected EventLoopGroup workerGroup;
    protected EventLoopGroup bossGroup;
    protected ChannelFuture websocket;

    @Override
    public void close(ServerContext serverContext) {
        websocket.channel().close();
    }

    /**
     * 停止netty服务
     */
    public void destroy(EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        if (bossGroup != null && !bossGroup.isShuttingDown() && !bossGroup.isShutdown()) {
            try {
                bossGroup.shutdownGracefully();
            } catch (Exception ignore) {
            }
        }
        if (workerGroup != null && !workerGroup.isShuttingDown() && !workerGroup.isShutdown()) {
            try {
                workerGroup.shutdownGracefully();
            } catch (Exception ignore) {
            }
        }
    }
}
