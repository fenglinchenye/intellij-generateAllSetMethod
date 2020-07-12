package com.bruce.intellijplugin.generatesetter.utils;

import java.util.Objects;

/**
 *  工具类
 * @author freedo
 **/
public class StringUtils {

    public static String firstLowCase(String str){
        if (Objects.isNull(str)||str.isEmpty()){
            return str;
        }
        if (str.length()==1){
            return str.toLowerCase();
        }else{
            return str.substring(0,1).toLowerCase()+str.substring(1);
        }
    }

    public static String firstUpperCase(String str){
        if (Objects.isNull(str)||str.isEmpty()){
            return str;
        }
        if (str.length()==1){
            return str.toUpperCase();
        }else{
            return str.substring(0,1).toUpperCase()+str.substring(1);
        }
    }

}
