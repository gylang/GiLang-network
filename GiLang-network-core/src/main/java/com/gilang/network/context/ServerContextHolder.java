package com.gilang.network.context;

/**
 * @author gylang
 * data 2022/6/30
 */
public class ServerContextHolder {

    private static final ServerContext serverContext = new ServerContext();


    public static ServerContext instance() {
        return serverContext;
    }
}
