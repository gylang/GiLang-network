package com.gilang.network.example;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.context.SessionContext;
import com.gilang.network.converter.PackageConverter;
import com.gilang.network.layer.app.socket.MessageAction;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gylang
 * data 2022/7/8
 */
@Slf4j
public class CallBackAction implements MessageAction<String> {

    @Override
    public void doAction(SocketDataPackage<String> dataPackage, SessionContext sessionContext) {

        String payload = dataPackage.getPayload();
        log.info("payload : {}", payload);
        SocketDataPackage<String> callBackPackage = PackageConverter.copyBaseNew(dataPackage);
        callBackPackage.setPayload("I have received your message '" + payload + "'");
        sessionContext.write(callBackPackage);
    }
}
