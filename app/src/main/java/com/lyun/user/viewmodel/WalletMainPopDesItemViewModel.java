package com.lyun.user.viewmodel;

import android.databinding.ObservableField;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.api.response.FindLanguageResponse;

/**
 * @author Gordon
 * @since 2017/1/10
 * do()
 */

public class WalletMainPopDesItemViewModel extends ViewModel {
    public WalletMainPopDesItemViewModel(FindLanguageResponse response){
        this.text.set(response.getName());
    }
    public final ObservableField<String> text = new ObservableField<>();
}
