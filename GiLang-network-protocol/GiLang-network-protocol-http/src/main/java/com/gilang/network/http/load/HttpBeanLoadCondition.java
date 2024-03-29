package com.gilang.network.http.load;

import com.gilang.common.context.BeanLoadWrapper;
import com.gilang.common.util.ClassUtils;
import com.gilang.network.context.BeanLoadCondition;
import com.gilang.network.context.ServerContext;
import com.gilang.network.http.HttpServerRunner;

import java.util.List;

/**
 * @author gylang
 * data 2022/8/24
 */
public class HttpBeanLoadCondition implements BeanLoadCondition {

    private final Class<?>[] httpBean = {HttpServerRunner.class,};

    @Override
    public boolean judge(ServerContext serverContext, BeanLoadWrapper<?> currentBean, List<BeanLoadWrapper<?>> allBean) {

        boolean open = serverContext.getPropertiesVisitor().getBaseTypeValue("gilang.network.http.open", false, Boolean.class);
        if (!open) {
            // 如果没有启动服务 过滤掉没用的bean注册
            for (Class<?> clazz : httpBean) {
                return !ClassUtils.isAssignable(clazz, currentBean.getClazz());
            }
        }

        return true;
    }
}
