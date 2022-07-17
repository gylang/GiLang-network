package com.gilang.network.example.action;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.context.ServerContext;
import com.gilang.network.context.SessionContext;
import com.gilang.network.converter.PackageConverter;
import com.gilang.network.example.constant.CodeConst;
import com.gilang.network.example.domain.UserInfo;
import com.gilang.network.example.domain.payload.res.CodeRes;
import com.gilang.network.example.session.ChatRoomSession;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.layer.app.socket.ActionType;
import com.gilang.network.layer.app.socket.MessageAction;

import java.util.Map;

/**
 * @author gylang
 * data 2022/7/17
 */
@ActionType(2)
public class JoinAction implements MessageAction<UserInfo>, AfterNetWorkContextInitialized {

    private ChatRoomSession chatRoomSession;

    @Override
    public void doAction(SocketDataPackage<UserInfo> dataPackage, SessionContext sessionContext) {

        Map<Long, UserInfo> aDefault = chatRoomSession.getRoomUsers("DEFAULT");
        UserInfo userInfo = dataPackage.getPayload();
        aDefault.put(userInfo.getContactId(), userInfo);

        // 通知成功
        SocketDataPackage<CodeRes> message = PackageConverter.copyBase(dataPackage);
        if (dataPackage.isOneSend()) {
            message.setPayload(new CodeRes(CodeConst.OK, "加入群聊成功"));
        }
    }

    @Override
    public void post(ServerContext serverContext) {
        chatRoomSession = serverContext.getBeanFactoryContext().getBean(ChatRoomSession.class);
    }
}
