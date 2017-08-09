package com.lyun.user.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.user.R;
import com.lyun.user.activity.AccountBindingActivity;
import com.lyun.user.activity.ImageCropActivity;
import com.lyun.user.activity.ImageHeaderActivity;
import com.lyun.user.activity.UserServiceCardListActivity;
import com.lyun.user.databinding.FragmentUserCenterBinding;
import com.lyun.user.eventbusmessage.EventIntentActivityMessage;
import com.lyun.user.eventbusmessage.EventToastMessage;
import com.lyun.user.viewmodel.UserCenterFragmentViewModel;
import com.lyun.utils.GlideUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class UserCenterFragment extends MvvmFragment<FragmentUserCenterBinding, UserCenterFragmentViewModel> {
    private final int IMAGE_HEADER = 10002;
    public UserCenterFragment() {
        // Required empty public constructor
    }

    public static UserCenterFragment newInstance() {
        UserCenterFragment fragment = new UserCenterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @NonNull
    @Override
    protected UserCenterFragmentViewModel createViewModel() {
        return new UserCenterFragmentViewModel().setPropertyChangeListener(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_user_center;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResetPasswordResult(EventIntentActivityMessage message) {
        if (message.getMessage().getStringExtra("flag").equals("AccountBindingActivity")) {
            startActivity(new Intent(getContext(), AccountBindingActivity.class));
        } else if (message.getMessage().getStringExtra("flag").equals("UserServiceCardListActivity")) {
            startActivity(new Intent(getContext(), UserServiceCardListActivity.class));
        } else if (message.getMessage().getStringExtra("flag").equals("ImageCropActivity")) {
            startActivityForResult(new Intent(getContext(), ImageHeaderActivity.class), IMAGE_HEADER);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onToastText(EventToastMessage message) {
        Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE_HEADER && resultCode == Activity.RESULT_OK && data != null) {
            String path = data.getStringExtra(ImageCropActivity.SAVE_PATH);

            if (path != null)
                GlideUtils.showImage(getContext(), getFragmentViewDataBinding().userCenterAvatar, new File(path));
//                getFragmentViewDataBinding().userCenterAvatar.setImageBitmap(getLoacalBitmap(path));
        }
    }

    public static Bitmap getUrlBitmap(String url) {
        try {
            InputStream fis = new URL(url).openStream();
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
