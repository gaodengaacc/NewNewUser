package com.lyun.viewmodel;

import android.app.Dialog;
import android.content.Context;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.view.View;

import com.lyun.library.BR;
import com.lyun.widget.dialog.SimpleMessageDialog;

/**
 * @author Gordon
 * @since 2016/12/22
 * do(SimpleDialogViewModel类)
 */

public class SimpleDialogViewModel extends BaseViewModel {
    public final ObservableField<String>  contentText = new ObservableField<>();
    public final ObservableField<String> btnCancelText = new ObservableField<>();
    public final ObservableField<String> btnOkText = new ObservableField<>();
    public final ObservableField<Integer> btnCancelVisible = new ObservableField<>();
    public final ObservableField<Integer> btnOkVisible = new ObservableField<>();
    public final ObservableField<Integer> dividerVisible = new ObservableField<>();
    private SimpleMessageDialog.OnItemClickListener onItemClickListener;

    public SimpleDialogViewModel(Context context) {
        super(context);
        contentText.set("提示信息");
        btnCancelText.set("取消");
        btnOkText.set("确定");
        btnCancelVisible.set(View.VISIBLE);
        btnOkVisible.set(View.VISIBLE);
        dividerVisible.set(View.VISIBLE);
    }
    public void setOnItemClickListener(SimpleMessageDialog.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void onCancelClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.OnCancelClick(view);
        }
    }

    public void onOkClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.OnYesClick(view);
        }
    }
}
