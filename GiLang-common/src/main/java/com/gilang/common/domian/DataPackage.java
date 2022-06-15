package com.gilang.common.domian;

import lombok.Data;

/**
 * @author gylang
 * data 2022/5/31
 */
@Data
public class DataPackage<T> {

    /** 64bit 雪花算法消息id */
    private long msgId;

    /** 16bit 报文体长度 */
    private int contentLength;

    /** 服务上传消息体 */
    private byte[] appContent;

    /** 解析后的报文体 */
    private T actBody;

}
