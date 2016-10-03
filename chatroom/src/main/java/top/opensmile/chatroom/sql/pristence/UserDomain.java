/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.chatroom.sql.pristence;

/**
 * Created by opensmile on 16/10/1.
 */
public class UserDomain {

    private int id;

    private String name;

    private String macadd;

    private String passwd;

    private String ip;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMacadd() {
        return macadd;
    }

    public void setMacadd(String macadd) {
        this.macadd = macadd;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
