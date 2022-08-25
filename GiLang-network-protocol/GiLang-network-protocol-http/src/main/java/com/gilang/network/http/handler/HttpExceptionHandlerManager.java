package com.gilang.network.http.handler;

import com.gilang.common.annotation.ExceptionType;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.common.util.ClassUtils;
import com.gilang.common.util.OrderUtil;
import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gylang
 * data 2022/8/17
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class HttpExceptionHandlerManager implements AfterNetWorkContextInitialized {

    private Map<Class<?>, HttpExceptionHandler> httpExceptionHandlerPool;


    public void handle(HttpDataRequest<?> request, HttpDataResponse response, Exception e) {
        response.setStatus(500);
        Class<?> aClass = e.getClass();
        while (true) {
            HttpExceptionHandler httpExceptionHandler = httpExceptionHandlerPool.get(aClass);
            if (null == aClass) {
                break;
            }
            if (null != httpExceptionHandler) {
                httpExceptionHandler.handler(request, response, e);
                return;
            }
            aClass = aClass.getSuperclass();
        }
        // 如果有异常处理已经return了, 执行倒这里证明没有一个可以捕获的异常处理类
    }

    @Override
    public void post(ServerContext serverContext) {

        httpExceptionHandlerPool = new HashMap<>();
        List<HttpExceptionHandler> beanList = serverContext.getBeanFactoryContext().getBeanList(HttpExceptionHandler.class);
        beanList.removeIf(b -> null == ClassUtils.recurseFindAnnotation(b.getClass(), ExceptionType.class));
        Map<Class<?>, List<HttpExceptionHandler>> groupByHandType = beanList.stream()
                .collect(Collectors.groupingBy(v -> ClassUtils.recurseFindAnnotation(v.getClass(), ExceptionType.class).value()));
        for (Map.Entry<Class<?>, List<HttpExceptionHandler>> entry : groupByHandType.entrySet()) {

            if (entry.getValue().size() == 1) {
                httpExceptionHandlerPool.put(entry.getKey(), entry.getValue().get(0));
            } else {
                httpExceptionHandlerPool.put(entry.getKey(), OrderUtil.minOrderByAnnotation(entry.getValue(), Integer.MAX_VALUE));
            }
        }
    }
}
