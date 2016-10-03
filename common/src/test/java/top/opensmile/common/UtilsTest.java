/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.common;

import org.junit.Assert;
import org.junit.Test;
import top.opensmile.core.test.BaseTest;

/**
 * Created by opensmile on 16/10/2.
 */
public class UtilsTest extends BaseTest{

    private static final String ORIGINAL = "The_China_N|ational_Day";

    @Test public void subStrByTheChar(){
        Assert.assertEquals("HeadToTheChar error",Utils.subStrByTheChar(ORIGINAL,"_",Boolean.TRUE),"The");
        Assert.assertEquals("TheCharToEnd error",Utils.subStrByTheChar(ORIGINAL,"_",Boolean.FALSE),"China_N|ational_Day");
        Assert.assertEquals("TheCharToEnd error",Utils.subStrByTheChar(ORIGINAL,"y",Boolean.FALSE),"");
        Assert.assertEquals("TheCharToEnd error",Utils.subStrByTheChar(ORIGINAL,"y",Boolean.TRUE),"The_China_N|ational_Da");
        Assert.assertEquals("TheCharToEnd error",Utils.subStrByTheChar(ORIGINAL,"T",Boolean.FALSE),"he_China_N|ational_Day");
        Assert.assertEquals("TheCharToEnd error",Utils.subStrByTheChar(ORIGINAL,"T",Boolean.TRUE),"");
    }

}
