package com.gilang.network.example.init;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.ServerContext;
import com.gilang.network.example.action.http.CorsApi;
import com.gilang.network.example.action.http.ExceptionApi;
import com.gilang.network.example.action.socket.LoginAction;
import com.gilang.network.example.action.socket.OnlineUserListAction;
import com.gilang.network.example.action.socket.RegisterAction;
import com.gilang.network.example.action.socket.SendMessageAction;
import com.gilang.network.example.action.http.HelloApi;
import com.gilang.network.example.session.ChatRoomSession;

/**
 * @author gylang
 * data 2022/7/10
 */
public class ActionBeanLoader implements BeanLoader {

    @Override
    public void scan(ServerContext serverContext) {
        BeanFactoryContext beanFactoryContext = serverContext.getBeanFactoryContext();

        beanFactoryContext.register(ChatRoomSession.class.getName(), new ChatRoomSession());


        beanFactoryContext.register(LoginAction.class.getName(), new LoginAction());
        beanFactoryContext.register(SendMessageAction.class.getName(), new SendMessageAction());
        beanFactoryContext.register(OnlineUserListAction.class.getName(), new OnlineUserListAction());
        beanFactoryContext.register(RegisterAction.class.getName(), new RegisterAction());
        beanFactoryContext.register(ExceptionApi.class.getName(), new ExceptionApi());


        beanFactoryContext.register(HelloApi.class.getName(), new HelloApi());
        beanFactoryContext.register(CorsApi.class.getName(), new CorsApi());
    }
}
