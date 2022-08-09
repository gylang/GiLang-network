package com.gilang.common.domian.http;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.gilang.common.exception.HttpPathRepetitionException;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gylang
 * data 2022/8/8
 */
public class UrlSearchTree {

    /** 树根节点列表 */
    private Root root;

    public PathLeaf findPathLeaf(String uri) {

        List<String> parts = StrUtil.split(uri, '/', true, true);
        if (CollUtil.isEmpty(parts)) {
            return root.pathLeaf;
        }
        return doFind(parts, 0, root, root.nextRoot);
    }

    private PathLeaf doFind(List<String> uri, int index, Root father, Map<String, Root> next) {

        if (uri.size() > index) {
            // 还未到叶子节点
            if (CollUtil.isEmpty(next)) {
                return null;
            } else {
                // 继续查找
                Root root = next.get(uri.get(index));
                if (null != root) {
                    // 通过静态uri配置查找
                    PathLeaf pathLeaf = doFind(uri, index + 1, root, root.nextRoot);
                    if (null != pathLeaf) {
                        // 已经找到就直接返回
                        return pathLeaf;
                    }
                }
                // 通过动态uri参数配置查找
                Root generics = next.get(null);
                if (null != generics) {
                    // 通过静态uri配置查找
                    // 已经找到就直接返回
                    return doFind(uri, index + 1, generics, generics.nextRoot);
                }
                // 还未到叶子节点 但是已经不符合
                return null;
            }

        } else {
            // 已经是叶子节点
            return null != father ? father.pathLeaf : null;
        }
    }

    public void register(String uri, HttpInvokeHelper httpInvokeHelper) {

        PathLeaf pathLeaf = new PathLeaf();
        pathLeaf.httpInvokeHelper = httpInvokeHelper;
        pathLeaf.variable = new HashMap<>();
        List<String> parts = StrUtil.split(uri, '/', true, true);
        if (CollUtil.isEmpty(parts)) {
            if (null != root.pathLeaf) {
                // 重复注册
                throw new HttpPathRepetitionException(uri);
            } else {
                root.pathLeaf = pathLeaf;
            }
        }
        Root tempRoot = root;
        for (int i = 0; i < parts.size(); i++) {

            // 判断是否为静态part
            String part = parts.get(i);
            if (StrUtil.startWith(part, '{') && StrUtil.endWith(part, '}')) {
                Root root = tempRoot.nextRoot.get(null);
                if (null == root) {
                    root = new Root();
                    tempRoot.nextRoot.put(null, root);
                }
                tempRoot = root;
            } else {
                // 静态
                Root root = tempRoot.nextRoot.get(part);
                if (null == root) {
                    root = new Root();
                    root.part = part;
                    tempRoot.nextRoot.put(null, root);
                }
                tempRoot = root;
            }
            // 判断是否为末尾节点
            if (parts.size() - 1 == i) {
                // 末尾节点, 生成叶子节点
                if (null != tempRoot.pathLeaf) {
                    // 重复注册
                    throw new HttpPathRepetitionException(uri);
                } else {
                    tempRoot.pathLeaf = pathLeaf;
                }
            }
        }
    }

    public static class Root {

        /** 当前路径 */
        private String part;


        /** 树上不存在任何一个相同叶子节点(不可能存在相同路径规则的uri) */
        private PathLeaf pathLeaf;

        /** 子根节点 */
        private Map<String, Root> nextRoot;
    }

    @Getter
    public static class PathLeaf {


        /** 变量 */
        private Map<Integer, String> variable;

        /** http调用处理器 */
        private HttpInvokeHelper httpInvokeHelper;
    }
}
