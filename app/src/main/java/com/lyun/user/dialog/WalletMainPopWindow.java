package com.lyun.user.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.widget.PopupWindow;

import com.lyun.user.R;
import com.lyun.user.databinding.WalletMainPopubWindowBinding;
import com.lyun.user.viewmodel.WalletMainPopViewModel;
import com.lyun.user.viewmodel.watchdog.IWalletMainPopViewModelCallbacks;
import com.lyun.utils.DisplayUtil;

/**
 * @author Gordon
 * @since 2016/2/25
 * do()
 */
public class WalletMainPopWindow extends PopupWindow implements IWalletMainPopViewModelCallbacks {
    public WalletMainPopWindow(final Context context, WalletMainPopViewModel viewModel) {
        LayoutInflater inflater = LayoutInflater.from(context);
        WalletMainPopubWindowBinding viewBinding= DataBindingUtil.inflate(inflater, R.layout.wallet_main_popub_window,null,false);
        viewModel.setPropertyChangeListener(this);
        viewBinding.setMvvm(viewModel);
        // 设置SelectPicPopupWindow的View
        this.setContentView(viewBinding.getRoot());
        // 设置SelectPicPopupWindow弹出窗体的宽
        //宽度全屏
        setWidth(DisplayUtil.dip2px(context,260));
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(DisplayUtil.dip2px(context,185));
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.bg_home_fragment_center));
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void isShow(ObservableField<WalletMainPopViewModel.ShowData> observableField, int fieldId) {
        WalletMainPopViewModel.ShowData showData = observableField.get();
        if (showData.getGravity() != WalletMainPopViewModel.ShowData.DEFAULT_DATA)
            showAsDropDown(showData.getAnchor(),showData.getXoff(),showData.getYoff(),showData.getGravity());
       else if(showData.getXoff()!= WalletMainPopViewModel.ShowData.DEFAULT_DATA)
            showAsDropDown(showData.getAnchor(),showData.getXoff(),showData.getYoff());
        else
            showAsDropDown(showData.getAnchor());
    }

    @Override
    public void isDismiss(ObservableBoolean observableField, int fieldId) {
        dismiss();
    }



}
