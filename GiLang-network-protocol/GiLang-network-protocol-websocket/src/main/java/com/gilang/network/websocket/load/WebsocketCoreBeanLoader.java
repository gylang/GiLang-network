package com.gilang.network.websocket.load;

import com.gilang.common.context.BeanLoadWrapper;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.PropertiesVisitor;
import com.gilang.network.context.ServerContext;
import com.gilang.network.websocket.WebsocketConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gylang
 * data 2022/7/8
 */
public class WebsocketCoreBeanLoader implements BeanLoader {
    @Override
    public List<BeanLoadWrapper<?>> scan(ServerContext serverContext) {
        List<BeanLoadWrapper<?>> supplierList = new ArrayList<>();
        PropertiesVisitor propertiesVisitor = serverContext.getPropertiesVisitor();
        supplierList.add(new BeanLoadWrapper<>(WebsocketConfig.class, () -> propertiesVisitor.parseObject("gilang.network.websocket", WebsocketConfig.class)));
        return supplierList;
    }
}
