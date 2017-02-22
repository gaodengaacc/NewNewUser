package com.lyun.user.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import android.widget.TextView;

import com.lyun.library.mvvm.viewmodel.DialogViewModel;
import com.lyun.user.R;
import com.lyun.user.adapter.LanguageTextAdapter;
import com.lyun.user.api.response.FindLanguageResponse;
import com.lyun.user.dialog.LanguagePickerDialog;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

/**
 * Created by 郑成裕 on 2017/1/11.
 */

public class LanguagePickerDialogViewModel extends DialogViewModel {

    public final ObservableField<OnWheelChangedListener> changedListener = new ObservableField<>();
    public final ObservableField<OnWheelScrollListener> scrollListener = new ObservableField<>();
    public final ObservableField<LanguageTextAdapter> adapter = new ObservableField<>();
    public final ObservableInt visibleItem = new ObservableInt();
    public final ObservableInt currentItem = new ObservableInt();
    public final ObservableField<Boolean> isCyclic = new ObservableField<>();
    public final ObservableInt foreground = new ObservableInt();
    public final ObservableBoolean isShow = new ObservableBoolean();

    private LanguageTextAdapter mLanguageAdapter;
    private List<FindLanguageResponse> mLanguageDatas = new ArrayList<>();

    //设置选择时字体大小
    private int maxTextSize = 16;
    private int minTextSize = 14;

    //当前选择的位置及其内容
    private int currentPosition;
    private Object currentContent;

    //已选择的语种
    private String language = "";

    private OnLanguagePickedListener onLanguagePickedListener;

    public LanguagePickerDialogViewModel(Context context,List<FindLanguageResponse> languageDatas) {
        super(context);
        this.mLanguageDatas = languageDatas;
        initData();
        setDataOne(languageDatas);
        new LanguagePickerDialog(getContext(), this);
    }

    private void initData() {
        mLanguageAdapter = new LanguageTextAdapter(getContext(), mLanguageDatas, 0, maxTextSize, minTextSize);
        changedListener.set(onWheelChangedListener);
        scrollListener.set(onWheelScrollListener);

//        initListData();//设置选项值
        adapter.set(mLanguageAdapter);
        visibleItem.set(7);//设置item的显示数目
        currentItem.set(0);
        isCyclic.set(true);//设置循环
        foreground.set(R.mipmap.bg_wheel_divider);
    }

    private OnWheelChangedListener onWheelChangedListener = new OnWheelChangedListener() {//接口实现
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            String currentText = (String) mLanguageAdapter.getItemText(wheel.getCurrentItem());
            setTextviewSize(currentText, mLanguageAdapter);
            currentPosition = wheel.getCurrentItem();
            currentContent = currentText;
            language = currentText;
        }
    };
    private OnWheelScrollListener onWheelScrollListener = new OnWheelScrollListener() {//接口实现
        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            String currentText = (String) mLanguageAdapter.getItemText(wheel.getCurrentItem());
            setTextviewSize(currentText, mLanguageAdapter);
        }
    };

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, LanguageTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTextViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textView = (TextView) arrayList.get(i);
            currentText = textView.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textView.setTextSize(maxTextSize);
            } else {
                textView.setTextSize(minTextSize);
            }
        }
    }

    private void setDataOne(List<FindLanguageResponse> listLanguagePicker) {
        if (listLanguagePicker != null && listLanguagePicker.size() > 0) {
            //设置默认为第一个
            currentPosition = 0;
            currentContent = listLanguagePicker.get(0);
            String currentText = mLanguageAdapter.getItemText(currentItem.get());
            language = currentText;
        }
    }

    public void textViewDone(View view) {
        if (onLanguagePickedListener != null) {
            onLanguagePickedListener.onPick(language);
            dismiss();
        }
    }

    public interface OnLanguagePickedListener {
        void onPick(String language);
    }

    //该方法为了让PickLanguage接口的方法在SpecialistTranslationFragmentViewModel中实现
    public void setOnLanguagePickedListener(OnLanguagePickedListener onLanguagePickedListener) {
        this.onLanguagePickedListener = onLanguagePickedListener;
    }

}
