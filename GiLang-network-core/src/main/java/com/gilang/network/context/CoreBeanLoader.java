package com.gilang.network.context;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.config.HttpConfig;
import com.gilang.network.config.WebsocketConfig;
import com.gilang.network.event.EventContext;
import com.gilang.network.http.handler.HttpExceptionHandlerManager;
import com.gilang.network.layer.app.http.HttpActionRegister;
import com.gilang.network.layer.app.http.ResponseRenderImpl;
import com.gilang.network.layer.app.http.SimpleHttpAppLayerInvokerAdapterImpl;
import com.gilang.network.layer.app.socket.SimpleSocketSocketAppLayerInvokerAdapter;
import com.gilang.network.layer.session.SocketSessionManagerImpl;
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
        beanFactoryContext.register(SimpleSocketSocketAppLayerInvokerAdapter.class.getName(), new SimpleSocketSocketAppLayerInvokerAdapter());
        beanFactoryContext.register(MessageUtil.class.getName(), new MessageUtil());
        beanFactoryContext.register(SocketSessionManagerImpl.class.getName(), new SocketSessionManagerImpl());
        beanFactoryContext.register(EventContext.class.getName(), new EventContext());

        // http
        beanFactoryContext.register(SimpleHttpAppLayerInvokerAdapterImpl.class.getName(), new SimpleHttpAppLayerInvokerAdapterImpl());
        beanFactoryContext.register(HttpActionRegister.class.getName(), new HttpActionRegister());
        beanFactoryContext.register(ResponseRenderImpl.class.getName(), new ResponseRenderImpl());
        beanFactoryContext.register(HttpConfig.class.getName(), propertiesVisitor.parseObject("gilang.network.http", HttpConfig.class));

        // 拦截器
        beanFactoryContext.register(HttpExceptionHandlerManager.class.getName(), new HttpExceptionHandlerManager());


    }
}
