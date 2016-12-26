package com.lyun.user.model;

import java.math.BigDecimal;

/**
 * @author Gordon
 * @since 2016/12/20
 * do(处理DiscoverFragment逻辑类)
 */

public class DiscoverFragmentModel {

    public double divideWidth(int screenWidth, int picWidth, int retainValue) {
        BigDecimal screenBD = new BigDecimal(Double.toString(screenWidth));
        BigDecimal picBD = new BigDecimal(Double.toString(picWidth));
        return screenBD.divide(picBD, retainValue, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }
}
