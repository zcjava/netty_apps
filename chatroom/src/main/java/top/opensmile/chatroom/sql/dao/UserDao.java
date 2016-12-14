/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.chatroom.sql.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.opensmile.common.sql.H2SqlService;
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

    public static int insert(UserDomain userDomain) throws SQLException {
        if (userDomain == null || userDomain.getName() == null || userDomain.getName().trim().equals("")) return 0;
        Connection connection = H2SqlService.connectionThreadLocal.get();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from userinfo where name ='" + userDomain.getName() + "'");
        // exist username
        if(resultSet.next()){
            return -1;
        }
        StringBuffer sb = new StringBuffer("insert into userinfo(name,macadd,passwd,ip) values(");
        sb.append("'" + userDomain.getName() + "'");
        sb.append(",'" + userDomain.getMacadd() + "'");
        sb.append(",'" + userDomain.getPasswd() + "'");
        sb.append(",'" + userDomain.getIp() + "'");
        sb.append(")");
        logger.info("insert sql :"+sb.toString());
        statement.execute(sb.toString());
        num++;
        connection.commit();
        return 1;
    }

    public static ResultSet query(String querySql) throws SQLException {
        logger.info("query sql "+querySql);
        Connection connection = H2SqlService.connectionThreadLocal.get();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(querySql);
        return resultSet;
    }

    public static boolean queryLogin(String username,String password) throws SQLException{
        logger.info("queryLogin username "+username +" password "+password);
        String sql = "select * from userinfo where name='"+username+"' and passwd='"+password+"'";
        ResultSet rs = query(sql);
        if(rs.next()){
            return true;
        }
        return false;
    }

    public static UserDomain queryByIp(String ip) throws SQLException {
        logger.info("queryByIp "+ip);
        String queryByipSql = "select * from userinfo where ip ='"+ip+"' order by id desc limit 1";
        ResultSet rs = query(queryByipSql);
        if(rs.next()){
            UserDomain userDomain = new UserDomain();
            userDomain.setId(rs.getInt(1));
            userDomain.setName(rs.getString(2));
            userDomain.setMacadd(rs.getString(3));
            userDomain.setPasswd(rs.getString(4));
            userDomain.setIp(rs.getString(5));
            return userDomain;
        }
        return null;
    }

}
