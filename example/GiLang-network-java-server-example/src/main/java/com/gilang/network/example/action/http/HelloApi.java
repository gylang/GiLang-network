package com.gilang.network.example.action.http;

import com.gilang.common.annotation.RequestMapping;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.network.layer.app.http.HttpAction;

import java.util.Collections;

/**
 * @author gylang
 * data 2022/8/11
 */
@RequestMapping(path = "/hello/{name}")
public class HelloApi implements HttpAction<String> {


    @Override
    public void doAction(HttpDataRequest<String> dataPackage, HttpDataResponse response) {
        response.setPayload(Collections.singletonList(dataPackage.getPathDecodeVariable("name")));
    }
}
