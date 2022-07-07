package com.gilang.network.context;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.config.WebsocketConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * 启动im服务的上下文对象
 *
 * @author gylang
 * data 2022/5/31
 */

@Getter
@Setter
public class ServerContext {


    private BeanFactoryContext beanFactoryContext;

    private PropertiesVisitor propertiesVisitor;

    private String logLevel = "INFO";
}
