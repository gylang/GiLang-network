package com.gilang.network.netty.context;

import com.gilang.common.domian.http.HttpSessionContext;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author gylang
 * data 2022/8/11
 */
public class NettyHttpSessionContext extends HttpSessionContext {

    private final ChannelHandlerContext context;

    public NettyHttpSessionContext(ChannelHandlerContext channelHandlerContext) {
        context = channelHandlerContext;
    }

    @Override
    public void write(Object message) {
        context.writeAndFlush(message).addListener(ChannelFutureListener.CLOSE);
    }
}
