package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;

/**
 * @author Gordon
 * @since 2017/7/28
 * do()
 */

public class AccountBindingViewModel extends ViewModel {
    public final ObservableField<String> weiBoText = new ObservableField<>();
    public final ObservableField<String> qqText = new ObservableField<>();
    public final ObservableField<String> wxText = new ObservableField<>();

    public AccountBindingViewModel() {
        init();
    }

    private void init() {
        weiBoText.set("未绑定");
        qqText.set("未绑定");
        wxText.set("未绑定");
    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.text_wb:
                break;
            case R.id.text_qq:
                break;
            case R.id.text_wx:
                break;
        }
    }
}
