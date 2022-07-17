package com.gilang.network.example.domain.payload.res;

import lombok.Data;

/**
 * @author gylang
 * data 2022/7/17
 */
@Data
public class CodeRes {

    private String code;

    private String msg;


    public CodeRes(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
