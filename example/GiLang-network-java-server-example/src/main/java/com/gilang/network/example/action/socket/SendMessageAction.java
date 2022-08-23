package com.gilang.network.example.action.socket;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.gilang.common.annotation.SocketActionType;
import com.gilang.common.domian.socket.SocketDataPackage;
import com.gilang.network.context.ServerContext;
import com.gilang.network.context.SocketSessionContext;
import com.gilang.network.converter.PackageConverter;
import com.gilang.network.example.constant.CodeConst;
import com.gilang.network.example.domain.db.User;
import com.gilang.network.example.domain.payload.res.CodeRes;
import com.gilang.network.example.domain.payload.rqs.MessageRqs;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.layer.app.socket.MessageAction;
import com.gilang.network.layer.session.SocketSessionManager;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

/**
 * @author gylang
 * data 2022/7/17
 */
@SocketActionType(3)
public class SendMessageAction implements MessageAction<MessageRqs>, AfterNetWorkContextInitialized {

    private SocketSessionManager socketSessionManager;

    @Override
    public void doAction(SocketDataPackage<MessageRqs> dataPackage, SocketSessionContext socketSessionContext) {

        MessageRqs messageRqs = dataPackage.getPayload();

        SocketSessionContext sessionByAliasKey = socketSessionManager.getSessionByAliasKey(messageRqs.getReceive());
        SocketDataPackage<CodeRes> message = PackageConverter.copyBase(dataPackage);
        User current = socketSessionContext.attr(User.class.getName());
        if (null == current) {
            message.setPayload(new CodeRes(CodeConst.FAIL, "未授权无法发送信息"));
            socketSessionContext.write(message);
            return;
        }
        // 写入消息
        CompletableFuture.runAsync(() -> {
            Entity privateChat = Entity.create("history_private_chat")
                    .set("msg_id", dataPackage.getMsgId())
                    .set("send_id", current.getId())
                    .set("receive_id", messageRqs.getReceive())
                    .set("message", messageRqs.getContent())
                    .set("time_stamp", messageRqs.getTimestamp());
            try {
                Db.use().insertForGeneratedKey(privateChat);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        sessionByAliasKey.write(message);
        // 通知成功
        if (dataPackage.isOneSend()) {
            message.setPayload(new CodeRes(CodeConst.OK, "发送成功"));
        }
    }

    @Override
    public void post(ServerContext serverContext) {
        socketSessionManager = serverContext.getBeanFactoryContext().getBean(SocketSessionManager.class);
    }
}
