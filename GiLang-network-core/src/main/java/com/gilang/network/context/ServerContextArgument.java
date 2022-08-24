package com.gilang.network.context;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 启动im服务的上下文对象
 *
 * @author gylang
 * data 2022/5/31
 */

@Setter
public class ServerContextArgument {

    /** 命令参数识别 */
    private final Pattern ARGUMENT_PATTERN = Pattern.compile("-D(\\w+)=(.+)");

    /** 参数 */
    private Map<String, List<String>> arguments = new HashMap<>();

    public ServerContextArgument(String[] args) {

        // 只解析-Dxxx=xxx
        for (String arg : args) {
            if (ARGUMENT_PATTERN.matcher(arg).matches()) {
                List<String> allGroups = ReUtil.getAllGroups(ARGUMENT_PATTERN, arg);
                List<String> argument = arguments.computeIfAbsent(allGroups.get(0), k -> new LinkedList<>());
                argument.add(allGroups.get(1));
            }
        }
    }

    public void addArgument(String name, String value) {
        this.arguments.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
    }

    public void setArgument(String name, List<String> value) {
        this.arguments.put(name, value);
    }

    public List<String> getArgumentList(String name) {
        return this.arguments.get(name);
    }

    public String getFirstArgument(String name) {
        return CollUtil.getFirst(this.arguments.get(name));
    }
}
