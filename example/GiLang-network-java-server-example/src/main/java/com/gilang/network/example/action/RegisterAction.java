package com.gilang.network.example.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.context.ServerContext;
import com.gilang.network.context.SessionContext;
import com.gilang.network.converter.PackageConverter;
import com.gilang.network.example.constant.CodeConst;
import com.gilang.network.example.domain.db.User;
import com.gilang.network.example.domain.payload.req.LoginReq;
import com.gilang.network.example.domain.payload.req.RegisterReq;
import com.gilang.network.example.domain.payload.res.CodeRes;
import com.gilang.network.example.session.ChatRoomSession;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.layer.app.socket.ActionType;
import com.gilang.network.layer.app.socket.MessageAction;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author gylang
 * data 2022/7/17
 */
@ActionType(4)
public class RegisterAction implements MessageAction<User>, AfterNetWorkContextInitialized {


    @Override
    public void doAction(SocketDataPackage<User> dataPackage, SessionContext sessionContext) {

        User payload = dataPackage.getPayload();
        CompletableFuture.runAsync(() -> {
            SocketDataPackage<CodeRes> callBack = PackageConverter.copyBase(dataPackage);
            // 注册
            Entity record = Entity.create();
            record.set("username", payload.getUsername());
            String salt = IdUtil.fastUUID();
            record.set("nickname", salt);
            record.set("password", DigestUtil.md5Hex(payload.getPassword() + salt));
            record.set("nickname", payload.getNickname());
            try {
                Db.use().insertForGeneratedKey(record);
                payload.setPassword(null);
                callBack.setPayload(new CodeRes(CodeConst.OK, "注册成功"));
                sessionContext.setAttr(User.class.getName(), payload);

            } catch (SQLException throwables) {
                callBack.setPayload(new CodeRes(CodeConst.FAIL, "账号或已存在"));
            }
            sessionContext.write(callBack);
        });
    }

    @Override
    public void post(ServerContext serverContext) {
    }
}
