package com.lyun.user.viewmodel;

import android.view.View;

import com.lyun.library.mvvm.viewmodel.DialogViewModel;
import com.lyun.user.R;
import com.lyun.user.eventbusmessage.EventSelectImageItemMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Gordon
 * @since 2017/8/3
 * do()
 */

public class SelectImageDialogViewModel extends DialogViewModel {

    public void onClickView(View view) {
      switch (view.getId()){
          case R.id.select_image:
              dismiss();
              EventBus.getDefault().post(new EventSelectImageItemMessage(0));
              break;
          case R.id.save_image:
              dismiss();
              EventBus.getDefault().post(new EventSelectImageItemMessage(1));

              break;
          default:
              break;
      }
    }
}
