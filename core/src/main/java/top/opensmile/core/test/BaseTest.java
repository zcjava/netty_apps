/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.core.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by opensmile on 16/10/2.
 */
public class BaseTest {

    private static Logger logger = LoggerFactory.getLogger(BaseTest.class);

    private static long startTime ;

    @Before public void Before(){
        startTime = System.currentTimeMillis();
    }

    @After public void After() {
        float runtime = (System.currentTimeMillis() - startTime )/1000 ;
        logger.info("The test run time(s) :"+runtime);
    }

    @Rule
    public TestWatcher watchman= new TestWatcher() {

        @Override
        protected void finished(Description description) {
            logger.info("The class "+ description.getClassName() +" test  method :"+ description.getMethodName());
        }
    };
}
