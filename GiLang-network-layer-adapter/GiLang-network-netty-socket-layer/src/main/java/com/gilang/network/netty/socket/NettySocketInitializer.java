package com.gilang.network.netty.socket;

import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.socket.config.SocketConfig;
import com.gilang.network.socket.router.SocketSocketAppLayerInvokerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * netty 初始化器 当使用json进行交互时使用当前初始话器
 *
 * @author gylang
 * data 2020/11/6
 * @version v0.0.1
 */
public class NettySocketInitializer extends ChannelInitializer<SocketChannel> implements AfterNetWorkContextInitialized {

    private ServerContext serverContext;
    private SocketSocketAppLayerInvokerAdapter socketAppLayerInvokerAdapter;
    private NettySocketRouterInboundHandler nettySocketRouterInboundHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        SocketConfig websocketConfig = serverContext.getBeanFactoryContext().getBean(SocketConfig.class);
        ChannelPipeline pipeline = ch.pipeline();

        //空闲检测处理器
        // 参数 (设置时间内没有读操作(接收客户端数据) ,
        // 写时间(设置时间内没有给客户端写入数据),
        // all时间(设置时间内没有 读操作 or 写操作))
        pipeline.addLast("IdleStateHandler", new IdleStateHandler(
                websocketConfig.getReaderIdle(),
                websocketConfig.getWriteIdle(),
                websocketConfig.getAllIdle(),
                TimeUnit.SECONDS));
        //netty链式处理
        // 字符串 解码
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new NettySocketMessageDecoder(socketAppLayerInvokerAdapter));
        pipeline.addLast(new NettySocketMessageEncoder(socketAppLayerInvokerAdapter));
        pipeline.addLast("heart", new NettySocketHeartCheckHandler(serverContext));
        pipeline.addLast("dispatch", nettySocketRouterInboundHandler);
    }


    @Override
    public void post(ServerContext serverContext) {
        this.serverContext = serverContext;
        this.socketAppLayerInvokerAdapter = serverContext.getBeanFactoryContext().getPrimaryBean(SocketSocketAppLayerInvokerAdapter.class);
        this.nettySocketRouterInboundHandler = serverContext.getBeanFactoryContext().getPrimaryBean(NettySocketRouterInboundHandler.class);

    }
}

