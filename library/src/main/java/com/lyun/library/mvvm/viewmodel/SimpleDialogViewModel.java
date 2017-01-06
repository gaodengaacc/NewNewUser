package com.lyun.library.mvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.lyun.widget.dialog.SimpleMessageDialog;

/**
 * @author Gordon
 * @since 2016/12/22
 * do(SimpleDialogViewModel类)
 */
public class SimpleDialogViewModel extends ViewModel {
    public final ObservableField<String>  contentText = new ObservableField<>();
    public final ObservableField<String> btnCancelText = new ObservableField<>();
    public final ObservableField<String> btnOkText = new ObservableField<>();
    public final ObservableInt btnCancelVisible = new ObservableInt();
    public final ObservableInt btnOkVisible = new ObservableInt();
    public final ObservableInt dividerVisible = new ObservableInt();
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
