package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.lyun.library.mvvm.bindingadapter.edittext.ViewBindingAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.R;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.EventToastMessage;
import com.lyun.user.model.ResetPasswordModel;
import com.lyun.utils.RegExMatcherUtils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Gordon
 * @since 2017/2/15
 * do()
 */

public class ResetPasswordViewModel extends ViewModel {
    public final ObservableField<String> password = new ObservableField<>("");
    public final ObservableField<String> newPassword1 = new ObservableField<>("");
    public final ObservableField<String> newPassword2 = new ObservableField<>("");
    public final ObservableInt clearVisible1 = new ObservableInt();
    public final ObservableInt clearVisible2 = new ObservableInt();
    public final ObservableInt clearVisible3 = new ObservableInt();
    public ObservableInt clearVisible = new ObservableInt();
    private EventToastMessage message;
    private EventProgressMessage progressMessage;
    public ResetPasswordViewModel() {
        init();
    }

    private void init() {
        clearVisible1.set(View.INVISIBLE);
        clearVisible2.set(View.INVISIBLE);
        clearVisible3.set(View.INVISIBLE);
    }

    public RelayCommand onSubmitClick = new RelayCommand(() -> {
        message = new EventToastMessage();
        if ("".equals(password.get())) {
            message.setMessage("请输入原密码");
        } else if ("".equals(newPassword1.get())) {
            message.setMessage("请输入新密码");
        } else if ("".equals(newPassword2.get())) {
            message.setMessage("请确认新密码");
        } else if (!newPassword1.get().equals(newPassword2.get())) {
            message.setMessage("两次新密码输入不同");
        } else if (!RegExMatcherUtils.matchPassword(newPassword1.get())) {
            message.setMessage("请正确输入6~16位字母或数字");
        } else {
            resetPassword(Account.preference().getPhone(), password.get(), newPassword1.get());
            return;
        }
        EventBus.getDefault().post(message);
    });
    private void resetPassword(String userName, String password, String newPassword) {
        progressMessage = new EventProgressMessage(true);
        EventBus.getDefault().post(progressMessage);
        new ResetPasswordModel().resetPassword(userName, password, newPassword)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                            progressMessage.setMessage(false);
                            EventBus.getDefault().post(progressMessage);
                            if (apiResult.isSuccess()) {
                                message.setMessage("修改成功");
                            } else {
                                message.setMessage(apiResult.getDescribe());
                            }
                            EventBus.getDefault().post(message);
                        }
                        , throwable -> {
                            message.setMessage(throwable.getMessage());
                            progressMessage.setMessage(false);
                            EventBus.getDefault().post(progressMessage);
                            EventBus.getDefault().post(message);
                        });
    }

    public RelayCommand<ViewBindingAdapter.TextChangeData> editTextCommand = new RelayCommand<ViewBindingAdapter.TextChangeData>((data) -> {
        switch (data.viewId) {
            case R.id.edit_password:
                clearVisible = clearVisible1;
                break;
            case R.id.edit_newpassword1:
                clearVisible = clearVisible2;
                break;
            case R.id.edit_newpassword2:
                clearVisible = clearVisible3;
                break;
            default:
                break;
        }
        if(data.text.length()>0)
            clearVisible.set(View.VISIBLE);
        else
            clearVisible.set(View.INVISIBLE);
    });

    public void onClearClick(View view) {
        switch (view.getId()) {
            case R.id.clear_text1:
                password.set("");
                break;
            case R.id.clear_text2:
                newPassword1.set("");
                break;
            case R.id.clear_text3:
                newPassword2.set("");
                break;
            default:
                break;
        }
    }
}
