package com.gilang.network.example.action.socket;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.gilang.common.annotation.SocketActionType;
import com.gilang.common.domian.socket.SocketDataPackage;
import com.gilang.network.context.ServerContext;
import com.gilang.network.socket.context.SocketSessionContext;
import com.gilang.network.converter.PackageConverter;
import com.gilang.network.example.constant.CodeConst;
import com.gilang.network.example.domain.db.User;
import com.gilang.network.example.domain.payload.req.LoginReq;
import com.gilang.network.example.domain.payload.res.CodeRes;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.socket.router.MessageAction;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author gylang
 * data 2022/7/17
 */
@SocketActionType(2)
public class LoginAction implements MessageAction<LoginReq>, AfterNetWorkContextInitialized {


    @Override
    public void doAction(SocketDataPackage<LoginReq> dataPackage, SocketSessionContext socketSessionContext) {

        LoginReq payload = dataPackage.getPayload();
        CompletableFuture.runAsync(() -> {
            try {
                List<Entity> userList = Db.use().query("select from gn_user where username = ?", payload.getUsername());
                SocketDataPackage<CodeRes> callBack = PackageConverter.copyBase(dataPackage);
                if (CollUtil.isNotEmpty(userList)) {
                    Entity entity = userList.get(0);
                    if (entity.getStr("password").equals(DigestUtil.md5Hex(payload.getPassword() + entity.getStr("salt")))) {
                        // 登录成功
                        User user = new User();
                        user.setId(entity.getLong("id"));
                        user.setNickname(entity.getStr("username"));
                        user.setNickname(entity.getStr("nickname"));
                        user.setNickname(entity.getStr("nickname"));
                        socketSessionContext.setAttr(User.class.getName(), user);
                        callBack.setPayload(new CodeRes(CodeConst.OK, "登录成功"));
                        socketSessionContext.write(callBack);
                        return;
                    }
                }
                callBack.setPayload(new CodeRes(CodeConst.FAIL, "登录失败,账号密码错误"));
                socketSessionContext.write(callBack);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    @Override
    public void post(ServerContext serverContext) {
    }
}
