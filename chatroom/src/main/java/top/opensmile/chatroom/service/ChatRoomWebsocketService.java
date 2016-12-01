/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.chatroom.service;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.opensmile.chatroom.handler.ChatRoomHandler;
import top.opensmile.chatroom.handler.WebSocketFrameHandler;
import top.opensmile.core.service.AbstractLauncherService;

import java.util.Map;

/**
 * Created by opensmile on 16/11/13.
 */
public class ChatRoomWebsocketService extends AbstractLauncherService {

    private static Logger logger = LoggerFactory.getLogger(ChatRoomHandler.class);

    public static void main(String[] args) throws InterruptedException {
        logger.info("starting");
        new ChatRoomWebsocketService().run();
    }

    private static final String WEBSOCKET_PATH = "/";

    @Override
    public ChannelHandler getChildHandler() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new HttpServerCodec());
                pipeline.addLast(new HttpObjectAggregator(65536));
                pipeline.addLast(new WebSocketServerCompressionHandler());
                pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
                pipeline.addLast(new WebSocketFrameHandler());
//                pipeline.addLast(new WebSocketIndexPageHandler(WEBSOCKET_PATH));
//                pipeline.addLast(new WebSocketServerCompressionHandler());
//                pipeline.addLast(new WebSocketServerProtocolHandler());
//                pipeline.addLast(new WebsocketServerHandler());
            }
        };
    }

}
