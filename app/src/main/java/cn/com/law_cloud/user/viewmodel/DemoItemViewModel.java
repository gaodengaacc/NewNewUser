package cn.com.law_cloud.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import cn.com.law_cloud.user.BR;

/**
 * @author Gordon
 * @since 2016/12/16
 * do()
 */
public class DemoItemViewModel extends BaseObservable {
    private String label;

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
}
