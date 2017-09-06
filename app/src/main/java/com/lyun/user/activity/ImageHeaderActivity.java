package com.lyun.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.Account;
import com.lyun.user.AppApplication;
import com.lyun.user.Constants;
import com.lyun.user.R;
import com.lyun.user.databinding.ImageHeaderLayoutBinding;
import com.lyun.user.dialog.SelectImageDialog;
import com.lyun.user.eventbusmessage.EventSelectImageItemMessage;
import com.lyun.user.viewmodel.ImageHeaderViewModel;
import com.lyun.user.viewmodel.SelectImageDialogViewModel;
import com.netease.nim.uikit.common.util.media.ImageUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Gordon
 * @since 2017/8/3
 * do()
 */

public class ImageHeaderActivity extends GeneralToolbarActivity<ImageHeaderLayoutBinding, ImageHeaderViewModel> {
    private SelectImageDialog selectImageDialog;
    private SelectImageDialogViewModel dialogViewModel;
    private int PICK_IMAGE = 10000;
    private int CROP_IMAGE = 10001;
    public static final String IMAGE_PATH = "image_path";
    private String savePath;
    private Bitmap imageBitmap;

    @Override
    protected int getBodyLayoutId() {
        return R.layout.image_header_layout;
    }

    @NonNull
    @Override
    protected ImageHeaderViewModel createBodyViewModel() {

        return new ImageHeaderViewModel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bitmap src = BitmapDecoder.decodeSampledForDisplay(AppApplication.getAppFileDirs().image().getAbsolutePath()+"/header.jpg")
//
        Glide.with(this).load(Constants.IMAGE_BASE_URL + Account.preference().getUserHeader())
                .asBitmap().skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imageBitmap = resource;
                        ImageUtil.rotateBitmapInNeeded(ImageCropActivity.SAVE_PATH, resource);
                        getBodyViewDataBinding().headerImage.setImageBitmap(resource);
                    }
        });
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (imageBitmap != null) {
            imageBitmap.recycle();
            imageBitmap = null;
        }


    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("头像");
        viewModel.functionImage.set(R.mipmap.icon_common_top_more);
        viewModel.onFunctionClick.set(view -> {
                    if (dialogViewModel == null)
                        dialogViewModel = new SelectImageDialogViewModel();
                    if (selectImageDialog == null)
                        selectImageDialog = new SelectImageDialog(this, dialogViewModel);
                    selectImageDialog.show();
                }
        );
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDialogItemClick(EventSelectImageItemMessage message) {
        switch (message.getMessage()) {
            case 0:
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
                break;
            case 1:
                saveBitmap(imageBitmap);
                break;
            default:
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            c.close();
            Intent intent = new Intent(this, ImageCropActivity.class);
            intent.putExtra(IMAGE_PATH,imagePath);
            startActivityForResult(intent,CROP_IMAGE);
        }else if(requestCode == CROP_IMAGE && resultCode == Activity.RESULT_OK && data!=null){
            setResult(Activity.RESULT_OK,data);
            finish();
        }
    }

    public void saveBitmap(Bitmap bitmap) {
        savePath = AppApplication.getAppFileDirs().image().getAbsolutePath() + "/header/";
        File appDir = new File(savePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(this, "图片已保存在" + savePath + "文件夹下", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, "图片保存失败", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, "图片保存失败", Toast.LENGTH_LONG).show();
        }

    }

}

