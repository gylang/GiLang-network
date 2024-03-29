package com.gilang.network.socket.load;

import com.gilang.common.context.BeanLoadWrapper;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.ServerContext;
import com.gilang.network.socket.config.SocketConfig;
import com.gilang.network.socket.context.SocketSessionManagerImpl;
import com.gilang.network.socket.router.SimpleSocketAppLayerInvokerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gylang
 * data 2022/7/8
 */
public class SocketCoreBeanLoader implements BeanLoader {
    @Override
    public List<BeanLoadWrapper<?>> scan(ServerContext serverContext) {
        List<BeanLoadWrapper<?>> supplierList = new ArrayList<>();
        supplierList.add(new BeanLoadWrapper<>(SocketConfig.class, () -> serverContext.getPropertiesVisitor().parseObject("gilang.network.websocket", SocketConfig.class)));
        supplierList.add(new BeanLoadWrapper<>(SocketSessionManagerImpl.class, SocketSessionManagerImpl::new));
        supplierList.add(new BeanLoadWrapper<>(SimpleSocketAppLayerInvokerAdapter.class, SimpleSocketAppLayerInvokerAdapter::new));
        return supplierList;
    }
}
