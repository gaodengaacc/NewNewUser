package com.lyun.user.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.user.Account;
import com.lyun.user.Constants;
import com.lyun.user.GlideApp;
import com.lyun.user.R;
import com.lyun.user.activity.AccountBindingActivity;
import com.lyun.user.activity.AddressManageActivity;
import com.lyun.user.activity.AfterSaleServiceActivity;
import com.lyun.user.activity.ImageCropActivity;
import com.lyun.user.activity.ImageHeaderActivity;
import com.lyun.user.activity.UserServiceCardListActivity;
import com.lyun.user.databinding.FragmentUserCenterBinding;
import com.lyun.user.eventbusmessage.homefragment.EventMainIntentActivityMessage;
import com.lyun.user.eventbusmessage.mainactivity.EventMainProgressMessage;
import com.lyun.user.eventbusmessage.mainactivity.EventMainToastMessage;
import com.lyun.user.model.MultipartModel;
import com.lyun.user.viewmodel.UserCenterFragmentViewModel;
import com.lyun.utils.GlideUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        GlideApp.with(this)
                .asBitmap()
                .load(Constants.IMAGE_BASE_URL + Account.preference().getUserHeader())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        getFragmentViewDataBinding().userCenterAvatar.setImageBitmap(resource);
                    }
                });
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
    public void onResetPasswordResult(EventMainIntentActivityMessage message) {
        if (message.getMessage().getStringExtra("flag").equals("AccountBindingActivity")) {
            startActivity(new Intent(getContext(), AccountBindingActivity.class));
        } else if (message.getMessage().getStringExtra("flag").equals("UserServiceCardListActivity")) {
            startActivity(new Intent(getContext(), UserServiceCardListActivity.class));
        } else if (message.getMessage().getStringExtra("flag").equals("ImageCropActivity")) {
            startActivityForResult(new Intent(getContext(), ImageHeaderActivity.class), IMAGE_HEADER);
        } else if (message.getMessage().getStringExtra("flag").equals("AddressManageActivity")) {
            startActivity(new Intent(getContext(), AddressManageActivity.class));
        } else if (message.getMessage().getSerializableExtra("flag").equals("AfterSaleServiceActivity")) {
            AfterSaleServiceActivity.start(getActivity());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE_HEADER && resultCode == Activity.RESULT_OK && data != null) {
            String path = data.getStringExtra(ImageCropActivity.SAVE_PATH);

//            if (path != null)
//            GlideUtils.showImage(getContext(), getFragmentViewDataBinding().userCenterAvatar, new File(path));
//                getFragmentViewDataBinding().userCenterAvatar.setImageBitmap(getLoacalBitmap(path));
            updateHeader(path);

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

    public void updateHeader(String path) {
        EventBus.getDefault().post(new EventMainProgressMessage(true));
        new MultipartModel().upHeader(path)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                    EventBus.getDefault().post(new EventMainProgressMessage(false));
                    if (apiResult.isSuccess()) {
                        EventBus.getDefault().post(new EventMainToastMessage("上传成功"));
                        GlideUtils.showImage(getContext(), getFragmentViewDataBinding().userCenterAvatar, Constants.IMAGE_BASE_URL + apiResult.getContent(), true);
                        Account.preference().setUserHeader(String.valueOf(apiResult.getContent()));
                    } else
                        EventBus.getDefault().post(new EventMainToastMessage(apiResult.getDescribe()));

                }, throwable -> {
                    EventBus.getDefault().post(new EventMainToastMessage(throwable.getMessage()));
                    EventBus.getDefault().post(new EventMainProgressMessage(false));
                });
    }
}
