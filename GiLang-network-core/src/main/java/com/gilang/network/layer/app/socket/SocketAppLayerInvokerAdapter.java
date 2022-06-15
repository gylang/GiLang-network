package com.gilang.network.layer.app.socket;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.layer.app.AppLayerInvokerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gylang
 * data 2022/6/15
 */
public abstract class SocketAppLayerInvokerAdapter extends SimpleChannelInboundHandler<SocketDataPackage<?>> implements AppLayerInvokerAdapter<Byte> {
}
