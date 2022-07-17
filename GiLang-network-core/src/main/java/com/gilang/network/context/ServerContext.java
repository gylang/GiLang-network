package com.gilang.network.context;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.config.WebsocketConfig;
import com.gilang.network.event.EventContext;
import com.gilang.network.layer.session.SessionManager;
import lombok.Getter;
import lombok.Setter;

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
    private SessionManager sessionManager;

    protected String logLevel = "INFO";
}
