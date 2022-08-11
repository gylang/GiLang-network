package com.gilang.network.config;

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

}
