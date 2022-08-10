package com.gilang.common.util;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gylang
 * data 2021/2/20
 */
public class ClassUtils extends ClassUtil {


    /**
     * 递归
     *
     * @param clazz 当前被代理类
     * @return (被代理)原类型
     */
    public static Class<?> getUserClass(Class<?> clazz) {

        if (clazz.getName().contains("$$")) {
            Class<?> superclass = clazz.getSuperclass();
            Class<?>[] interfaces = clazz.getInterfaces();
            if (superclass != null && superclass != Object.class) {
                // 被代理类通过接口方式实现, 继承实现, 如cglib
                return getUserClass(superclass);

            } else if (ArrayUtil.isNotEmpty(interfaces)) {
                // 被代理类通过接口方式实现, jdk动态代理
                for (Class<?> anInterface : interfaces) {
                    if (anInterface != null && anInterface != Object.class) {
                        return getUserClass(anInterface);
                    }
                }
            }
        }

        return clazz;
    }

    /**
     * 递归获取所有的父类接口
     *
     * @param clazz 被解析的类
     * @return 所有的父类
     */
    public static <T extends Annotation> T recurseFindAnnotation(Class<?> clazz, Class<T> annotationType) {

        return doRecurseFindAnnotation(clazz, annotationType);
    }

    /**
     * 递归获取所有的父类接口, 寻找指定注解
     *
     * @param clazz 被解析的类
     */
    private static <T extends Annotation> T doRecurseFindAnnotation(Class<?> clazz, Class<T> annotationType) {
        if (clazz != Object.class) {
            Class<?> superclass = clazz.getSuperclass();
            Class<?>[] interfaces = clazz.getInterfaces();

            T annotation = AnnotationUtil.getAnnotation(clazz, annotationType);
            if (null != annotation) {
                return annotation;
            }
            if (superclass != null) {
                // 被代理类通过接口方式实现, 继承实现, 如cglib
                annotation = doRecurseFindAnnotation(superclass, annotationType);
                if (null != annotation) {
                    return annotation;
                }
            }
            if (ArrayUtil.isNotEmpty(interfaces)) {
                // 被代理类通过接口方式实现, jdk动态代理
                for (Class<?> anInterface : interfaces) {
                    if (anInterface != null) {
                        annotation = doRecurseFindAnnotation(anInterface, annotationType);
                        if (null != annotation) {
                            return annotation;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 递归获取所有的父类接口
     *
     * @param clazz 被解析的类
     * @return 所有的父类
     */
    public static List<Class<?>> resolveAllType(Class<?> clazz) {

        List<Class<?>> types = new ArrayList<>();

        doResolveAllType(types, clazz);
        return types;
    }

    /**
     * 递归获取所有的父类接口
     *
     * @param clazz 被解析的类
     */
    private static void doResolveAllType(List<Class<?>> types, Class<?> clazz) {
        if (clazz != Object.class) {
            types.add(clazz);
            Class<?> superclass = clazz.getSuperclass();
            Class<?>[] interfaces = clazz.getInterfaces();
            if (superclass != null) {
                // 被代理类通过接口方式实现, 继承实现, 如cglib
                doResolveAllType(types, superclass);

            }
            if (ArrayUtil.isNotEmpty(interfaces)) {
                // 被代理类通过接口方式实现, jdk动态代理
                for (Class<?> anInterface : interfaces) {
                    if (anInterface != null) {
                        doResolveAllType(types, anInterface);
                    }
                }
            }
        }
    }

}
