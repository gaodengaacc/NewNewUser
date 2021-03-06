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
    public static final String THIRD_LOGIN_BIND = "other_bind";//三方登录绑定
    public static final String RELEVANCE_THIRD = "findThird";//三方登录绑定信息查询
    public static final String REGISTERVERIFYPHONE = "smsSend";//获取验证码的URL
    public static final String REGISTER = "register";//注册的URL
    public static final String RESET_PASSWORD = "uppassword";//修改密码
    public static final String FIND_BY_LANGUAGE = "findByDomain";//查找语言
    public static final String CHECK_VERIFICATION = "checkVerification";//验证验证码
    public static final String FIND_PASSWORD = "resetPassword";//找回密码
    public static final String STATISTICS_CARDNO = "statisticsCardNo";//查询使用时长，呼叫总次数，接触语种

    public static final String CHARGE_PAY = "advancePayment"; //充值接口
    public static final String CHARGE_RECORDER = "findPayinfo"; //充值记录
    // Order Service
    public static final String GENERATE_TRANSLATION_ORDER = "addBill";//生成翻译订单
    public static final String REMAINING_TIME = "remainingtime";//查询剩余时间
    public static final String HEART_BEAT = "heartbeat";//心跳接口
    public static final String CANCEL_ORDER = "ringoffupstatus";//修改订单状态
    public static final String TRANSLATOR_STATUS = "lawUserPhone";//翻译过程的状态
    //地址管理
    public static final String ADD_ADDRESS = "add_address"; //添加地址
    public static final String QUERY_ADDRESS = "my_address"; //查询地址
    public static final String DEFAULT_ADDRESS = "default_address"; //设置默认地址
    public static final String DELETE_ADDRESS = "del_address"; //删除地址
    public static final String UPDATE_ADDRESS = "update_address"; //修改地址
    //服务卡
    public static final String QUERY_SERVICE_CARD_LIST = "findAllCard";//查询服务卡
    public static final String QUERY_SERVICE_MY_CARD_LIST = "findMyCard";//查询我的服务卡
    //律世界
    public static final String QUERY_LAW_WORLD_LIST = "get_lawyer_info";//查询律世界列表
    //上传图片
    public static final String UPDATE_HEADER = "update_img";//上传头像
    // 售后服务
    public static final String QUERY_ORDER_HISTORY = "getDealByCardNo";//查询订单记录
    public static final String QUERY_INVOICE_HISTORY = "getInvoiceByCardNo";//查询开票记录
    public static final String APPLY_FOR_INVOICE = "invoice_apply";//申请开票

}
