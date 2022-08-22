package com.gilang.network.http.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.common.domian.http.HttpHeaders;
import com.gilang.common.domian.http.HttpStatus;
import com.gilang.common.enums.RequestMethod;
import com.gilang.network.config.CorsConfig;
import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.sun.istack.internal.Nullable;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author gylang
 * data 2022/8/20
 */
@Slf4j
public class HttpCorsFilter implements HttpFilter, AfterNetWorkContextInitialized {

    private CorsConfig corsConfig;

    @Override
    public void doFilter(HttpDataRequest<?> request, HttpDataResponse response, HttpFilterChain chain) {
        boolean isValid = processRequest(corsConfig, request, response);
        if (!isValid || isPreFlightRequest(request)) {
            return;
        }
        chain.doFilter(request, response);

    }

    public boolean processRequest(@Nullable CorsConfig config, HttpDataRequest<?> request,
                                  HttpDataResponse response) {

        Collection<String> varyHeaders = response.getHeaderList(HttpHeaders.VARY);
        if (!varyHeaders.contains(HttpHeaders.ORIGIN)) {
            response.addHeader(HttpHeaders.VARY, HttpHeaders.ORIGIN);
        }
        if (!varyHeaders.contains(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD)) {
            response.addHeader(HttpHeaders.VARY, HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD);
        }
        if (!varyHeaders.contains(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS)) {
            response.addHeader(HttpHeaders.VARY, HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
        }

        if (!isCors(request)) {
            return true;
        }

        if (response.getHeaderList(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN) != null) {
            log.trace("Skip: response already contains \"Access-Control-Allow-Origin\"");
            return true;
        }

        boolean preFlightRequest = isPreFlightRequest(request);
        if (config == null) {
            if (preFlightRequest) {
                rejectRequest(response);
                return false;
            } else {
                return true;
            }
        }

        return handleInternal(request, response, config, preFlightRequest);
    }

    private boolean handleInternal(HttpDataRequest<?> request, HttpDataResponse response, CorsConfig config, boolean preFlightRequest) {
        String requestOrigin = request.getFirstHeader(HttpHeaders.ORIGIN);
        String allowOrigin = config.checkOrigin( requestOrigin);

        if (allowOrigin == null) {
            log.debug("Reject: '" + requestOrigin + "' origin is not allowed");
            rejectRequest(response);
            return false;
        }

        String requestMethod = getMethodToUse(request, preFlightRequest);
        List<String> allowMethods = config.checkHttpMethod(requestMethod);
        if (allowMethods == null) {
            log.debug("Reject: HTTP '" + requestMethod + "' is not allowed");
            rejectRequest(response);
            return false;
        }

        List<String> requestHeaders = getHeadersToUse(request, preFlightRequest);
        List<String> allowHeaders = config.checkHeaders(requestHeaders);
        if (preFlightRequest && allowHeaders == null) {
            log.debug("Reject: headers '" + requestHeaders + "' are not allowed");
            rejectRequest(response);
            return false;
        }

        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, allowOrigin);

        if (preFlightRequest) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, allowMethods);
        }

        if (preFlightRequest && !allowHeaders.isEmpty()) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, allowHeaders);
        }

        if (!CollUtil.isEmpty(config.getExposedHeaders())) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, config.getExposedHeaders());
        }

        if (Boolean.TRUE.equals(config.getAllowCredentials())) {
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, String.valueOf(true));
        }

        if (preFlightRequest && config.getMaxAge() != null) {
            response.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, String.valueOf(config.getMaxAge()));
        }

        response.write(new byte[]{});
        return true;
    }

    private List<String> getHeadersToUse(HttpDataRequest<?> request, boolean isPreFlight) {
        return (isPreFlight ? request.getHeaderList(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS) : new ArrayList<>());
    }

    private void rejectRequest(HttpDataResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.write("Invalid CORS request".getBytes(StandardCharsets.UTF_8));
    }

    public static boolean isPreFlightRequest(HttpDataRequest<?> request) {
        return (RequestMethod.OPTIONS.match(request.getMethod()) &&
                request.getHeaderList(HttpHeaders.ORIGIN) != null &&
                request.getHeaderList(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD) != null);
    }

    private boolean isCors(HttpDataRequest<?> request) {
        String origin = request.getFirstHeader(HttpHeaders.ORIGIN);
        if (origin == null) {
            return false;
        }
        URL originUrl = null;
        try {
            originUrl = new URL(origin);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("[" + origin + "] is not a valid \"Origin\" header value");
        }
        String scheme = request.getServerProtocol();
        String host = request.getServerName();
        int port = request.getServerPort();
        return !(ObjectUtil.equals(scheme, originUrl.getProtocol()) &&
                ObjectUtil.equals(host, originUrl.getHost()) &&
                getPort(scheme, port) == getPort(originUrl.getProtocol(), originUrl.getPort()));
    }

    private int getPort(String scheme, int port) {
        if (port == -1) {
            if ("http".equals(scheme) || "ws".equals(scheme)) {
                port = 80;
            } else if ("https".equals(scheme) || "wss".equals(scheme)) {
                port = 443;
            }
        }
        return port;
    }

    private String getMethodToUse(HttpDataRequest<?> request, boolean isPreFlight) {
        return (isPreFlight ? request.getFirstHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD) : request.getMethod());
    }

    @Override
    public void post(ServerContext serverContext) {
        this.corsConfig = serverContext.getBeanFactoryContext().getPrimaryBean(CorsConfig.class);
    }
}
