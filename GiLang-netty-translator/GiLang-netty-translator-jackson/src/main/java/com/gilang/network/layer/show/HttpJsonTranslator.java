package com.gilang.network.layer.show;

import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.network.layer.show.http.HttpTranslator;

import java.io.IOException;

/**
 * @author gylang
 * data 2022/8/11
 */
public class HttpJsonTranslator extends JacksonPackageTranslator implements HttpTranslator {

    @Override
    public <T> void writeBody(byte[] bs, Class<T> type, HttpDataRequest<T> httpDataRequest) {

        try {
            T t = JsonUtil.mapper.readValue(bs, type);
            httpDataRequest.setPayload(t);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Jackson deserialize exception : " + e.getMessage());
        }
    }

    @Override
    public byte[] toByte(Object object) {
        try {
            return JsonUtil.mapper.writeValueAsBytes(object);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Jackson deserialize exception : " + e.getMessage());
        }
    }

    @Override
    public String[] supportContentTypes() {
        return new String[]{"application/json"};
    }
}
