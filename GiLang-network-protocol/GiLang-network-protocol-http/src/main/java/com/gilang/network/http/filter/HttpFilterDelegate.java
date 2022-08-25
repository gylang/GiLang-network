package com.gilang.network.http.filter;

import cn.hutool.core.collection.CollUtil;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.common.util.OrderUtil;
import com.gilang.network.context.ServerContext;
import com.gilang.network.context.SessionContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.http.HttpSessionContext;

import java.util.List;

/**
 * @author gylang
 * data 2022/8/18
 */
public class HttpFilterDelegate implements AfterNetWorkContextInitialized {

    private List<HttpFilterChain> orderFilter;


    /**
     * 过滤器
     *
     * @param request  请求
     * @param response 响应
     */
    public void doFilter(HttpDataRequest<?> request, HttpDataResponse response, HttpSessionContext sessionContext) {

        for (HttpFilterChain httpFilterChain : orderFilter) {
            httpFilterChain.doFilter(request, response);
            if (response.isDone()) {
                sessionContext.write(response);
                return;
            }
        }
    }

    @Override
    public void post(ServerContext serverContext) {
        List<HttpFilter> beanList = serverContext.getBeanFactoryContext().getBeanList(HttpFilter.class);
        beanList = OrderUtil.sortAscByAnnotation(beanList, Integer.MAX_VALUE);
        orderFilter = CollUtil.map(beanList, HttpFilterChainImpl::new, true);

        for (int i = 0; i < orderFilter.size(); i++) {

            HttpFilterChain httpFilterChain = orderFilter.get(i);
            httpFilterChain.setNext(i == orderFilter.size() - 1 ? new HttpFilterChainImpl(empty) : orderFilter.get(i + 1));
        }
    }

    private HttpFilter empty = new HttpFilter() {
        @Override
        public void doFilter(HttpDataRequest<?> request, HttpDataResponse response, HttpFilterChain chain) {

        }
    };
}
