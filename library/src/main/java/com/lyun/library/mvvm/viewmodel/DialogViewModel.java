package com.lyun.library.mvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;

import net.funol.databinding.watchdog.annotations.WatchThis;

/**
 * @author Gordon
 * @since 2017/1/16
 * do()
 */

public class DialogViewModel extends ViewModel {

    public DialogViewModel() {
    }

    public DialogViewModel(Context context) {
        super(context);
    }

    @WatchThis
    public final ObservableBoolean isShow = new ObservableBoolean();
    @WatchThis
    public final ObservableBoolean isDismiss = new ObservableBoolean();
    @WatchThis
    public final ObservableBoolean isOutSideCancel = new ObservableBoolean();
    public void show() {
        if (getContext() != null) {
            isShow.notifyChange();
        }
    }
    public void dismiss() {
        isDismiss.notifyChange();
    }
}
