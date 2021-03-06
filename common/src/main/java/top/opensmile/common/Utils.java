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

    /**
     * 传进来的 包含Ip字符串，通过正则表达式得到ip
     *  @param ipstr
     * @return
     */
    public static String getIpFromIpstrByPattern(String ipstr) {
        String p_str = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(p_str);
        java.util.regex.Matcher matcher = pattern.matcher(ipstr);
        if(matcher.find()) {
            String ip = matcher.group();
            return ip;
        }
        return "";
    }

    public static String[] splitStr(String content,String symbol) {
        String[] array = content.split(symbol);
        return array;
    }

    public static String[] splitStr(String content) {
        return splitStr(content, "&");
    }

    /**
     * 获取项目物理路径
     * if 在生产环境中，使用startup.sh启动项目，需要java启动加上-Dproject.path的参数
     * if 在开发环境中，使用的类的
     * @return
     */
    public static String getProjectResourcesPath(){
        String ppath = System.getProperty("project.path");
        if(ppath == null || "".equals(ppath)){
            DEBUG_MODEL = true;
            return Utils.class.getResource("/").getPath().toString();
        }
        return ppath;
    }

    private static boolean DEBUG_MODEL = false ;

    public static boolean isDebugModel(){
        return DEBUG_MODEL;
    }
    public static final String RESOURCE_PATH = getProjectResourcesPath();
}
