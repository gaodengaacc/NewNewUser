package com.lyun.user.viewmodel;

import android.databinding.ObservableField;

import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * @author Gordon
 * @since 2017/1/9
 * do()
 */

public class WalletMainRecorderItemViewModel extends ViewModel {
    public final ObservableField<String> time = new ObservableField<>();
    public final ObservableField<String> description = new ObservableField<>();
   public WalletMainRecorderItemViewModel(){

   }
    public WalletMainRecorderItemViewModel(String time, String description){
        this.time.set(time);
        this.description.set(description);
    }
    public void init(int position) {

    }
}
