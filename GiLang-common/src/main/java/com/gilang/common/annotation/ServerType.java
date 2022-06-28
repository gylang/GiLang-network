package com.gilang.common.annotation;

import com.gilang.common.enums.ServerTypeEnum;

/**
 * 服务类型, http,socket, websocket
 * @author gylang
 * data 2022/6/28
 */

public @interface ServerType {

    ServerTypeEnum value();
}
