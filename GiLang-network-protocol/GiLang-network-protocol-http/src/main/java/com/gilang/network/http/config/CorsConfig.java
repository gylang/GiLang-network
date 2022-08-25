
package com.gilang.network.http.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 跨域配置
 */
@Data
public class CorsConfig {

    public static final String ALL = "*";

    private static final List<String> DEFAULT_METHODS = Collections.unmodifiableList(
            Arrays.asList("GET", "HEAD"));

    private static final List<String> DEFAULT_PERMIT_METHODS = Collections.unmodifiableList(
            Arrays.asList("GET", "HEAD", "POST"));


    private List<String> allowedOrigins;


    private List<String> allowedMethods;

    /** 默认 */
    private List<String> resolvedMethods = DEFAULT_METHODS;

    /** 允许的请求头 */
    private List<String> allowedHeaders;


    private List<String> exposedHeaders;

    private List<OriginPattern> allowedOriginPatterns;

    private Boolean allowCredentials;

    private Long maxAge;


    public String checkOrigin(String origin) {
        if (!StrUtil.isNotEmpty(origin)) {
            return null;
        }
        String originToCheck = trimTrailingSlash(origin);
        if (!CollUtil.isEmpty(this.allowedOrigins)) {
            if (this.allowedOrigins.contains(ALL)) {
                validateAllowCredentials();
                return ALL;
            }
            for (String allowedOrigin : this.allowedOrigins) {
                if (originToCheck.equalsIgnoreCase(allowedOrigin)) {
                    return origin;
                }
            }
        }
        if (!CollUtil.isEmpty(this.allowedOriginPatterns)) {
            for (OriginPattern p : this.allowedOriginPatterns) {
                if (p.getDeclaredPattern().equals(ALL) || p.getPattern().matcher(originToCheck).matches()) {
                    return origin;
                }
            }
        }
        return null;
    }

    private String trimTrailingSlash(String origin) {
        return (origin.endsWith("/") ? origin.substring(0, origin.length() - 1) : origin);
    }

    public void validateAllowCredentials() {
        if (this.allowCredentials == Boolean.TRUE &&
                this.allowedOrigins != null && this.allowedOrigins.contains(ALL)) {

            throw new IllegalArgumentException(
                    "When allowCredentials is true, allowedOrigins cannot contain the special value \"*\" " +
                            "since that cannot be set on the \"Access-Control-Allow-Origin\" response header. " +
                            "To allow credentials to a set of origins, list them explicitly " +
                            "or consider using \"allowedOriginPatterns\" instead.");
        }
    }

    public List<String> checkHttpMethod(String requestMethod) {
        if (requestMethod == null) {
            return null;
        }
        if (this.resolvedMethods == null) {
            return Collections.singletonList(requestMethod);
        }
        return (this.resolvedMethods.contains(requestMethod) ? this.resolvedMethods : null);
    }


    public List<String> checkHeaders(List<String> requestHeaders) {
        if (requestHeaders == null) {
            return null;
        }
        if (requestHeaders.isEmpty()) {
            return Collections.emptyList();
        }
        if (CollUtil.isEmpty(this.allowedHeaders)) {
            return null;
        }

        boolean allowAnyHeader = this.allowedHeaders.contains(ALL);
        List<String> result = new ArrayList<>(requestHeaders.size());
        for (String requestHeader : requestHeaders) {
            if (StrUtil.isNotEmpty(requestHeader)) {
                requestHeader = requestHeader.trim();
                if (allowAnyHeader) {
                    result.add(requestHeader);
                } else {
                    for (String allowedHeader : this.allowedHeaders) {
                        if (requestHeader.equalsIgnoreCase(allowedHeader)) {
                            result.add(requestHeader);
                            break;
                        }
                    }
                }
            }
        }
        return (result.isEmpty() ? null : result);
    }

    private static class OriginPattern {

        private static final Pattern PORTS_PATTERN = Pattern.compile("(.*):\\[(\\*|\\d+(,\\d+)*)]");

        private final String declaredPattern;

        private final Pattern pattern;

        OriginPattern(String declaredPattern) {
            this.declaredPattern = declaredPattern;
            this.pattern = initPattern(declaredPattern);
        }

        private static Pattern initPattern(String patternValue) {
            String portList = null;
            Matcher matcher = PORTS_PATTERN.matcher(patternValue);
            if (matcher.matches()) {
                patternValue = matcher.group(1);
                portList = matcher.group(2);
            }

            patternValue = "\\Q" + patternValue + "\\E";
            patternValue = patternValue.replace("*", "\\E.*\\Q");

            if (portList != null) {
                patternValue += (portList.equals(ALL) ? "(:\\d+)?" : ":(" + portList.replace(',', '|') + ")");
            }

            return Pattern.compile(patternValue);
        }

        public String getDeclaredPattern() {
            return this.declaredPattern;
        }

        public Pattern getPattern() {
            return this.pattern;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || !getClass().equals(other.getClass())) {
                return false;
            }
            return ObjectUtil.equals(
                    this.declaredPattern, ((OriginPattern) other).declaredPattern);
        }

        @Override
        public int hashCode() {
            return this.declaredPattern.hashCode();
        }

        @Override
        public String toString() {
            return this.declaredPattern;
        }
    }

}
