package com.gilang.network.layer.app.http;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.common.domian.http.HttpServiceWrapper;
import com.gilang.common.domian.http.UrlSearchTree;
import com.gilang.common.enums.RequestMethod;
import com.gilang.network.context.HttpSessionContext;
import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.http.exception.Http404Exception;
import com.gilang.network.http.exception.HttpInterceptPreException;
import com.gilang.network.http.handler.HttpExceptionHandlerManager;
import com.gilang.network.http.exception.HttpRenderException;
import com.gilang.network.http.intercept.HttpIntercept;
import com.gilang.network.layer.show.http.HttpTranslator;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author gylang
 * data 2022/8/8
 */
@Slf4j
public class SimpleHttpAppLayerInvokerAdapterImpl implements HttpAppLayerInvokerAdapter, AfterNetWorkContextInitialized {

    private Map<String, UrlSearchTree<HttpServiceWrapper>> urlSearchTreePool;

    private Map<String, HttpTranslator> httpTranslatorPool;

    private ResponseRender responseRender;

    private HttpExceptionHandlerManager httpExceptionHandlerManager;

    private Map<String, List<HttpIntercept>> httpInterceptPool;

    @Override
    public void route(HttpDataRequest<?> httpDataRequest, HttpSessionContext context) {
        HttpDataResponse httpDataResponse = new HttpDataResponse();
        String uri = httpDataRequest.getUri();
        List<HttpIntercept> httpIntercepts = findIntercept(uri);
        HttpServiceWrapper serviceWrapper = null;
        try {
            // 地址查询
            UrlSearchTree<HttpServiceWrapper> searchTree = urlSearchTreePool.get(httpDataRequest.getMethod().toUpperCase());
            if (null == searchTree) {
                throw new Http404Exception(httpDataRequest);
            }
            // 寻找业务执行
            UrlSearchTree.PathLeaf<HttpServiceWrapper> pathLeaf = searchTree.findPathLeaf(uri);
            if (null == pathLeaf) {
                throw new Http404Exception(httpDataRequest);
            }
            // 设置路径参数
            setVariable(httpDataRequest, uri, pathLeaf);
            // 执行拦截器
            serviceWrapper = pathLeaf.getPayload();
            if (CollUtil.isNotEmpty(httpIntercepts)) {
                for (HttpIntercept httpIntercept : httpIntercepts) {
                    boolean b = httpIntercept.preHandle(httpDataRequest, httpDataResponse, serviceWrapper);
                    if (!b) {
                        // 请求被拦截
                        throw new HttpInterceptPreException();
                    }
                }
            }
            serviceWrapper.getHttpInvokeHelper().doAction(httpDataRequest, httpDataResponse);
            // 后置拦截器
            for (HttpIntercept httpIntercept : httpIntercepts) {
                httpIntercept.postHandle(httpDataRequest, httpDataResponse, serviceWrapper);
            }
            httpDataResponse.setStatus(httpDataResponse.getStatus() != null ? httpDataResponse.getStatus() : 200);
        } catch (Exception e) {
            httpExceptionHandlerManager.handle(httpDataRequest, httpDataResponse, e);
        }
        // 写入完成之后的拦截器
        try {
            write(httpDataRequest, httpDataResponse, context);
            for (HttpIntercept httpIntercept : httpIntercepts) {
                try {
                    httpIntercept.afterCompletion(httpDataRequest, httpDataResponse, serviceWrapper, null);
                } catch (Exception e) {
                    log.error("httpIntercept.afterCompletion", e);
                }
            }
        } catch (Exception e) {
            // 写入异常
            for (HttpIntercept httpIntercept : httpIntercepts) {
                try {
                    httpIntercept.afterCompletion(httpDataRequest, httpDataResponse, serviceWrapper, e);
                } catch (Exception e1) {
                    log.error("httpIntercept.afterCompletion", e1);
                }
            }
            httpExceptionHandlerManager.handle(httpDataRequest, httpDataResponse, new HttpRenderException(e));
            write(httpDataRequest, httpDataResponse, context);
        }

    }

