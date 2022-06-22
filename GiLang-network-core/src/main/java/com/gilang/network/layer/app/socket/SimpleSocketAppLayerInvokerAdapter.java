package com.gilang.network.layer.app.socket;

import cn.hutool.core.annotation.AnnotationUtil;
import com.gilang.common.context.BeanFactoryContext;
import com.gilang.common.domian.SocketDataPackage;
import com.gilang.common.util.ClassUtils;
import com.gilang.network.context.ServerContext;
import com.gilang.network.context.SessionContext;
import com.gilang.network.exception.MultiCommandException;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.layer.show.PackageTranslator;
import com.gilang.network.layer.show.TranslatorType;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gylang
 * data 2022/6/15
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SimpleSocketAppLayerInvokerAdapter extends SocketAppLayerInvokerAdapter implements AfterNetWorkContextInitialized {

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
        if (null == packageTranslator) {
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
    public void route(SocketDataPackage<?> dataPackage, SessionContext sessionContext) {

        byte cmd = dataPackage.getCmd();
        MessageAction messageAction = cmdActionMap.get(cmd);
        if (null == messageAction) {
            return;
        }
        if (null != actionHookHolder) {
            actionHookHolder.doActionBefore(dataPackage, sessionContext, messageAction);
            messageAction.doAction(dataPackage, sessionContext);
            actionHookHolder.doActionAfter(dataPackage, sessionContext, messageAction);
        } else {
            messageAction.doAction(dataPackage, sessionContext);
        }
    }


    @Override
    public void post(ServerContext serverContext) {

        // 解析获取所有的业务类型对应的
        BeanFactoryContext beanFactoryContext = serverContext.getBeanFactoryContext();
        List<MessageAction> messageActionList = beanFactoryContext.getBeanList(MessageAction.class);
        for (MessageAction<?> messageAction : messageActionList) {
            ActionType actionType = AnnotationUtil.getAnnotation(messageAction.getClass(), ActionType.class);
            if (null != actionType) {
                // 获取命令类型
                if (cmdParamTypeMap.containsKey(actionType.value())) {
                    throw new MultiCommandException(Byte.toString(actionType.value()));
                }
                Class<?> userClass = ClassUtils.getUserClass(messageAction.getClass());
                cmdParamTypeMap.put(actionType.value(), ClassUtils.getTypeArgument(userClass));
                cmdActionMap.put(actionType.value(), messageAction);
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
