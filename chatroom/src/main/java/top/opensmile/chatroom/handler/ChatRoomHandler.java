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

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by opensmile on 16/9/18.
 */
public class ChatRoomHandler extends SimpleChannelInboundHandler<String> {

    Logger logger = LoggerFactory.getLogger(ChatRoomHandler.class);

    private static final CopyOnWriteArrayList<ChannelHandlerContext> CTXLIST = new CopyOnWriteArrayList();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("ChatRoomHandler channelRegistered()");
        String ipstr = ctx.channel().remoteAddress().toString();
        String ip = Utils.getIpFromIpstrByPattern(ipstr);
        UserDomain userDomain = UserDao.queryByIp(ip);
        if (userDomain == null) {
            // 该ip的用户没有注册过
            ctx.writeAndFlush("welcome to opensmile chatroom,please set you name \r\n");
        }else {
            ctx.writeAndFlush("hi " + userDomain.getName() + " welcome to opensmile chatroom! \r\n");
            CTXLIST.add(ctx);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        String ipstr = ctx.channel().remoteAddress().toString();
        String ip = Utils.getIpFromIpstrByPattern(ipstr);
        if (!CTXLIST.contains(ctx)) {
            String username = Utils.subStrByTheCharFromHead(msg,"\n");
            UserDomain userdomain = new UserDomain();
            userdomain.setName(username);
            userdomain.setIp(ip);
            UserDao.insert(userdomain);
            ctx.writeAndFlush("hi " + username+" thank to register \r\n");
            CTXLIST.add(ctx);
            return ;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("ip : " + ip);
        sb.append(" msg : " + msg+"\r\n");
        logger.info(sb.toString());
        for (ChannelHandlerContext _ctx : CTXLIST) {
            if (_ctx != ctx) {
                _ctx.writeAndFlush(sb.toString());
            }
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        CTXLIST.remove(ctx);
        super.channelUnregistered(ctx);
    }
}
