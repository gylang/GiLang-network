package com.gilang.network.context;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.event.EventContext;
import com.gilang.network.layer.access.ServerRunner;
import com.gilang.network.layer.session.SocketSessionManager;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 启动im服务的上下文对象
 *
 * @author gylang
 * data 2022/5/31
 */

@Setter
@Getter
public class ServerContext {

    /** 容器工厂 */
    protected BeanFactoryContext beanFactoryContext;
    /** 配置文件访问器 */
    protected PropertiesVisitor propertiesVisitor;
    /** 事件处理器 */
    private EventContext eventContext;
    /** session会话管理 */
    private SocketSessionManager socketSessionManager;

    /** 应用服务 */
    private List<ServerRunner> serverRunner;

    protected String logLevel = "INFO";
}
