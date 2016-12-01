/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.core.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by opensmile on 16/8/17.
 */
public abstract class AbstractLauncherService {

    private static Logger logger = LoggerFactory.getLogger(AbstractLauncherService.class);

    public AbstractLauncherService(){
        port = DEFAULT_PORT;
        String _port = System.getProperty("server.port");
        logger.info("start port "+port);
        if(_port != null && !"".equals(_port) ){
            try {
                port = Integer.parseInt(_port);
            } catch (NumberFormatException e) {
                logger.info("param : server port invalid "+port);
            }
        }
        logger.info("start port "+port);
    }

    public AbstractLauncherService(int _port){
        port = _port;
    }

    private int port ;

    public abstract ChannelHandler getChildHandler();

    private static final int DEFAULT_PORT = 9000;

    public void run() throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            ServerBootstrap channel = b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class);
            channel.
                    childHandler(getChildHandler())
                    .option(ChannelOption.SO_BACKLOG, 1024).childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }


//    new ChannelInitializer<SocketChannel>() {
//        protected void initChannel(SocketChannel socketChannel) throws Exception {
//            ChannelPipeline pipeline = socketChannel.pipeline();
//            if(_cls.){
//                pipeline.addLast(new HttpRequestDecoder());
//                pipeline.addLast(new HttpResponseEncoder());
//            }else {
//                pipeline.addLast(new StringDecoder());
//                pipeline.addLast(new StringEncoder());
//            }
//            socketChannel.pipeline().addLast((ChannelInboundHandlerAdapter)_cls.newInstance());
//        }
//    }
}
