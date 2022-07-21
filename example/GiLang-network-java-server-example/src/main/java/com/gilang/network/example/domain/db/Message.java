package com.gilang.network.example.domain.db;

import lombok.Data;

/**
 * @author gylang
 * data 2022/7/21
 */
@Data
public class Message {

    /** id */
    private Long id;
    /** 消息id */
    private Long msgId;
    /** 发送人 */
    private Long sendId;
    /** 接收人id */
    private Long receiveId;
    /** 消息内容 */
    private String message;

    private Long timeStamp;


}
