package com.gilang.network.event;

import com.gilang.common.util.ClassUtils;
import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author gylang
 * data 2022/7/17
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class EventContext implements AfterNetWorkContextInitialized {

    /** 事件监听器 key为事件监听器, value为监听事件类型 */
    private final Map<EventListener<?>, Class<?>> eventListenerClassMap = new HashMap<>();

    private static final Executor executor = Executors.newCachedThreadPool();


    /**
     * 发送通知事件
     *
     * @param event 事件源, 通过此参数判断哪个能够接收改事件
     */
    public void sendEvent(Event event) {
        eventListenerClassMap.forEach((eventListener, aClass) -> {
            if (aClass.isInstance(event)) {
                ((EventListener<Event>) eventListener).call(event);
            }
        });
    }

    /**
     * 异步发送通知事件
     *
     * @param event 事件源, 通过此参数判断哪个能够接收改事件
     */
    public <T extends Event> void asyncSendEvent(T event) {
        // TODO 线程池如何控制, 当前直接通过newCachedThreadPool
        executor.execute(() -> sendEvent(event));
    }

    @Override
    public void post(ServerContext serverContext) {
        List<EventListener> listeners = serverContext.getBeanFactoryContext().getBeanList(EventListener.class);
        for (EventListener listener : listeners) {
            eventListenerClassMap.put(listener, ClassUtils.getTypeArgument(ClassUtils.getUserClass(listener.getClass())));
        }
    }
}
