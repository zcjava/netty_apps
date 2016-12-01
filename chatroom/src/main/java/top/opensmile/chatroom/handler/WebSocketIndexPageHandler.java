/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.chatroom.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * Created by opensmile on 16/11/19.
 */
public class WebSocketIndexPageHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String websocketpath;

    public WebSocketIndexPageHandler(String websocketpath) {
        this.websocketpath = websocketpath;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        // handle a bad request
        if (!fullHttpRequest.decoderResult().isSuccess()) {
            return ;
        }
    }
}
