/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */


import org.h2.index.Index;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by opensmile on 16/9/29.
 */
public class ThreadLocalTest implements Runnable {

    Logger logger = Logger.getLogger(ThreadLocalTest.class.getName());

    private static String str = new String();

    private static Person person = new Person();

    private static ThreadLocal<Person> threadLocal = new ThreadLocal<Person>();

    static class Person {
        private String name;
        private String passwd;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

    }

    public void run() {
        str = Thread.currentThread().getName().toString();
        person.setName(str);
        threadLocal.set(person);
        Person o = threadLocal.get();
        logger.info(Thread.currentThread().toString() + "  ++  " + o.toString() + "   "+ o.getName());

    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(new ThreadLocalTest()).start();
        }
        TimeUnit.SECONDS.sleep(1);
    }
}
