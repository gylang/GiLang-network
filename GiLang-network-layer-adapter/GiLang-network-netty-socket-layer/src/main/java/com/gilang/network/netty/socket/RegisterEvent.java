package com.gilang.network.netty.socket;

import com.gilang.network.context.SessionContext;
import com.gilang.network.event.Event;
import lombok.Getter;

/**
 * @author gylang
 * data 2022/7/17
 */
@Getter
public class RegisterEvent implements Event {

    private final SessionContext socketSessionContext;

    public RegisterEvent(SessionContext socketSessionContext) {
        this.socketSessionContext = socketSessionContext;
    }
}
