/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.chatroom.sql.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.opensmile.core.sql.H2SqlService;
import top.opensmile.chatroom.sql.pristence.UserDomain;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by opensmile on 16/9/29.
 */
public class UserDao {

    private static Logger logger = LoggerFactory.getLogger(UserDao.class);

    private static int num = 1;

    public static void insert(UserDomain userDomain) throws SQLException {
        if (userDomain == null) return ;
        Connection connection = H2SqlService.connectionThreadLocal.get();
        Statement statement = connection.createStatement();
        StringBuffer sb = new StringBuffer("insert into userinfo(id,name,macadd,passwd,ip) values(");
        sb.append(num);
        sb.append(",'" + userDomain.getName() + "'");
        sb.append(",'" + userDomain.getMacadd() + "'");
        sb.append(",'" + userDomain.getPasswd() + "'");
        sb.append(",'" + userDomain.getIp() + "'");
        sb.append(")");
        logger.info("UserDao insert sql :"+sb.toString());
        statement.execute(sb.toString());
        num++;
        connection.commit();
    }

    public static ResultSet query(String querySql) throws SQLException {
        Connection connection = H2SqlService.connectionThreadLocal.get();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(querySql);
        return resultSet;
    }

    public static UserDomain queryByIp(String ip) throws SQLException {
        String queryByipSql = "select * from userinfo where ip ='" + ip + "'";
        ResultSet rs = query(queryByipSql);
        if(rs.getFetchSize() == 0 ){
            return null;
        }
        UserDomain userDomain = new UserDomain();
        userDomain.setId(rs.getInt(0));
        userDomain.setName(rs.getString(1));
        userDomain.setMacadd(rs.getString(2));
        userDomain.setPasswd(rs.getString(3));
        userDomain.setIp(rs.getString(4));
        return userDomain;
    }

}
