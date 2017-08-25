package com.opipo.junit.pojo.utils;

public final class Utils {
    private Utils(){}

    public static String removeSemicolon(String text){
        return text.endsWith(";")?text.substring(0,text.length()-1):text;
    }
}
