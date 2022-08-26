package com.gilang.network.netty.socket;

import com.gilang.network.event.Event;
import com.gilang.network.socket.context.SocketSessionContext;
import lombok.Getter;

/**
 * @author gylang
 * data 2022/7/17
 */
@Getter
public class RegisterEvent implements Event {

    private final SocketSessionContext socketSessionContext;

    public RegisterEvent(SocketSessionContext socketSessionContext) {
        this.socketSessionContext = socketSessionContext;
    }
}
