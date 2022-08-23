package com.gilang.network.example;

import com.gilang.network.context.ServerContext;
import com.gilang.network.layer.access.ServerRunner;
import com.gilang.network.netty.http.NettyHttpServerRunner;
import com.gilang.network.netty.ws.NettyWebsocketServerRunner;
import com.gilang.network.starter.Application;
import com.gilang.network.starter.ContextLoader;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author gylang
 * data 2022/6/28
 */
public class ExampleServerStarter {


    public static void main(String[] args) throws Exception {

        Application application = new Application();
        application.start(args);

    }
}
