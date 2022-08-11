package com.gilang.network.example;

import com.gilang.network.context.ServerContext;
import com.gilang.network.netty.http.NettyHttpServerRunner;
import com.gilang.network.netty.ws.NettyWebsocketServerRunner;
import com.gilang.network.starter.ContextLoader;

/**
 * @author gylang
 * data 2022/6/28
 */
public class ExampleServerStarter {


    public static void main(String[] args) throws Exception {

        ContextLoader contextLoader = new ContextLoader();
        contextLoader.setPropertiesPath("gilang.properties");
        ServerContext serverContext = contextLoader.contextLoad();
        NettyWebsocketServerRunner websocketServerRunner = new NettyWebsocketServerRunner();
        new Thread(() -> {
            try {
                websocketServerRunner.start(serverContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        NettyHttpServerRunner nettyHttpServerRunner = new NettyHttpServerRunner();
        nettyHttpServerRunner.start(serverContext);
    }
}
