package com.lyun.utils;

import android.support.annotation.NonNull;

/**
 * Created by ZHAOWEIWEI on 2017/3/13.
 */

public class FormatUtil {

    public static String formatUserName(@NonNull String phone) {
        if (phone != null && RegExMatcherUtils.isMobileNO(phone.substring(1))) {
            return phone.substring(0, 4) + "*****" + phone.substring(9);
        }
        return phone;
    }

}
