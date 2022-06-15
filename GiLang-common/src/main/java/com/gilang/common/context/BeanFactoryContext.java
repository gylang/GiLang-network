package com.gilang.common.context;

import cn.hutool.core.collection.CollUtil;
import com.gilang.common.exception.BeanException;
import com.gilang.common.util.Asserts;
import com.gilang.common.util.ClassUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gylang
 * data 2022/6/7
 */
@SuppressWarnings("unchecked")
public class BeanFactoryContext {


    /** bean的下标查找 */
    private final Map<Integer, BeanWrapper<?>> indexBean = new ConcurrentHashMap<>();

    /** 名称索引 */
    private final Map<String, Integer> nameIndex = new ConcurrentHashMap<>();

    /** 类型索引 */
    private final Map<Class<?>, LinkedHashSet<Integer>> typeIndex = new ConcurrentHashMap<>();

    /** 生命周期调用 */
    private List<BeanFactoryContextLifecycle> beanFactoryContextLifecycleList = new ArrayList<>();

    /**
     * 获取bean
     *
     * @param clazz bean的类型
     * @return bean
     */
    public <T> T getBean(Class<T> clazz) {

        LinkedHashSet<Integer> indexs = typeIndex.get(clazz);
        if (CollUtil.isNotEmpty(indexs)) {
            Asserts.isTrue(indexs.size() > 1, () -> new BeanException(clazz.getName(), "存在多个bean"));
        }
        return (T) indexBean.get(CollUtil.getFirst(indexs)).getBean();
    }

    /**
     * 获取bean
     *
     * @param clazz bean的类型
     * @return bean
     */
    public <T> T getPrimaryBean(Class<T> clazz) {

        LinkedHashSet<Integer> indexs = typeIndex.get(clazz);
        // todo 多个类型, 选择主要
        if (CollUtil.isNotEmpty(indexs)) {
            Asserts.isTrue(indexs.size() > 1, () -> new BeanException(clazz.getName(), "存在多个bean"));
        }
        return (T) indexBean.get(CollUtil.getFirst(indexs)).getBean();
    }

    /**
     * 获取bean list
     *
     * @param clazz bean的类型
     * @return bean
     */
    public <T> List<T> getBeanList(Class<T> clazz) {

        LinkedHashSet<Integer> indexs = typeIndex.get(clazz);
        if (CollUtil.isNotEmpty(indexs)) {
            return indexs.stream().map(indexBean::get)
                    .map(b -> (T) b.getBean())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * 获取bean map
     * k->name b ->bean
     *
     * @param clazz bean的类型
     * @return bean
     */
    public <T> Map<String, T> getBeanMap(Class<T> clazz) {

        LinkedHashSet<Integer> indexs = typeIndex.get(clazz);
        List<BeanWrapper<?>> beanWrapperList = indexs.stream().map(indexBean::get)
                .collect(Collectors.toList());
        return beanWrapperList.stream().collect(Collectors.toMap(BeanWrapper::getName, v -> (T) v.getBean()));
    }

    /**
     * 获取bean map
     * k->nameFunc生成 b ->bean
     *
     * @param clazz    bean的类型
     * @param nameFunc 自定义名称
     * @return bean
     */
    public <T> Map<String, T> getBeanMap(Class<T> clazz, Function<BeanWrapper<T>, String> nameFunc) {

        List<BeanWrapper<T>> beanList = new ArrayList<>();
        LinkedHashSet<Integer> indexs = typeIndex.get(clazz);
        List<BeanWrapper<?>> beanWrapperList = indexs.stream().map(indexBean::get)
                .collect(Collectors.toList());
        return beanWrapperList.stream()
                .collect(Collectors.toMap(beanWrapper -> nameFunc.apply((BeanWrapper<T>) beanWrapper), v -> (T) v.getBean()));
    }

    /**
     * 注册bean
     *
     * @param name bean名称
     * @param bean bean对象
     */
    public void register(String name, Object bean) {

        doRegister(name, bean);
    }

    /**
     * 执行注册bean
     *
     * @param name bean名称
     * @param bean bean对象
     */
    private synchronized void doRegister(String name, Object bean) {

        Asserts.notNull(bean, () -> new BeanException(name, "bean为null"));
        Asserts.isFalse(nameIndex.containsKey(name), () -> new BeanException(name, "重复注册bean"));

        int index = nameIndex.size();

        List<Class<?>> types = ClassUtils.resolveAllType(bean.getClass());
        BeanWrapper<?> beanWrapper = new BeanWrapper<>(name, bean, types);

        for (BeanFactoryContextLifecycle beanFactoryContextLifecycle : beanFactoryContextLifecycleList) {
            beanFactoryContextLifecycle.beanRegisterBefore(beanWrapper);
        }
        // 名称下标
        nameIndex.put(name, index);
        // 存储bean
        indexBean.put(index, beanWrapper);
        // 存储类型索引
        for (Class<?> type : types) {
            Set<Integer> typeIndexs = typeIndex.computeIfAbsent(type, k -> new LinkedHashSet<>());
            typeIndexs.add(index);

        }
        for (BeanFactoryContextLifecycle beanFactoryContextLifecycle : beanFactoryContextLifecycleList) {
            beanFactoryContextLifecycle.beanRegisterAfter(beanWrapper);
        }
    }
}
