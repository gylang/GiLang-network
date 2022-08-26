package com.gilang.common.domian.http;

import com.gilang.common.enums.RequestMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Type;

/**
 * @author gylang
 * data 2022/8/10
 */
@Getter
@Builder
@AllArgsConstructor
public class HttpServiceWrapper {

    /** 请求业务路径 */
    private final String path;
    /** 方法 */
    private final RequestMethod[] methods;
    /** 响应类型 */
    private final String contentType;

    private final Type type;
    /** 服务调用帮助器 */
    private final HttpInvokeHelper httpInvokeHelper;

}
