package com.gilang.network.starter;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.setting.dialect.Props;
import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.context.PropertiesWrite;
import com.gilang.network.context.ServerContext;
import com.gilang.network.event.EventContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.layer.session.SessionManager;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gylang
 * data 2022/7/7
 */
@Slf4j
public class ContextLoader {

    private String propertiesPath = "gilang.properties";

    public ServerContext contextLoad() {
        ServerContext serverContext = new ServerContext();
        serverContext.setBeanFactoryContext(new BeanFactoryContext());
        // 加载配置文件
        serverContext.setPropertiesVisitor(PropertiesWrite.init(propertiesPath));
        // 加载bean
        List<URL> resources = ClassUtil.getResources("init.properties");
        List<Props> propsList = resources.stream().map(Props::new).collect(Collectors.toList());

        new BeanLoaderProcess().process(serverContext, propsList);
        printAllBean(serverContext.getBeanFactoryContext());
        BeanFactoryContext beanFactoryContext = serverContext.getBeanFactoryContext();
        // bean加载完, 执行钩子函数, 回调, 处理注入逻辑
        List<AfterNetWorkContextInitialized> afterNetWorkContextInitializedList = beanFactoryContext.getBeanList(AfterNetWorkContextInitialized.class);
        serverContext.setEventContext(beanFactoryContext.getPrimaryBean(EventContext.class));
        serverContext.setSessionManager(beanFactoryContext.getPrimaryBean(SessionManager.class));
        for (AfterNetWorkContextInitialized afterNetWorkContextInitialized : afterNetWorkContextInitializedList) {
            afterNetWorkContextInitialized.post(serverContext);
        }
        return serverContext;
    }

    private void printAllBean(BeanFactoryContext beanFactoryContext) {

        String[] beanNames = beanFactoryContext.getAllBeanName();
        for (String name : beanNames) {
            log.debug("已加载bean: " + name);
        }
    }

    public void setPropertiesPath(String propertiesPath) {
        this.propertiesPath = propertiesPath;
    }
}
