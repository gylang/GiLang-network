package com.gilang.network.http.config;

import lombok.Data;

/**
 * @author gylang
 * data 2022/5/31
 */
@Data
public class HttpConfig {



    /** 处理tcp线程数 */
    private Integer bossGroupThreadNum;

    /** 处理channel现成数 */
    private Integer workerGroupThreadNum;

    /** 服务端口 */
    private Integer port;

    /** 设置多久时间内没有读操作(接收客户端数据)触发读超时 */
    private Integer readerIdle;

    /** 设置时间内没有给客户端写入数据触发写超时 */
    private Integer writeIdle;

    /** 设置时间内没有 读操作 or 写操作 超时 */
    private Integer allIdle;

}
