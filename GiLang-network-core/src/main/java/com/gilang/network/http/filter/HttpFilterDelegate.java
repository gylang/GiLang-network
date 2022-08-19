package com.gilang.network.http.filter;

import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.common.domian.http.HttpSessionContext;
import com.gilang.common.util.OrderUtil;
import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;

import java.util.List;

/**
 * @author gylang
 * data 2022/8/18
 */
public class HttpFilterDelegate implements AfterNetWorkContextInitialized {

    private List<HttpFilter> orderFilter;


    /**
     * 过滤器
     *
     * @param request  请求
     * @param response 响应
     */
   public void doFilter(HttpDataRequest<?> request, HttpDataResponse response, HttpSessionContext sessionContext) {

        for (int i = 0; i < orderFilter.size(); i++) {
            orderFilter.get(i).doFilter(request, response, i == orderFilter.size() - 1 ? empty : orderFilter.get(i + 1));
            if (response.isDone()) {
                sessionContext.write(response);
                return;
            }
        }
    }

    @Override
    public void post(ServerContext serverContext) {
        List<HttpFilter> beanList = serverContext.getBeanFactoryContext().getBeanList(HttpFilter.class);
        orderFilter = OrderUtil.sortAscByAnnotation(beanList, Integer.MAX_VALUE);
    }

    private HttpFilter empty = new HttpFilter() {
        @Override
        public void doFilter(HttpDataRequest<?> request, HttpDataResponse response, HttpFilter chain) {

        }
    };
}
