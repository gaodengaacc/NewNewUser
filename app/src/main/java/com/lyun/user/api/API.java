package com.lyun.user.api;

import com.lyun.api.APIBase;
import com.lyun.user.api.service.AuthService;
import com.lyun.user.api.service.LanguageService;
import com.lyun.user.api.service.TranslationOrderService;

/**
 * 网络接口服务集合
 *
 * @author 赵尉尉
 * @date 2016/12/20
 */
public class API extends APIBase {

    public static AuthService auth = create(AuthService.class);
    public static TranslationOrderService translationOrder = create(TranslationOrderService.class);
    public static LanguageService language = create(LanguageService.class);

}
