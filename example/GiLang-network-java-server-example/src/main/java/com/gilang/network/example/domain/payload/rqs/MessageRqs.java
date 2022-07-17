package com.gilang.network.example.domain.payload.rqs;

import lombok.Data;

/**
 * @author gylang
 * data 2022/7/17
 */
@Data
public class MessageRqs {

    private String sender;

    private String receive;

    private String content;

    private Long timestamp;


}
