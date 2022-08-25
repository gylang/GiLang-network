package com.gilang.network.example.action.http;

import com.gilang.common.annotation.RequestMapping;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.common.exception.BaseException;
import com.gilang.network.http.HttpAction;

/**
 * @author 23516
 * @since 2022/8/18
 */
@RequestMapping(path = "/exception/{msg}")
public class ExceptionApi implements HttpAction<String> {
    @Override
    public void doAction(HttpDataRequest<String> dataPackage, HttpDataResponse response) {
        throw new BaseException("111111", dataPackage.getPathDecodeVariable("msg"));
    }
}
