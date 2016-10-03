/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.chatroom.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.opensmile.chatroom.sql.dao.UserDao;
import top.opensmile.chatroom.sql.pristence.UserDomain;
import top.opensmile.common.Utils;

/**
 * Created by opensmile on 16/9/18.
 */
public class ChatRoomHandler extends SimpleChannelInboundHandler<String> {

    Logger logger = LoggerFactory.getLogger(ChatRoomHandler.class);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("ChatRoomHandler channelRegistered()");
        String ip = ctx.channel().remoteAddress().toString();
        UserDomain userDomain = UserDao.queryByIp(ip);
        if (userDomain == null) {
            // 该ip的用户没有注册过
            ctx.writeAndFlush("welcome to opensmile chatroom,please set you name \n\r");
            return ;
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        String ip = ctx.channel().remoteAddress().toString();

        UserDomain userdomain = UserDao.queryByIp(ip);
        if (userdomain == null) {
            String username = Utils.subStrByTheCharFromHead(msg,"\n");
            userdomain = new UserDomain();
            userdomain.setName(username);
            userdomain.setIp(ip);
            UserDao.insert(userdomain);
            ctx.writeAndFlush("hi " + username+"\n\r");
            return ;
        }


        StringBuffer sb = new StringBuffer();
        sb.append("ip : " + ip);
        sb.append(" msg : " + msg);
        logger.info(sb.toString());
        ctx.writeAndFlush(sb.toString());
    }

}
