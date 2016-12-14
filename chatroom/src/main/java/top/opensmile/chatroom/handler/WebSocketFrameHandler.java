/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.chatroom.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.opensmile.chatroom.domain.MesInfo;
import top.opensmile.chatroom.sql.dao.UserDao;
import top.opensmile.chatroom.sql.pristence.UserDomain;
import top.opensmile.common.Utils;

import java.util.Enumeration;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by opensmile on 16/11/19.
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketFrameHandler.class);

    // exist channelHandlerContext token list
    private static final ConcurrentHashMap<ChannelHandlerContext,String> CONTEXT_TOKEN_MAP = new ConcurrentHashMap<ChannelHandlerContext,String>();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        String content = "{'code':0,'message':'not login'}";
        respMessage(ctx,content);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame) throws Exception {
        logger.info("webSocketFrame "+webSocketFrame);
        if (webSocketFrame instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) webSocketFrame).text();
            MesInfo mesInfo = JSON.parseObject(text, MesInfo.class);
            String mes = mesInfo.getMessage();
            // chatting
            if(10 == mesInfo.getCode()){
                String token = CONTEXT_TOKEN_MAP.get(channelHandlerContext);
                if(mesInfo.getToken() == null || mesInfo.getToken().equals("") || token == null || token.equals("") || !token.equals(mesInfo.getToken())){
                    String content = "{'code':241,'message':'" + mes + "'}";
                    respMessage(channelHandlerContext,content);
                    return;
                }
                String[] strs = token.split("&");
                String content = "{'code':200,'message':'"+strs[1]+" : " + mes + "'}";
                logger.info(content);
                Enumeration<ChannelHandlerContext> keys = CONTEXT_TOKEN_MAP.keys();
                while(keys.hasMoreElements()){
                    ChannelHandlerContext ctx = keys.nextElement();
                    respMessage(ctx,content);
                }
                return ;
            }
            // login
            if (1 == mesInfo.getCode()) {
                String[] contents = Utils.splitStr(mes);
                String username = contents[0];
                int code =211;
                String token = "";
                if(UserDao.queryLogin(username, contents[1])){
                    code = 210;
                    // token = uuid + username
                    token = UUID.randomUUID().toString()+"&"+username;
                    CONTEXT_TOKEN_MAP.put(channelHandlerContext,token);
                }
                String content = "{'code':"+code+",'message':'"+token+"'}";
                respMessage(channelHandlerContext,content);
                return ;
            }

            // register
            if (2 == mesInfo.getCode()) {
                String[] contents = Utils.splitStr(mes);
                if(contents[1] == contents[2]){
                    String content = "{'code':"+222+",'message':'two password not the same'}";
                    respMessage(channelHandlerContext,content);
                }
                UserDomain userDomain = new UserDomain();
                userDomain.setName(contents[0]);
                userDomain.setPasswd(contents[1]);
                int flag = UserDao.insert(userDomain);
                String content = "";
                if(flag == 1){
                    String token = UUID.randomUUID().toString()+"&"+contents[0];
                    CONTEXT_TOKEN_MAP.put(channelHandlerContext,token);
                    content = "{'code':"+220+",'message':'"+token+"'}";
                }else if (flag == -1){
                    content = "{'code':"+223+",'message':'register's name already exist'}";
                }else {
                    content = "{'code':"+229+",'message':'register other exception'}";
                }
                respMessage(channelHandlerContext,content);
                return ;
            }
        }else {
            throw new Exception(" unsupported frame type " + webSocketFrame.getClass().getName());
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        CONTEXT_TOKEN_MAP.remove(ctx);
        super.channelUnregistered(ctx);
    }

    private  void respMessage(ChannelHandlerContext ctx, String content){
        ctx.writeAndFlush(new TextWebSocketFrame(content));
    }
}
