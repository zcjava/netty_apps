/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.chatroom.service;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import top.opensmile.chatroom.handler.ChatRoomHandler;
import top.opensmile.core.service.AbstractLauncherService;

/**
 * Created by opensmile on 16/9/2.
 */
public class ChatRoomService extends AbstractLauncherService {

    public static void main(String[] args) throws InterruptedException {
        new ChatRoomService().run();
    }

    public ChannelHandler getChildHandler() {
        return new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new StringDecoder());
                    pipeline.addLast(new StringEncoder());
                    socketChannel.pipeline().addLast(new ChatRoomHandler());
                }
        };
    }
}
