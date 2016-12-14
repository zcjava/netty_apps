/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.common.sql;

import org.slf4j.LoggerFactory;
import top.opensmile.common.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by opensmile on 16/9/29.
 *
 */
public class H2SqlService {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(H2SqlService.class);


    private static String h2_db_initsql_path = Utils.getProjectResourcesPath();

    static {
        if(Utils.isDebugModel()){
            h2_db_initsql_path += "/create.sql";
        }else{
            h2_db_initsql_path += "/resources/create.sql";
        }
    }

    /**
     *  使用 ThreadLocal存储 sql connect, 便于各线程中调度使用
     */
    public static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>(){
        @Override
        protected Connection initialValue() {
            logger.debug("connectionThreadLocal initialValue()");
            try {
                Class.forName("org.h2.Driver");
                    return DriverManager.getConnection("jdbc:h2:~/chatroom;init=runscript from '"+h2_db_initsql_path+"'","sa","");
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
