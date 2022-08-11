package com.gilang.network.layer.app.http;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.gilang.common.constant.HttpHeader;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.common.domian.http.HttpServiceWrapper;
import com.gilang.common.domian.http.UrlSearchTree;
import com.gilang.common.enums.RequestMethod;
import com.gilang.network.context.HttpSessionContext;
import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.layer.show.http.HttpTranslator;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gylang
 * data 2022/8/8
 */
public class SimpleHttpAppLayerInvokerAdapterImpl implements HttpAppLayerInvokerAdapter, AfterNetWorkContextInitialized {

    private Map<String, UrlSearchTree<HttpServiceWrapper>> urlSearchTreePool;

    private Map<String, HttpTranslator> httpTranslatorPool;

    private ResponseRender responseRender;

    @Override
    public void route(HttpDataRequest<?> httpDataRequest, HttpSessionContext context) {
        HttpDataResponse httpDataResponse = new HttpDataResponse();
        String uri = httpDataRequest.getUri();
        UrlSearchTree<HttpServiceWrapper> searchTree = urlSearchTreePool.get(httpDataRequest.getMethod().toUpperCase());
        if (null == searchTree) {
            render404(httpDataRequest, httpDataResponse, context);
            return;
        }
        UrlSearchTree.PathLeaf<HttpServiceWrapper> pathLeaf = searchTree.findPathLeaf(uri);
        if (null == pathLeaf) {
            // todo
            render404(httpDataRequest, httpDataResponse, context);
            return;
        }
        Map<Integer, String> variable = pathLeaf.getVariable();
        List<String> parts = StrUtil.split(uri, '/', true, true);
        for (Map.Entry<Integer, String> entry : variable.entrySet()) {
            httpDataRequest.getPathVariables().put(entry.getValue(), parts.get(entry.getKey()));
        }
        pathLeaf.getPayload().getHttpInvokeHelper().doAction(httpDataRequest, httpDataResponse);
        httpDataResponse.setStatus(200);
        write(httpDataRequest, httpDataResponse, context);
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

    @Override
    public void post(ServerContext serverContext) {
        registerUrlSearch(serverContext);
        registerTranslator(serverContext);
        responseRender = serverContext.getBeanFactoryContext().getPrimaryBean(ResponseRender.class);
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


    protected void render404(HttpDataRequest<?> httpDataRequest, HttpDataResponse httpDataResponse, HttpSessionContext context) {
        httpDataResponse.setStatus(404);

        write(httpDataRequest, httpDataResponse, context);
    }

    private void write(HttpDataRequest<?> httpDataRequest, HttpDataResponse httpDataResponse, HttpSessionContext context) {
        responseRender.render(httpDataRequest, httpDataResponse);
        context.write(httpDataResponse);

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
