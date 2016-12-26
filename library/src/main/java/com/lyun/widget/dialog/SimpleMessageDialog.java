package com.lyun.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.lyun.library.R;
import com.lyun.library.databinding.DialogSimpleMessageBinding;
import com.lyun.library.mvvm.viewmodel.SimpleDialogViewModel;

/**
 * @author 赵尉尉
 * @version 创建时间：2014年5月27日 上午10:12:47
 */

public class SimpleMessageDialog extends Dialog {

    private Context context;
    private String info = "";
    private OnItemClickListener onItemClickListener;
    private SimpleDialogViewModel viewModel;
    public SimpleMessageDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    public SimpleMessageDialog(Context context, String info) {
        super(context, R.style.dialog);
        this.context = context;
        this.info = info;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogSimpleMessageBinding viewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_simple_message, null, false);
        viewModel = new SimpleDialogViewModel(context);
        viewModel.contentText.set(info);
        viewModel.dividerVisible.set((viewModel.btnCancelVisible.get() == View.VISIBLE && viewModel.btnOkVisible.get()== View.VISIBLE) ? View.VISIBLE : View.GONE);
        viewBinding.setViewModel(viewModel);
        setContentView(viewBinding.getRoot());
    }

    @Override
    public void show() {
        if (!isShowing())
            super.show();
    }

    public void setInfo(String info) {
        viewModel.contentText.set(info);
    }
    public void setYesBtnText(String yes) {
       viewModel.btnOkText.set(yes);
    }

    public void setCancelBtnText(String cancel) {
        viewModel.btnCancelText.set(cancel);
    }

    public void setBtnYesVisibility(int visibility) {
       viewModel.btnOkVisible.set(visibility);
    }

    public void setBtnCancelVisibility(int visibility) {
       viewModel.btnCancelVisible.set(visibility);
    }
    public interface OnItemClickListener {
        public void OnYesClick(View view);

        public void OnCancelClick(View view);
    }
    public SimpleMessageDialog setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        viewModel.setOnItemClickListener(onItemClickListener);
        dismiss();
        return this;
    }

}
