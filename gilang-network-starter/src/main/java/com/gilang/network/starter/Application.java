package com.gilang.network.starter;

import com.gilang.network.context.ServerContext;
import com.gilang.network.layer.access.ServerRunner;

import java.util.List;

/**
 * @author gylang
 * data 2022/8/23
 */
public class Application {

    public void start(String[] args) {
        ContextLoader contextLoader = new ContextLoader();
        contextLoader.setPropertiesPath("gilang.properties");
        ServerContext serverContext = contextLoader.contextLoad();

        List<ServerRunner> serverRunner = serverContext.getServerRunner();
        for (ServerRunner runner : serverRunner) {
            Runnable runnable = () -> {
                try {
                    runner.start(serverContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            Thread thread = new Thread(runnable, ServerRunner.class.getName());
            thread.setDaemon(false);
            thread.start();
        }
    }
}
