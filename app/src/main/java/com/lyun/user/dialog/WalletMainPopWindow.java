package com.lyun.user.dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.lyun.user.R;
import com.lyun.user.databinding.WalletMainPopubWindowBinding;
import com.lyun.user.viewmodel.WalletMainPopViewModel;
import com.lyun.utils.DisplayUtil;

/**
 * @author Gordon
 * @since 2016/2/25
 * do()
 */
public class WalletMainPopWindow extends PopupWindow {
    private Context context;

    public WalletMainPopWindow(final Context context) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        WalletMainPopubWindowBinding viewBinding= DataBindingUtil.inflate(inflater, R.layout.wallet_main_popub_window,null,false);
        WalletMainPopViewModel viewModel = new WalletMainPopViewModel(context);
        viewBinding.setMvvm(viewModel);
        // 设置SelectPicPopupWindow的View
        this.setContentView(viewBinding.getRoot());

        // 设置SelectPicPopupWindow弹出窗体的宽
//        this.setWidth(DisplayUtil.dip2px(context, 153));
        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        this.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.wallet_main_des_bg));
    }
}
