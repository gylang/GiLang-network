package com.gilang.network.load;

import com.gilang.common.context.BeanLoadWrapper;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.ServerContext;
import com.gilang.network.event.EventContext;
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

         supplierList.add(new BeanLoadWrapper<>(MessageUtil.class, MessageUtil::new));
        supplierList.add(new BeanLoadWrapper<>(EventContext.class, EventContext::new));


        return supplierList;
    }
}
