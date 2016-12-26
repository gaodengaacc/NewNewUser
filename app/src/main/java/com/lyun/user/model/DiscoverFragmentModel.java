package com.lyun.user.model;

import android.view.View;

import com.lyun.fragment.BaseFragment;
import com.lyun.model.BaseModel;
import com.lyun.user.R;
import com.lyun.user.fragment.DiscoverFragment;
import com.lyun.user.viewmodel.DiscoverRecyclerItemViewModel;
import com.lyun.utils.ToastUtil;
import com.lyun.viewmodel.BaseViewModel;
import com.lyun.viewmodel.OnRecycleItemClickListener;
import com.lyun.widget.dialog.ProgressBarDialog;
import com.lyun.widget.dialog.ProgressDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/20
 * do(处理DiscoverFragment逻辑类)
 */

public class DiscoverFragmentModel extends BaseModel {

    public double divideWidth(int screenWidth, int picWidth, int retainValue) {
        BigDecimal screenBD = new BigDecimal(Double.toString(screenWidth));
        BigDecimal picBD = new BigDecimal(Double.toString(picWidth));
        return screenBD.divide(picBD, retainValue, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }
}
