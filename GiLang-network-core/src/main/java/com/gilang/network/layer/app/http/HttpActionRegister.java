package com.gilang.network.layer.app.http;

import com.gilang.common.annotation.RequestMapping;
import com.gilang.common.context.BeanFactoryContext;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.common.domian.http.HttpInvokeHelper;
import com.gilang.common.domian.http.HttpServiceWrapper;
import com.gilang.common.util.ClassUtils;
import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;

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
            RequestMapping annotation = ClassUtils.recurseFindAnnotation(ClassUtils.getUserClass(httpAction.getClass()), RequestMapping.class);
            if (null != annotation) {
                HttpServiceWrapper httpServiceWrapper = new HttpServiceWrapper();
                httpServiceWrapper.setPath(annotation.path());
                httpServiceWrapper.setMethods(annotation.method());
                httpServiceWrapper.setContentType(annotation.contentType());
                httpServiceWrapper.setHttpInvokeHelper(new HttpInvokeHelper() {
                    @Override
                    public <T> Object doAction(HttpDataRequest<T> request, HttpDataResponse response) {
                        httpAction.doAction(request, response);
                        return response;
                    }
                });
                serviceWrappers.add(httpServiceWrapper);
            }
        }

        return serviceWrappers;
    }

}
