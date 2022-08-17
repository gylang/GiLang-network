package com.gilang.common.util;

import com.gilang.common.annotation.Order;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author gylang
 * data 2022/8/17
 */
public class OrderUtil {


    /**
     * 通过注解获取权重值最小
     *
     * @param list            权重列表
     * @param defaultOrderNum 当获取不到权值的默认值
     * @param <T>             排序列表的值类型
     * @return 权重值最小的元素
     */
    public static  <T> T minOrderByAnnotation(List<T> list, int defaultOrderNum) {

        return list.stream().min(Comparator.comparingInt(v ->
                Optional.ofNullable(ClassUtils.recurseFindAnnotation(v.getClass(), Order.class)).map(Order::value)
                        .orElse(defaultOrderNum))).orElse(null);
    }

    /**
     * 通过注解获取权重值最大
     *
     * @param list            权重列表
     * @param defaultOrderNum 当获取不到权值的默认值
     * @param <T>             排序列表的值类型
     * @return 权重值最小的元素
     */
    public static <T> T maxOrderByAnnotation(List<T> list, int defaultOrderNum) {

        return list.stream().max(Comparator.comparingInt(v ->
                Optional.ofNullable(ClassUtils.recurseFindAnnotation(v.getClass(), Order.class)).map(Order::value)
                        .orElse(defaultOrderNum))).orElse(null);
    }
}
