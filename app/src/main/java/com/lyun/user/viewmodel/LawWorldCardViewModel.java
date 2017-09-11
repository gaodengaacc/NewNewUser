package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.command.consumer.Consumer0;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.api.response.LawWorldResponse;

import java.util.List;

public class LawWorldCardViewModel extends ViewModel {

    public final ObservableField<LawWorldResponse> data = new ObservableField<>();
    private OnClickListener onClickListener;

    public final ObservableField<String> domin1 = new ObservableField<>();
    public final ObservableInt domin1Visibility = new ObservableInt(View.GONE);
    public final ObservableField<String> domin2 = new ObservableField<>();
    public final ObservableInt domin2Visibility = new ObservableInt(View.GONE);
    public final ObservableField<String> domin3 = new ObservableField<>();
    public final ObservableInt domin3Visibility = new ObservableInt(View.GONE);


    public LawWorldCardViewModel(OnClickListener onClickListener, LawWorldResponse data) {
        this.data.set(data);
        this.onClickListener = onClickListener;

        List<LawWorldResponse.Domin> domins = data.getDominList();
        if (domins != null) {
            if (domins.size() >= 1 && domins.get(0) != null) {
                domin1.set(domins.get(0).getName());
                domin1Visibility.set(View.VISIBLE);
            }
            if (domins.size() >= 2 && domins.get(1) != null) {
                domin2.set(domins.get(1).getName());
                domin2Visibility.set(View.VISIBLE);
            }
            if (domins.size() >= 3 && domins.get(2) != null) {
                domin3.set(domins.get(2).getName());
                domin3Visibility.set(View.VISIBLE);
            }
        }
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
        void onClick(LawWorldResponse card);
    }

}
