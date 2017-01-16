package com.lyun.user.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;

import com.lyun.library.BuildConfig;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * @author Gordon
 * @since 2017/1/6
 * do()
 */

public class UserSettingViewModel extends ViewModel {
    public final ObservableField<String> appVersion = new ObservableField<>();

    public UserSettingViewModel(GeneralToolbarViewModel.ToolbarViewModel toolbarViewModel) {
        toolbarViewModel.title.set("设置");
        toolbarViewModel.onBackClick.set((v) ->getActivity().finish());
        init();
    }

    private void init() {
        appVersion.set(BuildConfig.VERSION_NAME);
    }

    public void changePassWardOnClick(View view){

    }

}
