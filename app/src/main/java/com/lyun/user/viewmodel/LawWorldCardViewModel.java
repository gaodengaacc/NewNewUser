package com.lyun.user.viewmodel;

import android.databinding.ObservableField;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.command.consumer.Consumer0;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.api.response.LawWorldCardResponse;

/**
 * Created by ZHAOWEIWEI on 2017/7/31.
 */

public class LawWorldCardViewModel extends ViewModel {

    public final ObservableField<LawWorldCardResponse> data = new ObservableField<>();
    private OnClickListener onClickListener;

    public LawWorldCardViewModel(OnClickListener onClickListener, LawWorldCardResponse data) {
        this.data.set(data);
        this.onClickListener = onClickListener;
    }

    public RelayCommand onClick = new RelayCommand<>(new Consumer0() {
        @Override
        public void accept() throws Exception {
            if (onClickListener != null) {
                onClickListener.onClick(data.get());
            }
        }
    });

    interface OnClickListener {
        void onClick(LawWorldCardResponse card);
    }

}
