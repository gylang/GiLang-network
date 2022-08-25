package com.gilang.network.context;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.event.EventContext;
import com.gilang.network.ServerRunner;

import java.util.List;

/**
 * 启动im服务的上下文对象
 *
 * @author gylang
 * data 2022/5/31
 */

public class ServerContext {

    /** 容器工厂 */
    protected BeanFactoryContext beanFactoryContext;
    /** 配置文件访问器 */
    protected PropertiesVisitor propertiesVisitor;
    /** 事件处理器 */
    private EventContext eventContext;


    /** 应用服务 */
    private List<ServerRunner> serverRunner;

    /** 服务参数 */
    private ServerContextArgument serverContextArgument;

    protected String logLevel = "INFO";

    public BeanFactoryContext getBeanFactoryContext() {
        return beanFactoryContext;
    }

    protected void setBeanFactoryContext(BeanFactoryContext beanFactoryContext) {
        this.beanFactoryContext = beanFactoryContext;
    }

    public PropertiesVisitor getPropertiesVisitor() {
        return propertiesVisitor;
    }

    protected void setPropertiesVisitor(PropertiesVisitor propertiesVisitor) {
        this.propertiesVisitor = propertiesVisitor;
    }

    public EventContext getEventContext() {
        return eventContext;
    }

    protected void setEventContext(EventContext eventContext) {
        this.eventContext = eventContext;
    }



    public List<ServerRunner> getServerRunner() {
        return serverRunner;
    }

    protected void setServerRunner(List<ServerRunner> serverRunner) {
        this.serverRunner = serverRunner;
    }

    public ServerContextArgument getServerContextArgument() {
        return serverContextArgument;
    }

    protected void setServerContextArgument(ServerContextArgument serverContextArgument) {
        this.serverContextArgument = serverContextArgument;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }
}
