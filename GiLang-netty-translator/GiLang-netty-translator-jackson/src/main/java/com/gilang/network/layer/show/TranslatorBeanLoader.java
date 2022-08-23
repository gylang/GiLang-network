package com.gilang.network.layer.show;

import com.gilang.common.context.BeanLoadWrapper;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.ServerContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gylang
 * data 2022/7/10
 */
public class TranslatorBeanLoader implements BeanLoader {

    @Override
    public List<BeanLoadWrapper<?>> scan(ServerContext serverContext) {
        List<BeanLoadWrapper<?>> beanLoadWrappers = new ArrayList<>();
        beanLoadWrappers.add(new BeanLoadWrapper<>(JacksonPackageTranslator.class, JacksonPackageTranslator::new));
        beanLoadWrappers.add(new BeanLoadWrapper<>(HttpJsonTranslator.class, HttpJsonTranslator::new));
        return beanLoadWrappers;
    }
}
