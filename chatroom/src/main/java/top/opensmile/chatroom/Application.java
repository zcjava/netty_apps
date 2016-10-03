/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.chatroom;

import top.opensmile.chatroom.service.ChatRoomService;

/**
 * Created by opensmile on 16/9/28.
 * 启动器
 */
public class Application {

    public static void main(String[] args) throws InterruptedException {
        new ChatRoomService().run();
    }
}
