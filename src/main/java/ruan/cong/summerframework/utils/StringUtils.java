package ruan.cong.summerframework.utils;

public class StringUtils {
    public static Boolean isEmpty(String str){
        return str == null || str.trim().equals("");
    }

    public static Boolean nonEmpty(String str){
        return !(str == null || str.trim().equals(""));
    }

    public static String lowerFirst(String str){
        if(null == str || str.length() <= 0) return null;

        if (Character.isUpperCase(str.charAt(0))) {
            if (str.length() < 2) {
                return String.valueOf(Character.toLowerCase(str.charAt(0)));
            }
            return Character.toLowerCase(str.charAt(0)) + str.substring(1);
        }
        return str;
    }
}