    private List<HttpIntercept> findIntercept(String uri) {
        List<HttpIntercept> findIntercepts = new ArrayList<>();
        for (Map.Entry<String, List<HttpIntercept>> entry : httpInterceptPool.entrySet()) {
            if (uri.contains(entry.getKey())) {
                findIntercepts.addAll(entry.getValue());
            }
        }
        return findIntercepts;
    }

    private void setVariable(HttpDataRequest<?> httpDataRequest, String uri, UrlSearchTree.PathLeaf<HttpServiceWrapper> pathLeaf) {
        Map<Integer, String> variable = pathLeaf.getVariable();
        List<String> parts = StrUtil.split(uri, '/', true, true);
        for (Map.Entry<Integer, String> entry : variable.entrySet()) {
            httpDataRequest.getPathVariables().put(entry.getValue(), parts.get(entry.getKey()));
        }
    }


    @Override
    public <T> Class<T> resolveInvokeParamType(String method, String uri) {
        return null;
    }

    @Override
    public Object toObject(String contentType, byte[] bs, Type type) {
        return null;
    }


    @Override
    public byte[] toByte(HttpDataResponse response) {
        return httpTranslatorPool.get(response.contentType()).toByte(response.getPayload());
    }


    private void write(HttpDataRequest<?> httpDataRequest, HttpDataResponse httpDataResponse, HttpSessionContext context) {
        responseRender.render(httpDataRequest, httpDataResponse);
        context.write(httpDataResponse);

    }

    @Override
    public void post(ServerContext serverContext) {
        responseRender = serverContext.getBeanFactoryContext().getPrimaryBean(ResponseRender.class);
        httpExceptionHandlerManager = serverContext.getBeanFactoryContext().getPrimaryBean(HttpExceptionHandlerManager.class);
        registerUrlSearch(serverContext);
        registerTranslator(serverContext);
        registerIntercept(serverContext);
    }

    private void registerIntercept(ServerContext serverContext) {

        httpInterceptPool = new HashMap<>();
        List<HttpIntercept> beanList = serverContext.getBeanFactoryContext().getBeanList(HttpIntercept.class);
        for (HttpIntercept httpIntercept : beanList) {
            for (String path : httpIntercept.interceptPath()) {
                List<HttpIntercept> httpIntercepts = httpInterceptPool.computeIfAbsent(path, k -> new LinkedList<>());
                httpIntercepts.add(httpIntercept);
            }
        }
    }

    private void registerTranslator(ServerContext serverContext) {
        List<HttpTranslator> beanList = serverContext.getBeanFactoryContext().getBeanList(HttpTranslator.class);
        this.httpTranslatorPool = new HashMap<>();
        for (HttpTranslator httpTranslator : beanList) {
            String[] contentTypes = httpTranslator.supportContentTypes();
            for (String contentType : contentTypes) {

                this.httpTranslatorPool.put(contentType.toLowerCase(), httpTranslator);
            }
        }
    }


    private void registerUrlSearch(ServerContext serverContext) {
        List<HttpInvokerScanner> httpInvokerScannerList = serverContext.getBeanFactoryContext().getBeanList(HttpInvokerScanner.class);
        this.urlSearchTreePool = new HashMap<>();
        if (CollUtil.isNotEmpty(httpInvokerScannerList)) {
            for (HttpInvokerScanner httpInvokerScanner : httpInvokerScannerList) {
                // 扫描获取服务
                List<HttpServiceWrapper> httpServiceWrappers = httpInvokerScanner.scan(serverContext);
                for (HttpServiceWrapper httpServiceWrapper : httpServiceWrappers) {
                    // 生成rest uri树
                    RequestMethod[] methods = httpServiceWrapper.getMethods();
                    if (ArrayUtil.isEmpty(methods)) {
                        // 没有指定方法就按默认所有方法
                        methods = RequestMethod.values();
                    }
                    for (RequestMethod method : methods) {
                        // 注册调用树
                        UrlSearchTree<HttpServiceWrapper> urlSearchTree = urlSearchTreePool.computeIfAbsent(method.name(), k -> new UrlSearchTree<>());
                        urlSearchTree.register(httpServiceWrapper.getPath(), httpServiceWrapper);
                    }
                }
            }
        }
    }


}
