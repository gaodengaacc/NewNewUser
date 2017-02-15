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
import com.lyun.user.dialog.LanguagePickerDialog;

import java.util.ArrayList;

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

    private LanguageTextAdapter languageTextAdapter;
    ArrayList<Object> listLanguagePicker = new ArrayList<Object>();

    //设置选择时字体大小
    private int maxTextSize = 16;
    private int minTextSize = 14;

    //当前选择的位置及其内容
    private int currentPosition;
    private Object currentContent;

    //已选择的语种
    private String language = "";

    private PickLanguage pickLanguage;

    public LanguagePickerDialogViewModel(Context context) {
        super(context);
        initData();
        setDataOne(listLanguagePicker);
        new LanguagePickerDialog(getContext(), this);
    }

    private void initData() {
        languageTextAdapter = new LanguageTextAdapter(getContext(), listLanguagePicker, 0, maxTextSize, minTextSize);

        changedListener.set(onWheelChangedListener);
        scrollListener.set(onWheelScrollListener);

        initListData();//设置选项值
        adapter.set(languageTextAdapter);
        visibleItem.set(7);//设置item的显示数目
        currentItem.set(0);
        isCyclic.set(true);//设置循环
        foreground.set(R.mipmap.bg_wheel_divider);
    }

    private OnWheelChangedListener onWheelChangedListener = new OnWheelChangedListener() {//接口实现
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            String currentText = (String) languageTextAdapter.getItemText(wheel.getCurrentItem());
            setTextviewSize(currentText, languageTextAdapter);
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
            String currentText = (String) languageTextAdapter.getItemText(wheel.getCurrentItem());
            setTextviewSize(currentText, languageTextAdapter);
        }
    };

    private void initListData() {
        listLanguagePicker.add("英语");
        listLanguagePicker.add("日语");
        listLanguagePicker.add("法语");
        listLanguagePicker.add("德语");
        listLanguagePicker.add("俄语");
        listLanguagePicker.add("韩语");
        listLanguagePicker.add("意大利语");
        listLanguagePicker.add("西班牙语");
        listLanguagePicker.add("葡萄牙语");
        listLanguagePicker.add("泰语");
    }

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

    private void setDataOne(ArrayList<Object> listLanguagePicker) {
        if (listLanguagePicker != null && listLanguagePicker.size() > 0) {
            //设置默认为第一个
            currentPosition = 0;
            currentContent = listLanguagePicker.get(0);
            String currentText = (String) languageTextAdapter.getItemText(currentItem.get());
            language = currentText;
        }
    }

    public void textViewDone(View view) {
        if (pickLanguage != null) {
            pickLanguage.onPick(language);
            dismiss();
        }
    }

    public interface PickLanguage {
        public void onPick(String language);
    }

    //该方法为了让PickLanguage接口的方法在SpecialistTranslationFragmentViewModel中实现
    public void setPickLanguage(PickLanguage pickLanguage) {
        this.pickLanguage = pickLanguage;
    }

}
