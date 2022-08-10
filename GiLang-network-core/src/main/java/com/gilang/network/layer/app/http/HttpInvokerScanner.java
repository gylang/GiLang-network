package com.gilang.network.layer.app.http;

import com.gilang.common.domian.http.HttpServiceWrapper;

import java.util.List;

/**
 * @author gylang
 * data 2022/8/10
 */
public interface HttpInvokerScanner {


    /**
     * httpService扫描器
     *
     * @return 服务包装数据
     */
    List<HttpServiceWrapper> scan();
}
