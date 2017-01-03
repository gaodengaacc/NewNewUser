package com.lyun.user.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.lyun.library.mvvm.observable.ObservableActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.activity.ServiceCategoryActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 郑成裕 on 2016/12/30.
 */

public class SpecialistTranslationFragmentViewModel extends ViewModel {
    public final ObservableField<Integer> imageViewModelChange = new ObservableField<>();
    public final ObservableField<Integer> modelChange = new ObservableField<>();
    public final ObservableField<String> textViewModelChange = new ObservableField<>();
    private boolean mCommunicationModel = false;
    public final ObservableField<String> textViewCategoryChange = new ObservableField<>();//服务类型
    private final int REQUEST_CODE = 10000;

    public SpecialistTranslationFragmentViewModel(Context context) {
        super(context);
    }

    public void initData() {
        modelChange.set(R.mipmap.call_phone);
        imageViewModelChange.set(R.drawable.image_call_selector);
        textViewModelChange.set("语音呼叫");
        textViewCategoryChange.set("普通服务");
    }

    public void languageLinearLayoutClick(View view) {
//        LanguageChoicePopupWindow languageChoicePopupWindow = new LanguageChoicePopupWindow(getActivity(), linearLayoutLanguage.getWidth());//获取控件宽度
    }

    public void categoryRelativeLayoutClick(View view) {
//        Intent intent = new Intent(getContext(), ServiceCategoryActivity.class);

//        intent.putExtra("languageCategory", textViewCategoryChange.getText().toString());//传递服务类别
        ObservableActivity.Request request = new ObservableActivity.Request(REQUEST_CODE, new Intent(getContext(), ServiceCategoryActivity.class)
                .putExtra("languageCategory", textViewCategoryChange.get().toString()));
        getActivity().startActivityForResult(request);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (!(bundle.equals("")) && !(bundle == null)) {
                textViewCategoryChange.set(bundle.getString("category"));
            }
        }
    }

    public void modelChangeImageViewClick(View view) {
        if (!mCommunicationModel) {
            modelChange.set(R.mipmap.call_phone);
            imageViewModelChange.set(R.drawable.image_call_selector);
            textViewModelChange.set("语音呼叫");
            mCommunicationModel = true;
        } else {
            modelChange.set(R.mipmap.call_picture);
            imageViewModelChange.set(R.drawable.image_picture_selector);
            textViewModelChange.set("图文翻译");
            mCommunicationModel = false;
        }
    }
}
