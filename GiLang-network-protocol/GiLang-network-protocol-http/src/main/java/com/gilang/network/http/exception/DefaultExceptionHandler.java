package com.gilang.network.http.exception;

import com.gilang.common.annotation.ExceptionType;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;

/**
 * @author 23516
 * @since 2022/8/18
 */
@ExceptionType(Exception.class)
public class DefaultExceptionHandler implements HttpExceptionHandler<Exception>{

    @Override
    public void handler(HttpDataRequest<Object> request, HttpDataResponse response, Exception exception) {
        response.setStatus(500);
        response.setPayload("你这个喜仔搞什么, 报错了哇~, 你看看这个错误: " + exception.getMessage());
    }
}
