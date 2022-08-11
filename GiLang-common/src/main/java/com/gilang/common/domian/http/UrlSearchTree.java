package com.gilang.common.domian.http;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.gilang.common.exception.HttpPathRepetitionException;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gylang
 * data 2022/8/8
 */
public class UrlSearchTree<T> {

    /** 树根节点列表 */
    private final Root<T> root = new Root<>();

    public PathLeaf<T> findPathLeaf(String uri) {

        List<String> parts = StrUtil.split(uri, '/', true, true);
        if (CollUtil.isEmpty(parts)) {
            return root.pathLeaf;
        }
        return doFind(parts, 0, root, root.nextRoot);
    }

    private PathLeaf<T> doFind(List<String> uri, int index, Root<T> father, Map<String, Root<T>> next) {

        if (uri.size() > index) {
            // 还未到叶子节点
            if (CollUtil.isEmpty(next)) {
                return null;
            } else {
                // 继续查找
                Root<T> root = next.get(uri.get(index));
                if (null != root) {
                    // 通过静态uri配置查找
                    PathLeaf<T> pathLeaf = doFind(uri, index + 1, root, root.nextRoot);
                    if (null != pathLeaf) {
                        // 已经找到就直接返回
                        return pathLeaf;
                    }
                }
                // 通过动态uri参数配置查找
                Root<T> generics = next.get(null);
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

    /**
     * 将uri注册到查找树
     *
     * @param uri     注册的uri
     * @param payload 附带调用方式
     */
    public void register(String uri, T payload) {

        PathLeaf<T> pathLeaf = new PathLeaf<>();
        pathLeaf.uri = uri;
        pathLeaf.payload = payload;
        pathLeaf.variable = new HashMap<>();
        List<String> parts = StrUtil.split(uri, '/', true, true);
        if (CollUtil.isEmpty(parts)) {
            if (null != root.pathLeaf) {
                // 重复注册
                throw new HttpPathRepetitionException(uri, root.pathLeaf.uri);
            } else {
                root.pathLeaf = pathLeaf;
            }
        }
        Root<T> tempRoot = root;
        for (int i = 0; i < parts.size(); i++) {

            // 判断是否为静态part
            String part = parts.get(i);
            if (StrUtil.startWith(part, '{') && StrUtil.endWith(part, '}')) {
                pathLeaf.variable.put(i, part.substring(1, part.length() - 1));
                Root<T> root = tempRoot.nextRoot.get(null);
                if (null == root) {
                    root = new Root<>();
                    tempRoot.nextRoot.put(null, root);
                }
                tempRoot = root;
            } else {
                // 静态
                Root<T> root = tempRoot.nextRoot.get(part);
                if (null == root) {
                    root = new Root<>();
                    root.part = part;
                    tempRoot.nextRoot.put(part, root);
                }
                tempRoot = root;
            }
            // 判断是否为末尾节点
            if (parts.size() - 1 == i) {
                // 末尾节点, 生成叶子节点
                if (null != tempRoot.pathLeaf) {
                    // 重复注册
                    throw new HttpPathRepetitionException(uri, tempRoot.pathLeaf.uri);
                } else {
                    tempRoot.pathLeaf = pathLeaf;
                }
            }
        }
    }

    @Getter
    public static class Root<T> {

        /** 当前路径 */
        private String part;


        /** 树上不存在任何一个相同叶子节点(不可能存在相同路径规则的uri) */
        private PathLeaf<T> pathLeaf;

        /** 子根节点 */
        private final Map<String, Root<T>> nextRoot = new HashMap<>();
    }

    @Getter
    @ToString
    public static class PathLeaf<T> {

        private String uri;

        /** 变量 */
        private Map<Integer, String> variable = new HashMap<>();

        /** 负载数据 */
        private T payload;
    }

    public static void main(String[] args) {
        UrlSearchTree<HttpInvokeHelper> urlSearchTree = new UrlSearchTree<>();
        urlSearchTree.register("user/id", null);
        urlSearchTree.register("user/{id}", null);
        urlSearchTree.register("{app}/search", null);
        System.out.println(urlSearchTree.findPathLeaf("/user/id"));
        System.out.println(urlSearchTree.findPathLeaf("/user/12345"));
        System.out.println(urlSearchTree.findPathLeaf("/user/search"));
        System.out.println(urlSearchTree.findPathLeaf("/user1/search"));
        System.out.println(urlSearchTree.findPathLeaf("/user/search/aa"));
    }
}
