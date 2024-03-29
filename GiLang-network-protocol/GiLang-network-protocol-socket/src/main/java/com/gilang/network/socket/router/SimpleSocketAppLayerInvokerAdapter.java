package com.gilang.network.socket.router;

import cn.hutool.core.annotation.AnnotationUtil;
import com.gilang.common.annotation.SocketActionType;
import com.gilang.common.annotation.TranslatorType;
import com.gilang.common.context.BeanFactoryContext;
import com.gilang.common.domian.socket.SocketDataPackage;
import com.gilang.common.util.ClassUtils;
import com.gilang.network.context.ServerContext;
import com.gilang.network.exception.MultiCommandException;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.socket.context.SocketDoActionHookHolder;
import com.gilang.network.socket.context.SocketSessionContext;
import com.gilang.network.translator.PackageTranslator;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gylang
 * data 2022/6/15
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SimpleSocketAppLayerInvokerAdapter extends SocketSocketAppLayerInvokerAdapter implements AfterNetWorkContextInitialized {

    private final Map<Byte, Type> cmdParamTypeMap = new HashMap<>();
    private final Map<Byte, MessageAction<?>> cmdActionMap = new HashMap<>();
    private final Map<Byte, PackageTranslator> packageTranslatorMap = new HashMap<>();
    private SocketDoActionHookHolder actionHookHolder;

    @Override
    public Type resolveInvokeParamType(Byte data) {
        return cmdParamTypeMap.get(data);
    }

    @Override
    public Object toObject(byte protocol, byte[] bs, Type type) {
        PackageTranslator packageTranslator = packageTranslatorMap.get(protocol);
        if (null == type || null == packageTranslator) {
            return null;
        }
        return packageTranslator.toObject(bs, type);
    }

    @Override
    public byte[] toByte(byte protocol, Object object) {
        PackageTranslator packageTranslator = packageTranslatorMap.get(protocol);
        if (null == packageTranslator) {
            return new byte[0];
        }

        return packageTranslator.toByte(object);
    }

    @Override
    public void route(SocketDataPackage<?> dataPackage, SocketSessionContext socketSessionContext) {

        byte cmd = dataPackage.getCmd();
        MessageAction messageAction = cmdActionMap.get(cmd);
        if (null == messageAction) {
            return;
        }
        if (null != actionHookHolder) {
            actionHookHolder.doActionBefore(dataPackage, socketSessionContext, messageAction);
            messageAction.doAction(dataPackage, socketSessionContext);
            actionHookHolder.doActionAfter(dataPackage, socketSessionContext, messageAction);
        } else {
            messageAction.doAction(dataPackage, socketSessionContext);
        }
    }


    @Override
    public void post(ServerContext serverContext) {

        // 解析获取所有的业务类型对应的
        BeanFactoryContext beanFactoryContext = serverContext.getBeanFactoryContext();
        List<MessageAction> messageActionList = beanFactoryContext.getBeanList(MessageAction.class);
        for (MessageAction<?> messageAction : messageActionList) {
            SocketActionType socketActionType = AnnotationUtil.getAnnotation(messageAction.getClass(), SocketActionType.class);
            if (null != socketActionType) {
                // 获取命令类型
                if (cmdParamTypeMap.containsKey(socketActionType.value())) {
                    throw new MultiCommandException(Byte.toString(socketActionType.value()));
                }
                Class<?> userClass = ClassUtils.getUserClass(messageAction.getClass());
                cmdParamTypeMap.put(socketActionType.value(), ClassUtils.getTypeArgument(userClass));
                cmdActionMap.put(socketActionType.value(), messageAction);
            }
        }
        // 数据编码解码翻译
        List<PackageTranslator> packageTranslatorList = beanFactoryContext.getBeanList(PackageTranslator.class);
        for (PackageTranslator packageTranslator : packageTranslatorList) {
            TranslatorType translatorType = AnnotationUtil.getAnnotation(packageTranslator.getClass(), TranslatorType.class);
            if (null != translatorType) {
                // 获取命令类型
                packageTranslatorMap.put(translatorType.value(), packageTranslator);
            }
        }
        actionHookHolder = beanFactoryContext.getPrimaryBean(SocketDoActionHookHolder.class);

    }


}
