package com.gilang.network.starter;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.ServerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author gylang
 * data 2022/7/7
 */
@Slf4j
public class BeanLoaderProcess {

    public void process(ServerContext serverContext, List<Props> propsList) {
        for (Props props : propsList) {
            String beanLoaderListStr = props.getStr("com.gilang.network.context.BeanLoader");
            String[] beanLoaderList = StrUtil.split(beanLoaderListStr, ",");
            for (String beanLoaderStr : beanLoaderList) {
                try {
                    BeanLoader beanLoader = ReflectUtil.newInstance(beanLoaderStr);
                    beanLoader.scan(serverContext);
                } catch (UtilException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
