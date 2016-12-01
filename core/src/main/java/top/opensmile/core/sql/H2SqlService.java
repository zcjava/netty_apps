/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.core.sql;

import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by opensmile on 16/9/29.
 *
 */
public class H2SqlService {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(H2SqlService.class);

    /**
     *  使用 ThreadLocal存储 sql connect, 便于各线程中调度使用
     */
    public static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>(){
        @Override
        protected Connection initialValue() {
            logger.debug("connectionThreadLocal initialValue()");
            try {
                Class.forName("org.h2.Driver");
                    return DriverManager.getConnection("jdbc:h2:~/chatroom;init=runscript from '~/create.sql'","sa","");
//                return DriverManager.getConnection("jdbc:h2:mem:chatroom;");
            } catch (ClassNotFoundException e) {
                logger.error("initialValue()",e);
                e.printStackTrace();
            } catch (SQLException e) {
                logger.error("initialValue()",e);
                e.printStackTrace();
            }
            return null;
        };
    };

    private H2SqlService(){}
}
