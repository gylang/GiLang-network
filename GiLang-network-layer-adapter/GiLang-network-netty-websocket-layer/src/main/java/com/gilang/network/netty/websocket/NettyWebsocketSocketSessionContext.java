package com.gilang.network.netty.websocket;

import com.gilang.network.socket.context.SocketSessionContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.util.Optional;

/**
 * @author gylang
 * data 2022/6/16
 */
public class NettyWebsocketSocketSessionContext extends SocketSessionContext {

    private final ChannelHandlerContext context;

    public NettyWebsocketSocketSessionContext(ChannelHandlerContext context) {
        this.context = context;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T attr(String key) {
        return (T)Optional.ofNullable(context.channel())
                .map( c-> c.attr(AttributeKey.valueOf(key)))
                .map(Attribute::get).orElse(null);
    }

    @Override
    public void setAttr(String key, Object attr) {
        context.channel().attr(AttributeKey.valueOf(key)).set(attr);
    }

    @Override
    public boolean hasAttr(String key) {
        return context.channel().hasAttr(AttributeKey.valueOf(key));
    }

    @Override
    public boolean isRemoved() {
        return context.isRemoved();
    }

    @Override
    public void write(Object message) {
        context.writeAndFlush(message);
    }
}
