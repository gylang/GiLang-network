package com.gilang.network.layer.show;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.ServerContext;

/**
 * @author gylang
 * data 2022/7/10
 */
public class TranslatorBeanLoader implements BeanLoader {

    @Override
    public void scan(ServerContext serverContext) {
        BeanFactoryContext beanFactoryContext = serverContext.getBeanFactoryContext();
        beanFactoryContext.register(JacksonPackageTranslator.class.getName(), new JacksonPackageTranslator());
    }
}
