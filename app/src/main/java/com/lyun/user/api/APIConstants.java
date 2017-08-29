package com.lyun.user.api;

/**
 * @author 赵尉尉
 * @date 2016/12/20
 */

public class APIConstants {

    // Auth Service
    public static final String LOGIN = "login";//登录的URL
    public static final String THIRD_LOGIN = "other_login";//三方登录
    public static final String THIRD_LOGIN_REGISTER = "other_register";//三方登录注册
    public static final String THIRD_LOGIN_BIND = "other_bind";//三方登录注册
    public static final String REGISTERVERIFYPHONE = "smsSend";//获取验证码的URL
    public static final String REGISTER = "register";//注册的URL
    public static final String RESET_PASSWORD = "uppassword";//修改密码
    public static final String FIND_BY_LANGUAGE = "findByDomain";//查找语言
    public static final String CHECK_VERIFICATION = "checkVerification";//验证验证码
    public static final String FIND_PASSWORD="resetPassword";//找回密码
    public static final String STATISTICS_CARDNO="statisticsCardNo";//查询使用时长，呼叫总次数，接触语种

    public static final String CHARGE_PAY = "advancePayment"; //充值接口
    public static final String CHARGE_RECORDER ="findPayinfo"; //充值记录
    // Order Service
    public static final String GENERATE_TRANSLATION_ORDER = "addBill";//生成翻译订单
    public static final String REMAINING_TIME = "remainingtime";//查询剩余时间
    public static final String HEART_BEAT = "heartbeat";//心跳接口
    public static final String CANCEL_ORDER = "ringoffupstatus";//修改订单状态
    public static final String TRANSLATOR_STATUS = "lawUserPhone";//翻译过程的状态
    //地址管理
    public static final String ADD_ADDRESS= "add_address"; //添加地址
    public static final String QUERY_ADDRESS ="my_address"; //查询地址
    public static final String DEFAULT_ADDRESS ="default_address"; //设置默认地址
    public static final String DELETE_ADDRESS ="del_address"; //删除地址
    public static final String UPDATE_ADDRESS ="update_address"; //修改地址

}
