package com.gilang.network.layer.show;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.network.translator.HttpTranslator;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author gylang
 * data 2022/8/11
 */
public class HttpJsonTranslator extends JacksonPackageTranslator implements HttpTranslator {


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

    @Override
    public Object toObject(byte[] bs, Type type, HttpDataRequest<?> httpDataRequest) {
        try {
            return JsonUtil.mapper.readValue(bs, new TypeReference<Object>() {
                @Override
                public Type getType() {
                    return type;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Jackson deserialize exception : " + e.getMessage());
        }
    }
}
