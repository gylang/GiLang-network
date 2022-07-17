package com.gilang.network.example.action;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.context.ServerContext;
import com.gilang.network.context.SessionContext;
import com.gilang.network.converter.PackageConverter;
import com.gilang.network.example.constant.CodeConst;
import com.gilang.network.example.domain.UserInfo;
import com.gilang.network.example.domain.payload.res.CodeRes;
import com.gilang.network.example.domain.payload.rqs.MessageRqs;
import com.gilang.network.example.session.ChatRoomSession;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.layer.app.socket.ActionType;
import com.gilang.network.layer.app.socket.MessageAction;
import com.gilang.network.layer.session.SessionManager;

import java.util.Map;

/**
 * @author gylang
 * data 2022/7/17
 */
@ActionType(3)
public class SendMessageAction implements MessageAction<MessageRqs>, AfterNetWorkContextInitialized {

    private SessionManager sessionManager;

    @Override
    public void doAction(SocketDataPackage<MessageRqs> dataPackage, SessionContext sessionContext) {

        MessageRqs userInfo = dataPackage.getPayload();

        SessionContext sessionByAliasKey = sessionManager.getSessionByAliasKey(userInfo.getReceive());
        SocketDataPackage<CodeRes> message = PackageConverter.copyBase(dataPackage);

        if (dataPackage.isOneSend()) {
            message.setPayload(new CodeRes(CodeConst.FAIL, "不在线"));
        }
        sessionByAliasKey.write(message);
        // 通知成功
        if (dataPackage.isOneSend()) {
            message.setPayload(new CodeRes(CodeConst.OK, "发送成功"));
        }
    }

    @Override
    public void post(ServerContext serverContext) {
        sessionManager = serverContext.getBeanFactoryContext().getBean(SessionManager.class);
    }
}
