package com.gilang.network.load;

import com.gilang.common.context.BeanLoadWrapper;
import com.gilang.network.config.HttpConfig;
import com.gilang.network.config.WebsocketConfig;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.PropertiesVisitor;
import com.gilang.network.context.ServerContext;
import com.gilang.network.event.EventContext;
import com.gilang.network.http.filter.HttpCorsFilter;
import com.gilang.network.http.filter.HttpFilterDelegate;
import com.gilang.network.http.handler.Default404ExceptionHandler;
import com.gilang.network.http.handler.DefaultExceptionHandler;
import com.gilang.network.http.handler.HttpExceptionHandlerManager;
import com.gilang.network.layer.app.http.HttpActionRegister;
import com.gilang.network.layer.app.http.ResponseRenderImpl;
import com.gilang.network.layer.app.http.SimpleHttpAppLayerInvokerAdapterImpl;
import com.gilang.network.layer.app.socket.SimpleSocketSocketAppLayerInvokerAdapter;
import com.gilang.network.layer.session.SocketSessionManagerImpl;
import com.gilang.network.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gylang
 * data 2022/7/8
 */
public class CoreBeanLoader implements BeanLoader {
    @Override
    public List<BeanLoadWrapper<?>> scan(ServerContext serverContext) {
        List<BeanLoadWrapper<?>> supplierList = new ArrayList<>();
        PropertiesVisitor propertiesVisitor = serverContext.getPropertiesVisitor();


        supplierList.add(new BeanLoadWrapper<>(WebsocketConfig.class,()-> propertiesVisitor.parseObject("gilang.network.websocket", WebsocketConfig.class)));
        supplierList.add(new BeanLoadWrapper<>(SimpleSocketSocketAppLayerInvokerAdapter.class, SimpleSocketSocketAppLayerInvokerAdapter::new));
        supplierList.add(new BeanLoadWrapper<>(MessageUtil.class, MessageUtil::new));
        supplierList.add(new BeanLoadWrapper<>(SocketSessionManagerImpl.class, SocketSessionManagerImpl::new));
        supplierList.add(new BeanLoadWrapper<>(EventContext.class, EventContext::new));

        // http
        supplierList.add(new BeanLoadWrapper<>(SimpleHttpAppLayerInvokerAdapterImpl.class, SimpleHttpAppLayerInvokerAdapterImpl::new));
        supplierList.add(new BeanLoadWrapper<>(HttpActionRegister.class, HttpActionRegister::new));
        supplierList.add(new BeanLoadWrapper<>(ResponseRenderImpl.class, ResponseRenderImpl::new));
        supplierList.add(new BeanLoadWrapper<>(HttpConfig.class,()-> propertiesVisitor.parseObject("gilang.network.http", HttpConfig.class)));

        // 全家异常处理
        supplierList.add(new BeanLoadWrapper<>(HttpExceptionHandlerManager.class, HttpExceptionHandlerManager::new));
        supplierList.add(new BeanLoadWrapper<>(DefaultExceptionHandler.class, DefaultExceptionHandler::new));
        supplierList.add(new BeanLoadWrapper<>(Default404ExceptionHandler.class, Default404ExceptionHandler::new));
        supplierList.add(new BeanLoadWrapper<>(HttpCorsFilter.class, HttpCorsFilter::new));
        supplierList.add(new BeanLoadWrapper<>(HttpFilterDelegate.class, HttpFilterDelegate::new));

        return supplierList;
    }
}
