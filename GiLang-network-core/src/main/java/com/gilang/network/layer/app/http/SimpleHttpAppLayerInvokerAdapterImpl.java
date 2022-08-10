package com.gilang.network.layer.app.http;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.common.domian.http.HttpServiceWrapper;
import com.gilang.common.domian.http.UrlSearchTree;
import com.gilang.common.enums.RequestMethod;
import com.gilang.common.exception.BaseException;
import com.gilang.network.context.ServerContext;
import com.gilang.network.context.SocketSessionContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gylang
 * data 2022/8/8
 */
public class SimpleHttpAppLayerInvokerAdapterImpl implements HttpAppLayerInvokerAdapter<HttpServiceWrapper>, AfterNetWorkContextInitialized {

    private Map<String, UrlSearchTree<HttpServiceWrapper>> urlSearchTreePool;


    @Override
    public void route(HttpDataRequest<?> dataPackage, SocketSessionContext socketSessionContext) {
        String uri = dataPackage.getUri();
        UrlSearchTree<HttpServiceWrapper> searchTree = urlSearchTreePool.get(dataPackage.getMethod());
        if (null == searchTree) {
            // todo
            throw new BaseException("404", "资源不存在");
        }
        UrlSearchTree.PathLeaf<HttpServiceWrapper> pathLeaf = searchTree.findPathLeaf(uri);
        if (null == pathLeaf) {
            // todo
            throw new BaseException("404", "资源不存在");
        }
        Map<Integer, String> variable = pathLeaf.getVariable();
        List<String> parts = StrUtil.split(uri, '/', true, true);
        for (Map.Entry<Integer, String> entry : variable.entrySet()) {
            dataPackage.getPathVariables().put(entry.getValue(), parts.get(entry.getKey()));
        }
        HttpDataResponse httpDataResponse = new HttpDataResponse();
        pathLeaf.getPayload().getHttpInvokeHelper().doAction(dataPackage, httpDataResponse);
        // todo 处理执行结果
    }

    @Override
    public Type resolveInvokeParamType(HttpServiceWrapper data) {
        return null;
    }

    @Override
    public Object toObject(byte protocol, byte[] bs, Type type) {
        return null;
    }

    @Override
    public byte[] toByte(byte protocol, Object object) {
        return new byte[0];
    }

    @Override
    public void post(ServerContext serverContext) {
        List<HttpInvokerScanner> httpInvokerScannerList = serverContext.getBeanFactoryContext().getBeanList(HttpInvokerScanner.class);
        this.urlSearchTreePool = new HashMap<>();
        if (CollUtil.isNotEmpty(httpInvokerScannerList)) {
            for (HttpInvokerScanner httpInvokerScanner : httpInvokerScannerList) {
                // 扫描获取服务
                List<HttpServiceWrapper> httpServiceWrappers = httpInvokerScanner.scan();
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
