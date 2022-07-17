package com.gilang.network.example.domain;

import lombok.Data;

/**
 * @author gylang
 * data 2022/7/17
 */
@Data
public class UserInfo {

    /** 用户名称 */
    private String contactName;

    /** 用户id */
    private Long contactId;

    /** 头像 */
    private String avatar;

}
