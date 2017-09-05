package com.lyun.user.api;

import com.lyun.api.APIBase;
import com.lyun.user.api.service.AfterSaleService;
import com.lyun.user.api.service.AuthService;
import com.lyun.user.api.service.LanguageService;
import com.lyun.user.api.service.LawWorldService;
import com.lyun.user.api.service.MultipartService;
import com.lyun.user.api.service.ServiceCardService;
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
    public static ServiceCardService serviceCard = create(ServiceCardService.class);
    public static LawWorldService lawWorld = create(LawWorldService.class);
    public static MultipartService multipartService = create(MultipartService.class);
    public static AfterSaleService afterSale = create(AfterSaleService.class);

}
