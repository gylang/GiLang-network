package com.gilang.network.netty.context;

import com.gilang.network.context.SessionContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.util.Optional;

/**
 * @author gylang
 * data 2022/6/16
 */
public class NettySessionContext extends SessionContext {

    private final ChannelHandlerContext context;

    public NettySessionContext(ChannelHandlerContext context) {
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
    public boolean hasAttr(String key) {
        return context.channel().hasAttr(AttributeKey.valueOf(key));
    }

    @Override
    public boolean isRemoved() {
        return context.isRemoved();
    }
}
