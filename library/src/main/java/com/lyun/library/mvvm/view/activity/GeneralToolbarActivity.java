package com.lyun.library.mvvm.view.activity;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.ViewStubCompat;

import com.lyun.library.R;
import com.lyun.library.databinding.ToolbarGeneralBinding;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;

public abstract class GeneralToolbarActivity<VDB extends ViewDataBinding, VM extends ViewModel>
        extends DiyTitleActivity<ToolbarGeneralBinding, VDB, GeneralToolbarViewModel.ToolbarViewModel, VM>
        implements ViewStubCompat.OnInflateListener {

    @Override
    protected int getTitleLayoutId() {
        return R.layout.toolbar_general;
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        return new GeneralToolbarViewModel.ToolbarViewModel().setPropertyChangeListener(this);
    }

}
