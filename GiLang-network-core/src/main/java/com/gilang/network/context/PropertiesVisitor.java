package com.gilang.network.context;

import cn.hutool.core.convert.Convert;
import cn.hutool.setting.dialect.Props;

/**
 * @author gylang
 * data 2022/6/30
 */
public class PropertiesVisitor {

    protected final Props props = new Props();

    /**
     * 获取基本的数据类型值
     *
     * @param key   key
     * @param clazz 类型
     * @param <T>   类型
     * @return 配置值
     */
    public <T> T getBaseTypeValue(String key, Class<T> clazz) {
        return Convert.convert(clazz, props.getStr(key));
    }

    /**
     * 获取基本的数据类型值
     *
     * @param key          key
     * @param clazz        类型
     * @param <T>          类型
     * @param defaultValue 默认值
     * @return 配置值
     */
    public <T> T getBaseTypeValue(String key, T defaultValue, Class<T> clazz) {
        return Convert.convert(clazz, props.getStr(key));
    }

    /**
     * 将配置文件转成bean
     *
     * @param prefix 前缀
     * @param clazz  类型
     * @param <T>    类型
     * @return 配置值
     */
    public <T> T parseObject(String prefix, Class<T> clazz) {
        if (null != prefix) {
            return props.toBean(clazz, prefix);
        } else {
            return props.toBean(clazz);
        }
    }


}
