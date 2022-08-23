package com.gilang.network.example.action.socket;

import cn.hutool.core.collection.CollUtil;
import com.gilang.common.domian.socket.SocketDataPackage;
import com.gilang.network.context.ServerContext;
import com.gilang.network.context.SocketSessionContext;
import com.gilang.network.converter.PackageConverter;
import com.gilang.network.example.domain.db.User;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.common.annotation.SocketActionType;
import com.gilang.network.layer.app.socket.MessageAction;
import com.gilang.network.layer.session.SocketSessionManager;

import java.util.List;

/**
 * @author gylang
 * data 2022/7/17
 */
@SocketActionType(5)
public class OnlineUserListAction implements MessageAction<Void>, AfterNetWorkContextInitialized {

    private SocketSessionManager socketSessionManager;

    @Override
    public void doAction(SocketDataPackage<Void> dataPackage, SocketSessionContext socketSessionContext) {

        List<SocketSessionContext> socketSessionContexts = socketSessionManager.querySession(s -> s.hasAttr(User.class.getName()));
        List<User> objects = CollUtil.map(socketSessionContexts, s -> s.attr(User.class.getName()), true);
        SocketDataPackage<List<User>> callBack = PackageConverter.copyBase(dataPackage);
        callBack.setPayload(objects);
        socketSessionContext.write(callBack);
    }

    @Override
    public void post(ServerContext serverContext) {
        socketSessionManager = serverContext.getSocketSessionManager();
    }
}
