package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.graphics.Bitmap;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.bindingadapter.zoomableimageview.ViewBindAdapter;

/**
 * @author Gordon
 * @since 2017/8/2
 * do()
 */

public class ImageCropViewModel extends ViewModel {
    public final ObservableField<Bitmap> cropBitmap = new ObservableField<>();
    public final ObservableField<ViewBindAdapter.OutPutInfo> outPutInfo = new ObservableField<>();

    public ImageCropViewModel() {
        init();
    }

    private void init() {
    }
}
