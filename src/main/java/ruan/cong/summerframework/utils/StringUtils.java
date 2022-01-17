package ruan.cong.summerframework.utils;

public class StringUtils {
    public static Boolean isEmpty(String str){
        return str == null || str.trim().equals("");
    }

    public static Boolean nonEmpty(String str){
        return !(str == null || str.trim().equals(""));
    }
}
