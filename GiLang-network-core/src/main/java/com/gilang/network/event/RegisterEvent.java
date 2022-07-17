package com.gilang.network.event;

import com.gilang.network.context.SessionContext;
import lombok.Getter;

/**
 * @author gylang
 * data 2022/7/17
 */
@Getter
public class RegisterEvent implements Event{

    private final SessionContext sessionContext;

    public RegisterEvent(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }
}
