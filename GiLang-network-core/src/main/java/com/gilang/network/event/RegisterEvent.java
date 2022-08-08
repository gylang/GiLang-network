package com.gilang.network.event;

import com.gilang.network.context.SocketSessionContext;
import lombok.Getter;

/**
 * @author gylang
 * data 2022/7/17
 */
@Getter
public class RegisterEvent implements Event{

    private final SocketSessionContext socketSessionContext;

    public RegisterEvent(SocketSessionContext socketSessionContext) {
        this.socketSessionContext = socketSessionContext;
    }
}
