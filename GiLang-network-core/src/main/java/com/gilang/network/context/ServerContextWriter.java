package com.gilang.network.context;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.event.EventContext;
import com.gilang.network.layer.access.ServerRunner;
import com.gilang.network.layer.session.SocketSessionManager;

import java.util.List;

/**
 * 启动im服务的上下文对象
 *
 * @author gylang
 * data 2022/5/31
 */

public class ServerContextWriter {


    public static void setBeanFactoryContext(ServerContext serverContext, BeanFactoryContext beanFactoryContext) {
        serverContext.setBeanFactoryContext(beanFactoryContext);
    }

    public static void setEventContext(ServerContext serverContext, EventContext eventContext) {
        serverContext.setEventContext(eventContext);
    }

    public static void setSocketSessionManager(ServerContext serverContext, SocketSessionManager socketSessionManager) {
        serverContext.setSocketSessionManager(socketSessionManager);

    }

    public static void setServerRunner(ServerContext serverContext, List<ServerRunner> serverRunner) {
        serverContext.setServerRunner(serverRunner);
    }

    public static void setPropertiesVisitor(ServerContext serverContext, PropertiesVisitor propertiesVisitor) {
        serverContext.setPropertiesVisitor(propertiesVisitor);
    }

    public static void setServerContextArgument(ServerContext serverContext, ServerContextArgument serverContextArgument) {
        serverContext.setServerContextArgument(serverContextArgument);
    }
}
