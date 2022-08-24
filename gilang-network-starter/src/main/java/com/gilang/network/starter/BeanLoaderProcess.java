package com.gilang.network.starter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.gilang.common.annotation.Bean;
import com.gilang.common.context.BeanFactoryContext;
import com.gilang.common.context.BeanLoadWrapper;
import com.gilang.common.util.ClassUtils;
import com.gilang.network.context.BeanLoadCondition;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.ServerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author gylang
 * data 2022/7/7
 */
@Slf4j
public class BeanLoaderProcess {

    public void process(ServerContext serverContext, List<Props> propsList) {
        List<BeanLoadWrapper<?>> beanLoadWrapperList = scan(serverContext, propsList);
        filterBean(serverContext, propsList, beanLoadWrapperList);

        register(serverContext, beanLoadWrapperList);
    }

    /**
     * 注册bean到容器
     *
     * @param serverContext       服务上下文
     * @param beanLoadWrapperList 待注册的bean
     */
    private void register(ServerContext serverContext, List<BeanLoadWrapper<?>> beanLoadWrapperList) {
        // 初始化
        BeanFactoryContext beanFactoryContext = serverContext.getBeanFactoryContext();
        for (BeanLoadWrapper<?> beanLoadWrapper : beanLoadWrapperList) {
            Class<?> clazz = beanLoadWrapper.getClazz();
            Bean bean = ClassUtils.recurseFindAnnotation(clazz, Bean.class);
            String beanName = clazz.getName();
            if (null != bean && !"".equals(bean.value())) {
                beanName = bean.value();
            }
            beanFactoryContext.register(beanName, beanLoadWrapper.getBeanInitFunc().get());
        }
    }

    /**
     * 过滤掉不符合注册条件的bean
     *
     * @param serverContext       服务上下文
     * @param propsList           配置文件
     * @param beanLoadWrapperList 扫描到的需要注册的所有bean
     */
    private void filterBean(ServerContext serverContext, List<Props> propsList, List<BeanLoadWrapper<?>> beanLoadWrapperList) {
        // 条件判断
        List<BeanLoadCondition> conditionList = propsList.stream().map(p -> p.getStr("com.gilang.network.context.BeanLoadCondition"))
                .filter(StrUtil::isNotBlank)
                .map(ps -> StrUtil.split(ps, ','))
                .filter(CollUtil::isNotEmpty)
                .flatMap(List::stream)
                .map(c -> (BeanLoadCondition)ReflectUtil.newInstance(c)).collect(Collectors.toList());

        Set<BeanLoadWrapper<?>> waitRemove = new HashSet<>();
        for (BeanLoadCondition beanLoadCondition : conditionList) {
            for (BeanLoadWrapper<?> beanLoadWrapper : beanLoadWrapperList) {
                if (!beanLoadCondition.judge(serverContext, beanLoadWrapper, beanLoadWrapperList)) {
                    waitRemove.add(beanLoadWrapper);
                }
            }
        }
        beanLoadWrapperList.removeAll(waitRemove);
    }

    /**
     * 扫描所有的bean加载器
     *
     * @param serverContext 服务上下文
     * @param propsList     配置文件列表
     * @return 扫描到的bean
     */
    private List<BeanLoadWrapper<?>> scan(ServerContext serverContext, List<Props> propsList) {
        List<BeanLoadWrapper<?>> beanLoadWrapperList = new ArrayList<>();
        // 处理扫描逻辑
        for (Props props : propsList) {
            String beanLoaderListStr = props.getStr("com.gilang.network.context.BeanLoader");
            String[] beanLoaderList = StrUtil.split(beanLoaderListStr, ",");
            for (String beanLoaderStr : beanLoaderList) {
                try {
                    BeanLoader beanLoader = ReflectUtil.newInstance(beanLoaderStr);
                    List<BeanLoadWrapper<?>> beanLoadWrappers = beanLoader.scan(serverContext);
                    if (null != beanLoadWrappers) {
                        beanLoadWrapperList.addAll(beanLoadWrappers);
                    }
                } catch (UtilException e) {
                    e.printStackTrace();
                }
            }
        }
        return beanLoadWrapperList;
    }


}
