package com.gilang.network.context;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.config.WebsocketConfig;

/**
 * @author gylang
 * data 2022/7/8
 */
public class PropertiesBeanLoader implements BeanLoader {
    @Override
    public void scan(ServerContext serverContext) {
        PropertiesVisitor propertiesVisitor = serverContext.getPropertiesVisitor();
        BeanFactoryContext beanFactoryContext = serverContext.getBeanFactoryContext();
        beanFactoryContext.register("WebsocketConfig", propertiesVisitor.parseObject("gilang.network.websocket", WebsocketConfig.class));
    }
}
