package com.gilang.network.context;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.config.WebsocketConfig;
import com.gilang.network.event.EventContext;
import com.gilang.network.layer.app.socket.SimpleSocketAppLayerInvokerAdapter;
import com.gilang.network.layer.session.SessionManagerImpl;
import com.gilang.network.util.MessageUtil;

/**
 * @author gylang
 * data 2022/7/8
 */
public class CoreBeanLoader implements BeanLoader {
    @Override
    public void scan(ServerContext serverContext) {
        PropertiesVisitor propertiesVisitor = serverContext.getPropertiesVisitor();
        BeanFactoryContext beanFactoryContext = serverContext.getBeanFactoryContext();
        beanFactoryContext.register(WebsocketConfig.class.getName(), propertiesVisitor.parseObject("gilang.network.websocket", WebsocketConfig.class));
        beanFactoryContext.register(SimpleSocketAppLayerInvokerAdapter.class.getName(), new SimpleSocketAppLayerInvokerAdapter());
        beanFactoryContext.register(MessageUtil.class.getName(), new MessageUtil());
        beanFactoryContext.register(SessionManagerImpl.class.getName(), new SessionManagerImpl());
        beanFactoryContext.register(EventContext.class.getName(), new EventContext());
    }
}
