package com.gilang.network.example.init;

import com.gilang.common.context.BeanLoadWrapper;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.ServerContext;
import com.gilang.network.example.action.http.CorsApi;
import com.gilang.network.example.action.http.ExceptionApi;
import com.gilang.network.example.action.http.HelloApi;
import com.gilang.network.example.action.socket.LoginAction;
import com.gilang.network.example.action.socket.OnlineUserListAction;
import com.gilang.network.example.action.socket.RegisterAction;
import com.gilang.network.example.action.socket.SendMessageAction;
import com.gilang.network.example.session.ChatRoomSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gylang
 * data 2022/7/10
 */
public class ActionBeanLoader implements BeanLoader {

    @Override
    public List<BeanLoadWrapper<?>> scan(ServerContext serverContext) {
        List<BeanLoadWrapper<?>> supplierList = new ArrayList<>();
        supplierList.add(new BeanLoadWrapper<>(ChatRoomSession.class, ChatRoomSession::new));


        supplierList.add(new BeanLoadWrapper<>(LoginAction.class, LoginAction::new));
        supplierList.add(new BeanLoadWrapper<>(SendMessageAction.class, SendMessageAction::new));
        supplierList.add(new BeanLoadWrapper<>(OnlineUserListAction.class, OnlineUserListAction::new));
        supplierList.add(new BeanLoadWrapper<>(RegisterAction.class, RegisterAction::new));
        supplierList.add(new BeanLoadWrapper<>(ExceptionApi.class, ExceptionApi::new));


        supplierList.add(new BeanLoadWrapper<>(HelloApi.class, HelloApi::new));
        supplierList.add(new BeanLoadWrapper<>(CorsApi.class, CorsApi::new));
        return supplierList;
    }
}
