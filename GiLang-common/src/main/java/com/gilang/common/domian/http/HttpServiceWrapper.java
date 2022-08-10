package com.gilang.common.domian.http;

import com.gilang.common.enums.RequestMethod;
import lombok.Data;

/**
 * @author gylang
 * data 2022/8/10
 */
@Data
public class HttpServiceWrapper {

    /** 请求业务路径 */
    private String path;
    /** 方法 */
    private RequestMethod[] methods;
    /** 响应类型 */
    private String contentType;
    /** 服务调用帮助器 */
    private HttpInvokeHelper httpInvokeHelper;
}
