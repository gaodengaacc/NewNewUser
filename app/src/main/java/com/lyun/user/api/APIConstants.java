package com.lyun.user.api;

/**
 * @author 赵尉尉
 * @date 2016/12/20
 */

public class APIConstants {

    // Auth Service
    public static final String LOGIN = "login/";//登录的URL
    public static final String REGISTERVERIFYPHONE = "smsSend/";//获取验证码的URL
    public static final String REGISTER = "register/";//注册的URL
    public static final String RESET_PASSWORD = "uppassword/";//修改密码
    public static final String FIND_BY_LANGUAGE = "findByLanguage/";//查找语言
    public static final String CHECK_VERIFICATION = "checkVerification/";//验证验证码
    public static final String FIND_PASSWORD="resetPassword/";//找回密码

    // Order Service
    public static final String GENERATE_TRANSLATION_ORDER = "addBill/";//生成翻译订单
    public static final String REMAINING_TIME = "remainingtime/";//查询剩余时间

}
