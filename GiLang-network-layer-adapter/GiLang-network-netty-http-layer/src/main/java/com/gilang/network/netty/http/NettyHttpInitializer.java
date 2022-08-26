package com.gilang.network.netty.http;

import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.http.config.HttpConfig;
import com.gilang.network.http.router.HttpAppLayerInvokerAdapter;
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


public class NettyHttpInitializer extends ChannelInitializer<SocketChannel> implements AfterNetWorkContextInitialized {

    private ServerContext serverContext;
    private HttpAppLayerInvokerAdapter httpAppLayerInvokerAdapter;
    private NettyHttpRouterInboundHandler nettyHttpRouterInboundHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        HttpConfig websocketConfig = serverContext.getBeanFactoryContext().getBean(HttpConfig.class);
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
        pipeline.addLast(new NettyHttpDecoder(httpAppLayerInvokerAdapter));
        pipeline.addLast(new NettyHttpEncoder(httpAppLayerInvokerAdapter));

        pipeline.addLast("dispatch", nettyHttpRouterInboundHandler);
    }


    @Override
    public void post(ServerContext serverContext) {
        this.serverContext = serverContext;
        this.httpAppLayerInvokerAdapter = serverContext.getBeanFactoryContext().getPrimaryBean(HttpAppLayerInvokerAdapter.class);
        this.nettyHttpRouterInboundHandler = serverContext.getBeanFactoryContext().getPrimaryBean(NettyHttpRouterInboundHandler.class);

    }
}

