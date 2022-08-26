package com.gilang.network.http.load;

import com.gilang.common.annotation.RequestMapping;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.common.domian.http.HttpInvokeHelper;
import com.gilang.common.domian.http.HttpServiceWrapper;
import com.gilang.common.util.ClassUtils;
import com.gilang.network.context.ServerContext;
import com.gilang.network.http.router.HttpAction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gylang
 * data 2022/8/10
 */
public class HttpActionRegister implements HttpInvokerScanner {



    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<HttpServiceWrapper> scan(ServerContext serverContext) {
        List<HttpAction> beanList = serverContext.getBeanFactoryContext().getBeanList(HttpAction.class);
        List<HttpServiceWrapper> serviceWrappers = new ArrayList<>();
        for (HttpAction httpAction : beanList) {
            Class<?> userClass = ClassUtils.getUserClass(httpAction.getClass());
            RequestMapping annotation = ClassUtils.recurseFindAnnotation(userClass, RequestMapping.class);
            if (null != annotation) {
                HttpServiceWrapper serviceWrapper = HttpServiceWrapper.builder()
                        .path(annotation.path())
                        .methods(annotation.method())
                        .contentType(annotation.contentType())
                        .type(ClassUtils.getTypeArgument(userClass, 0))
                        .httpInvokeHelper(new HttpInvokeHelper() {
                            @Override
                            public <T> Object doAction(HttpDataRequest<T> request, HttpDataResponse response) {
                                httpAction.doAction(request, response);
                                return response;
                            }
                        }).build();
                serviceWrappers.add(serviceWrapper);
            }
        }

        return serviceWrappers;
    }

}
