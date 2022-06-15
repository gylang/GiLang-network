package com.gilang.network.layer.app.socket;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.ReflectUtil;
import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.context.ServerContext;
import com.gilang.network.exception.MultiCommandException;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.layer.app.socket.SocketAppLayerInvokerAdapter;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import javafx.util.converter.ByteStringConverter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gylang
 * data 2022/6/15
 */
@ChannelHandler.Sharable
public class SimpleSocketAppLayerInvokerAdapter extends SocketAppLayerInvokerAdapter implements AfterNetWorkContextInitialized {

    private Map<Byte, Type> cmdParamTypeMap = new HashMap<>();

    @Override
    public Type resolveInvokeParamType(Byte data) {
        return null;
    }

    @Override
    public Object toObject(byte[] bs, Type type) {
        return null;
    }

    @Override
    public byte[] toByte(Object object) {
        return new byte[0];
    }

    @Override
    public void dispatch(SocketDataPackage<?> dataPackage) {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SocketDataPackage<?> dataPackage) throws Exception {

    }

    @Override
    public void post(ServerContext serverContext) {

        // 解析获取所有的业务类型对应的
        List<MessageAction> messageActionList = serverContext.getBeanFactoryContext().getBeanList(MessageAction.class);
        for (MessageAction messageAction : messageActionList) {
            ActionType actionType = AnnotationUtil.getAnnotation(messageAction.getClass(), ActionType.class);
            if (null != actionType) {
                // 获取命令类型
                if (cmdParamTypeMap.containsKey(actionType.value())) {
                    throw new MultiCommandException(Byte.toString(actionType.value()));
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(Byte.toString((byte) 0b11110101));
    }
}
