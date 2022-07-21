package com.gilang.network.example.domain.db;

import lombok.Data;

/**
 * @author gylang
 * data 2022/7/21
 */
@Data
public class User {

    /** 用户id */
    private Long id;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 昵称 */
    private String nickname;

    /** salt */
    private String salt;

    /** 状态 */
    private Integer status;


}
