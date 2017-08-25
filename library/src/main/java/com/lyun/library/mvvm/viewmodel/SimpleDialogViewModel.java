package com.lyun.library.mvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.view.View;

import com.lyun.widget.dialog.SimpleMessageDialog;

/**
 * @author Gordon
 * @since 2016/12/22
 * do(SimpleDialogViewModel类)
 */
public class SimpleDialogViewModel extends DialogViewModel {
    public final ObservableField<String>  contentText = new ObservableField<>();
    public final ObservableField<String> btnCancelText = new ObservableField<>();
    public final ObservableField<String> btnOkText = new ObservableField<>();
    public final ObservableInt btnCancelVisible = new ObservableInt();
    public final ObservableInt btnOkVisible = new ObservableInt();
    public final ObservableInt dividerVisible = new ObservableInt();
    public final ObservableInt btnCancelTextColor = new ObservableInt();
    public final ObservableInt btnYesTextColor = new ObservableInt();
    public final ObservableInt infoColor = new ObservableInt();
    private OnItemClickListener onItemClickListener;

    public SimpleDialogViewModel(Context context, String info) {
        super(context);
        contentText.set(info == null ? "提示信息" : info);
        init();
    }

    public SimpleDialogViewModel(Context context) {
        super(context);
        contentText.set( "提示信息");
        init();
    }

    private void init() {
        btnCancelTextColor.set(Color.parseColor("#485465"));
        btnYesTextColor.set(Color.parseColor("#485465"));
        infoColor.set(Color.parseColor("#485465"));
        btnCancelText.set("取消");
        btnOkText.set("确定");
        btnCancelVisible.set(View.VISIBLE);
        btnOkVisible.set(View.VISIBLE);
        new SimpleMessageDialog(getContext(), this);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void onCancelClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.OnCancelClick(view);
            isDismiss.notifyChange();
        }
    }

    public void setInfo(String info) {
        this.contentText.set(info);
    }

    public void setYesBtnText(String yes) {
        this.btnOkText.set(yes);
    }

    public void setCancelBtnText(String cancel) {
        this.btnCancelText.set(cancel);
    }

    public void setBtnYesTextColor(int color) {
        btnYesTextColor.set(color);
    }

    public void setBtnCancelTextColor(int color) {
        btnYesTextColor.set(color);
    }
    public void setBtnYesVisibility(int visibility) {
        this.btnOkVisible.set(visibility);
        if(visibility == View.GONE)
            this.dividerVisible.set(visibility);
    }

    public void setBtnCancelVisibility(int visibility) {
        this.btnCancelVisible.set(visibility);
        if(visibility == View.GONE)
            this.dividerVisible.set(visibility);
    }

    public void onOkClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.OnYesClick(view);
            isDismiss.notifyChange();
        }
    }

    public interface OnItemClickListener {
        public void OnYesClick(View view);

        public void OnCancelClick(View view);
    }
}
