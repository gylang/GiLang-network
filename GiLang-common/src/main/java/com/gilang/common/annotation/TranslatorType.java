package com.gilang.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 交互数据的编码与解码的翻译器类型表
 *
 * @author gylang
 * data 2022/6/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TranslatorType {

    /** translatorType 命令类型 */
    byte value();
}
