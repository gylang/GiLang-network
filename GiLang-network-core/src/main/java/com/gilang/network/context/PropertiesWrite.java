package com.gilang.network.context;

import cn.hutool.core.io.resource.ResourceUtil;

/**
 * @author gylang
 * data 2022/7/8
 */
public class PropertiesWrite {


    public static PropertiesVisitor init(String path) {
        PropertiesVisitor propertiesVisitor = new PropertiesVisitor();
        propertiesVisitor.props.load(ResourceUtil.getResource(path));
        return propertiesVisitor;
    }
}
