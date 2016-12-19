package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.lyun.user.BR;

/**
 * @author Gordon
 * @since 2016/12/16
 * do()
 */
public class DemoItemViewModel extends BaseObservable {
    private String label;
    private boolean isShow;

    public DemoItemViewModel(String label) {
        this.label = label;
    }
    @Bindable
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
       this.label = label;
        notifyPropertyChanged(BR.label);
    }
    @Bindable
    public boolean getIsShow() {
        if("1".equals(label))
            isShow = true;
        return isShow;
    }

    public void setIsShow(boolean show) {
        isShow = show;
        notifyPropertyChanged(BR.isShow);
    }

}
