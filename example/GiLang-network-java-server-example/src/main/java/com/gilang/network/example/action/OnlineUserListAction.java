package com.gilang.network.example.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.context.ServerContext;
import com.gilang.network.context.SessionContext;
import com.gilang.network.converter.PackageConverter;
import com.gilang.network.example.constant.CodeConst;
import com.gilang.network.example.domain.db.User;
import com.gilang.network.example.domain.payload.req.LoginReq;
import com.gilang.network.example.domain.payload.res.CodeRes;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.layer.app.socket.ActionType;
import com.gilang.network.layer.app.socket.MessageAction;
import com.gilang.network.layer.session.SessionManager;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author gylang
 * data 2022/7/17
 */
@ActionType(5)
public class OnlineUserListAction implements MessageAction<Void>, AfterNetWorkContextInitialized {

    private SessionManager sessionManager;

    @Override
    public void doAction(SocketDataPackage<Void> dataPackage, SessionContext sessionContext) {

        List<SessionContext> sessionContexts = sessionManager.querySession(s -> s.hasAttr(User.class.getName()));
        List<User> objects = CollUtil.map(sessionContexts, s -> s.attr(User.class.getName()), true);
        SocketDataPackage<List<User>> callBack = PackageConverter.copyBase(dataPackage);
        callBack.setPayload(objects);
        sessionContext.write(callBack);
    }

    @Override
    public void post(ServerContext serverContext) {
        sessionManager = serverContext.getSessionManager();
    }
}
