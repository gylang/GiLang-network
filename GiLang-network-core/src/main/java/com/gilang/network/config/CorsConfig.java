
package com.gilang.network.config;

import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * 跨域配置
 */
@Data
public class CorsConfig {


    private static final List<String> DEFAULT_METHODS = Collections.unmodifiableList(
            Arrays.asList("GET", "HEAD"));

    private static final List<String> DEFAULT_PERMIT_METHODS = Collections.unmodifiableList(
            Arrays.asList("GET", "HEAD", "POST"));


    private List<String> allowedOrigins;


    private List<String> allowedMethods;


    private List<String> resolvedMethods = DEFAULT_METHODS;


    private List<String> allowedHeaders;


    private List<String> exposedHeaders;


    private Boolean allowCredentials;


    private Long maxAge;


}
