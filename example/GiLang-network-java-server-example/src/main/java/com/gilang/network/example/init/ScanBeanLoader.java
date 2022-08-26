package com.gilang.network.example.init;

import cn.hutool.core.util.ReflectUtil;
import com.gilang.common.context.BeanLoadWrapper;
import com.gilang.common.util.ClassUtils;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.ServerContext;
import com.gilang.network.http.router.HttpAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author gylang
 * data 2022/8/26
 */
public class ScanBeanLoader implements BeanLoader {


    @Override
    public List<BeanLoadWrapper<?>> scan(ServerContext serverContext) {
        List<BeanLoadWrapper<?>> beanLoadWrappers = new ArrayList<>();
        Set<Class<?>> clazz = ClassUtils.scanPackageBySuper("com.gilang.network.example", HttpAction.class);

        clazz.forEach(c -> beanLoadWrappers.add(new BeanLoadWrapper<>((Class<Object>) c, () -> ReflectUtil.newInstance(c))));

        return beanLoadWrappers;
    }
}
