/*
 * Copyright (c) 2005-2016, Opensmile.  All rights reserved.
 */

package top.opensmile.common;

/**
 * Created by opensmile on 16/10/2.
 */
public class Utils {

    public static String subStrByTheCharFromHead(String original, String thechar) {
        return subStrByTheChar(original, thechar, true);
    }


    /**
     * 对原字符串，根据特定字符进行截取.
     * @param original
     * @param thechar
     * @param isFromHead 是否从头开始，否则截取从指定字符后到尾部
     * @return
     */
    public static String subStrByTheChar(String original , String thechar, boolean isFromHead){
        if(original == null || "".equals(original)) return "";
        int index = original.indexOf(thechar);
        if(index < 0 ) return original;
        if (isFromHead) {
            return original.substring(0, index);
        }else {
            return original.substring(index+thechar.length());
        }
    }

}
