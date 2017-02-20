package com.lyun.user.viewmodel;

import android.databinding.ObservableField;

import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * @author Gordon
 * @since 2017/2/17
 * do()
 */

public class WxPayResultViewModel extends ViewModel {
    public final ObservableField<String> text = new ObservableField<>();
}
