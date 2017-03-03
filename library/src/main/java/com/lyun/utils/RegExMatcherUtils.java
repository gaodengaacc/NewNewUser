package com.lyun.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Gordon
 * @since 2017/3/3
 * do(正则表达式处理)
 */

public class RegExMatcherUtils {
    public static boolean match(String str, String regEx) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    //处理6~16位数字或者字母的密码匹配
    public static boolean matchPassword(String str) {
        return match(str, "[\\da-zA-Z]{6,16}");
    }
}
