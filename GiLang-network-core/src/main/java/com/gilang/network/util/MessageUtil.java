package com.gilang.network.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.gilang.network.context.PropertiesVisitor;
import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;

/**
 * @author gylang
 * data 2022/7/9
 */
public class MessageUtil implements AfterNetWorkContextInitialized {
    private static Snowflake snowflake;

    public static Long nextMsgId() {
        return snowflake.nextId();
    }

    @Override
    public void post(ServerContext serverContext) {
        PropertiesVisitor propertiesVisitor = serverContext.getPropertiesVisitor();
        Integer workId = propertiesVisitor.getBaseTypeValue("gilang.network.workId", 1, Integer.class);
        Integer dataCenterId = propertiesVisitor.getBaseTypeValue("gilang.network.dataCenterId", 1, Integer.class);
        snowflake = IdUtil.getSnowflake(workId, dataCenterId);
    }
}
