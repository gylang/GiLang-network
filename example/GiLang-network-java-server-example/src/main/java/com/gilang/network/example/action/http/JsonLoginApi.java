package com.gilang.network.example.action.http;

import com.gilang.common.annotation.RequestMapping;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.network.http.router.HttpAction;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gylang
 * data 2022/8/26
 */
@RequestMapping(path = "/json/login")
@Slf4j
public class JsonLoginApi implements HttpAction<Map<String, Object>> {
    @Override
    public void doAction(HttpDataRequest<Map<String, Object>> dataPackage, HttpDataResponse response) {

        Map<String, Object> payload = dataPackage.getPayload();
        log.info("username : {}, password : {}", payload.get("username"), payload.get("password"));
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("msg", "成功");
        map.put("data", "eyfjaiwfjawiofjawfiawohfoiawgfhnioew");
        response.setPayload(map);
    }
}
