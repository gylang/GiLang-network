package com.gilang.network.example;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.ServerContext;

/**
 * @author gylang
 * data 2022/7/10
 */
public class ActionBeanLoader implements BeanLoader {

    @Override
    public void scan(ServerContext serverContext) {
        BeanFactoryContext beanFactoryContext = serverContext.getBeanFactoryContext();
        beanFactoryContext.register(CallBackAction.class.getName(), new CallBackAction());
    }
}
