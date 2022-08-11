package com.gilang.network.context;

import lombok.Data;

/**
 * @author gylang
 * data 2022/6/16
 */
@Data
public abstract class HttpSessionContext {


    /**
     * 写入消息
     * @param message 写入消息
     */
    public abstract void write(Object message);



}
