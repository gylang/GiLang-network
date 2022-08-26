package com.gilang.network.http.exception;

import com.gilang.common.annotation.ExceptionType;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;

/**
 * @author 23516
 * @since 2022/8/18
 */
@ExceptionType(Http404Exception.class)
public class Default404ExceptionHandler implements HttpExceptionHandler<Http404Exception> {

    @Override
    public void handler(HttpDataRequest<Object> request, HttpDataResponse response, Http404Exception exception) {
        response.setStatus(404);
        response.setPayload("你这个喜仔搞什么, 报错了哇~, 找不到这个页面" + request.getDecodedUri());
    }
}
