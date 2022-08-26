package com.gilang.network.example.action.http;

import com.gilang.common.annotation.RequestMapping;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.network.http.router.HttpAction;

/**
 * @author gylang
 * data 2022/8/23
 */
@RequestMapping(path = "/cors")
public class CorsApi implements HttpAction<String> {


    @Override
    public void doAction(HttpDataRequest<String> dataPackage, HttpDataResponse response) {

        response.setSuccessBody("跨域了, 应该看不到哦");
    }
}
