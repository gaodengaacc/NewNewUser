package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.graphics.Bitmap;

import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * @author Gordon
 * @since 2017/8/3
 * do()
 */

public class ImageHeaderViewModel extends ViewModel {
    public final ObservableField<Bitmap> headerBitmap = new ObservableField<>();

    public ImageHeaderViewModel() {
        init();
    }

    private void init() {
    }
}
