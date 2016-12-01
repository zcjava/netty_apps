/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.chatroom.domain;

import java.io.Serializable;

/**
 * Created by opensmile on 16/11/24.
 */
public class MesInfo implements Serializable{

    private static final long serialVersionUID = 4651045673760006528L;

    // request
    // 1 login operator;
    // 2 register opertator;
    // 10 say

    // response
    // 200 success;
    // 210 login success;
    // 211 login fail;
    // 220 register success;
    // 221 register fail;
    // 222 two password not the same
    // 223 register's name already exist
    // 229 register other exception
    // 241 token invalid
    private int code;

    //chat content
    private String message;

    // chat token
    private String token ;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
