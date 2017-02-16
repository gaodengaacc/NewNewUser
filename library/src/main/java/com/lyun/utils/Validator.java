package com.lyun.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    // 匹配是否为邮箱

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    // 匹配是否为手机号码

    public static boolean isMobileNO(String mobiles) {
        // 电信号段：133 139 153 180 181 189 177 170 176
        // 移动号段：138 137 136 135 134 178 188 187 183 182 159 158 157 152 150 147
        // 联通号段：186 185 130 131 132 155 156
        String str = "^((13[0-9])|(147)|(15[0,2,3,5-9])|(17[0,6-8])|(18[0-3,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    // 匹配是否为电话号码

    public static boolean isPhoneNum(String phoneNum) {
        String str = "([0-9]{2})+-([0-9]{4})+-([0-9]{4})+";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }

    // 匹配用户名

    // 0-9 a-z A-Z 汉字

    public static boolean isUserName(String name) {
        String str = "[a-zA-Z0-9\u4E00-\u9FA5]+";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    // 匹配QQ号码

    public static boolean isQQNum(String num) {
        String str = "[1-9][0-9]{4,}";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(num);
        return m.matches();
    }

    public static int isPassword(String password) {
        if (password.length() >= 6) {
            if (isPasswordOnlyLetter(password) || isPasswordOnlyNum(password) || isPasswordOnlySpecialCharacters(password)) {
                return 1;// 强度低

            } else if (isPasswordNumAndLetterAndSpecialCharacters(password)) {
                return 3;// 强度高

            } else if (isPasswordLetterAndSpecialCharacters(password) || isPasswordNumAndLetter(password) || isPasswordNumAndSpecialCharacters(password)) {
                return 2;// 强度中等

            } else {
                return -1;// 包含非法字符

            }
        } else {
            return 0;// 小于六位

        }
    }

    // 只包含数字的密码 强度：低
    public static boolean isPasswordOnlyNum(String password) {
        String str = "\\d+";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    // 只包含字母的密码 强度：低
    public static boolean isPasswordOnlyLetter(String password) {
        String str = "[a-zA-Z]+";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    // 只包含特殊字符的密码 强度：低
    public static boolean isPasswordOnlySpecialCharacters(String password) {
        String str = "[-`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]+";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    // 包含数字和字母小于六位 强度：中
    public static boolean isPasswordNumAndLetter(String password) {
        String str = "[a-zA-Z0-9]*\\d+[a-zA-Z0-9]+[a-zA-Z0-9]*";
        System.out.println(str);
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    // 包含特殊字符和数字小于六位 强度：中
    public static boolean isPasswordNumAndSpecialCharacters(String password) {
        String str = "[-\\d`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]*\\d+[-\\d`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]+[-\\d`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]*";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    // 包含特殊字符和字母小于6位 强度：中
    public static boolean isPasswordLetterAndSpecialCharacters(String password) {
        String str = "[-a-zA-Z`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]*[a-zA-Z]+[-a-zA-Z`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]+[-a-zA-Z`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]*";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    // 包含特殊字符、数字和字母小于六位 强度：强
    public static boolean isPasswordNumAndLetterAndSpecialCharacters(String password) {
        String str = "[-\\da-zA-Z`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]*((\\d+[a-zA-Z]+[-`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]+)|(\\d+[-`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]+[a-zA-Z]+)|([a-zA-Z]+\\d+[-`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]+)|([a-zA-Z]+[-`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]+\\d+)|([-`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]+\\d+[a-zA-Z]+)|([-`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]+[a-zA-Z]+\\d+))[-\\da-zA-Z`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]*";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isPassportNO(String no){
//        护照标准格式
//        1.G+8位数字
//        2.P+7位数字
//        3.S+7 or 8位数字
//        4.D+任意位数字
//        5.14+7位数字
//        6.15+7位数字
        String str = "(^(G|S)\\d{8}$)|(^(P|S|14|15)\\d{7}$)|(^D\\d+$)";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(no);
        return m.matches();
    }

    //身份证号码验证：start
    /**
     * 功能：身份证的有效验证
     * @param IDStr 身份证号
     * @return 有效：返回"" 无效：返回String信息
     * @throws ParseException
     */
    public static boolean isCardNO(String IDStr) throws Exception {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4",
                "3", "2" };
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2" };
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            throw new Exception(errorInfo);
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            throw new Exception(errorInfo);
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            throw new Exception(errorInfo);
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                || (gc.getTime().getTime() - s.parse(
                strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
            errorInfo = "身份证生日不在有效范围。";
            throw new Exception(errorInfo);
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            throw new Exception(errorInfo);
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            throw new Exception(errorInfo);
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            throw new Exception(errorInfo);
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if(IDStr.substring(17).equals("X")||IDStr.substring(17).equals("x")){
                return true;
            }
            if (Ai.equals(IDStr) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                throw new Exception(errorInfo);
            }
        } else {
            return true;
        }
        // =====================(end)=====================
        return true;
    }

    /**
     * 功能：判断字符串是否为数字
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：设置地区编码
     * @return Hashtable 对象
     */
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**验证日期字符串是否是YYYY-MM-DD格式
     * @param str
     * @return
     */
    public static boolean isDataFormat(String str){
        boolean flag=false;
        //String regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
        String regxStr="^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1=Pattern.compile(regxStr);
        Matcher isNo=pattern1.matcher(str);
        if(isNo.matches()){
            flag=true;
        }
        return flag;
    }

    //身份证号码验证：end

}