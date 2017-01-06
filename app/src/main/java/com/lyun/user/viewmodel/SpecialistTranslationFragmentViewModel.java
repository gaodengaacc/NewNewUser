package com.lyun.user.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.lyun.library.mvvm.bindingadapter.linearlayout.ViewBindingAdapter;
import com.lyun.library.mvvm.observable.ObservableActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.activity.ServiceCategoryActivity;
import com.lyun.user.dialog.LanguageChoicePopupWindow;
import com.lyun.user.dialog.LanguagePickerDialog;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 郑成裕 on 2016/12/30.
 */

public class SpecialistTranslationFragmentViewModel extends ViewModel {
    public final ObservableInt imageViewModelChange = new ObservableInt();
    public final ObservableInt modelChange = new ObservableInt();
    public final ObservableField<String> textViewModelChange = new ObservableField<>();
    private boolean mCommunicationModel = false;
    public final ObservableField<String> textViewCategoryChange = new ObservableField<>();//服务类型
    public final ObservableField<Boolean> showPopupWindow = new ObservableField<>();
    private final int REQUEST_CODE = 10000;

    public final ObservableField<String> textViewLanguage1 = new ObservableField<>();
    public final ObservableField<String> textViewLanguage2 = new ObservableField<>();
    public final ObservableField<ViewTreeObserver.OnGlobalLayoutListener> linearLayoutListener = new ObservableField<>();

    public final ObservableInt textViewColor1 = new ObservableInt();
    public final ObservableInt textViewColor2 = new ObservableInt();
    int linearLayoutWidth;//控件宽度

    public SpecialistTranslationFragmentViewModel(Context context) {

        super(context);
        initData();
    }

    public void initData() {
        modelChange.set(R.mipmap.radio_green_fragment_specialist_translation);
        imageViewModelChange.set(R.mipmap.call_fragment_specialist_translation);
        textViewModelChange.set("语音呼叫");
        textViewColor1.set(Color.parseColor("#40d12d"));
        textViewColor2.set(Color.parseColor("#333333"));
//        textViewCategoryChange.set("普通服务");
//        showPopupWindow.set(true);
//        textViewLanguage1.set("中文");
//        textViewLanguage2.set("英文");
    }

    //获取linearlayout的宽度
    public ViewBindingAdapter.GetWidthListener listener = new ViewBindingAdapter.GetWidthListener() {
        @Override
        public void getWidthListening(int width) {
            linearLayoutWidth = width;
        }
    };
    //选取母语和要翻译的语言
    public LanguageChoicePopupWindow.ChooseListener chooseListener = new LanguageChoicePopupWindow.ChooseListener() {
        @Override
        public void onClick(String language1, String language2) {
            if (language1.equals(language2)) {
                Toast.makeText(getContext(), "母语和目标语言不能相同", Toast.LENGTH_SHORT).show();
            } else {
                textViewLanguage1.set(language1);
                textViewLanguage2.set(language2);
            }
        }
    };

    public void languageLinearLayoutClick(View view) {
        if (showPopupWindow.get() == true) {
            showPopupWindow.set(false);
        } else {
            showPopupWindow.set(true);
        }
    }

    public void languagePickerLinearLayoutClick(View view) {
        LanguagePickerDialog languagePickerDialog = new LanguagePickerDialog(getContext());
        languagePickerDialog.show();
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
            if (!(data == null) && !(data.equals(""))) {
                Bundle bundle = data.getExtras();
                if (!(bundle.equals("")) && !(bundle == null)) {
                    textViewCategoryChange.set(bundle.getString("category"));
                }
            }
        }
    }

    public void modelChangeImageViewClick(View view) {
        if (!mCommunicationModel) {
            modelChange.set(R.mipmap.radio_green_fragment_specialist_translation);
            imageViewModelChange.set(R.mipmap.call_fragment_specialist_translation);
            textViewModelChange.set("语音呼叫");
            textViewColor1.set(Color.parseColor("#40d12d"));
            textViewColor2.set(Color.parseColor("#333333"));
            mCommunicationModel = true;
        } else {
            modelChange.set(R.mipmap.radio_brown_fragment_specialist_translation);
            imageViewModelChange.set(R.mipmap.picture_fragment_specialist_translation);
            textViewModelChange.set("图文翻译");
            textViewColor1.set(Color.parseColor("#333333"));
            textViewColor2.set(Color.parseColor("#ffb900"));
            mCommunicationModel = false;
        }
    }
}
