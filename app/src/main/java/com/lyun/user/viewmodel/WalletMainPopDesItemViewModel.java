package com.lyun.user.viewmodel;

import android.databinding.ObservableField;

import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * @author Gordon
 * @since 2017/1/10
 * do()
 */

public class WalletMainPopDesItemViewModel extends ViewModel {
    public WalletMainPopDesItemViewModel(String text){
        this.text.set(text);
    }
    public final ObservableField<String> text = new ObservableField<>();
}
