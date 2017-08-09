package com.lyun.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.databinding.ImageCropLayoutBinding;
import com.lyun.user.viewmodel.ImageCropViewModel;
import com.lyun.utils.Screen;
import com.netease.nim.uikit.common.util.media.BitmapDecoder;
import com.netease.nim.uikit.common.util.media.ImageUtil;

/**
 * @author Gordon
 * @since 2017/8/2
 * do()
 */

public class ImageCropActivity extends GeneralToolbarActivity<ImageCropLayoutBinding,ImageCropViewModel>{
    private String savePath;
    public static final String SAVE_PATH = "save_path";
    @Override
    protected int getBodyLayoutId() {
        return R.layout.image_crop_layout;
    }

    @NonNull
    @Override
    protected ImageCropViewModel createBodyViewModel() {
        return new ImageCropViewModel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String path = getIntent().getStringExtra(ImageHeaderActivity.IMAGE_PATH);
        Bitmap src = BitmapDecoder.decodeSampledForDisplay(path);
        src = ImageUtil.rotateBitmapInNeeded(path, src);
        getBodyViewDataBinding().cropImage.setOutput(Screen.getWidthPixels(this)-30,Screen.getWidthPixels(this)-30);
        getBodyViewDataBinding().cropImage.setImageBitmap(src);
        savePath = AppApplication.getAppFileDirs().image().getAbsolutePath()+"/header.jpg";
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("裁剪");
        viewModel.onBackClick.set(view -> finish());
        viewModel.function.set("使用");
        viewModel.onFunctionClick.set(view ->{
            boolean isSuccess = getBodyViewDataBinding().cropImage.saveCroppedIamge(savePath);
            if(isSuccess){
                setResult(Activity.RESULT_OK,new Intent().putExtra(SAVE_PATH,savePath));
                finish();
            }
        });
        return viewModel;
    }
}
