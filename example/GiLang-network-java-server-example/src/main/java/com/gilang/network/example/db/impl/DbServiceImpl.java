package com.gilang.network.example.db.impl;

import cn.hutool.db.DbUtil;
import com.gilang.network.example.db.DbService;

import javax.sql.DataSource;

/**
 * @author gylang
 * data 2022/7/21
 */

public class DbServiceImpl implements DbService {


    public <T> DataSource getDateSource() {
        return DbUtil.getDs();
    }

}
