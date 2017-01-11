package com.lyun.user.viewmodel;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.adapter.LanguageTextAdapter;
import com.lyun.user.databinding.DialogLanguagePickerBinding;

import java.util.ArrayList;

import butterknife.BindView;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

/**
 * Created by 郑成裕 on 2017/1/11.
 */

public class LanguagePickerDialogViewModel extends ViewModel {

    @BindView(R.id.textView_completed)
    TextView textViewCompleted;

    private LanguageTextAdapter languageTextAdapter;
    private Context context;
    ArrayList<Object> listLanguagePicker = new ArrayList<Object>();
    private DialogLanguagePickerBinding dialogLanguagePickerBinding;

    //设置选择时字体大小
    private int maxTextSize = 16;
    private int minTextSize = 14;

    //当前选择的位置及其内容
    private int currentPosition;
    private Object currentContent;

    //已选择的语种
    private String language;



    public LanguagePickerDialogViewModel(Context context) {
        super(context);
//        dialogLanguagePickerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_language_picker, null, false);
//        initData();
//        setDataOne(listLanguagePicker);
    }

    private void initData() {
        languageTextAdapter = new LanguageTextAdapter(context, listLanguagePicker, 0, maxTextSize, minTextSize);
        dialogLanguagePickerBinding.wheelViewLanguagePicker.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) languageTextAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, languageTextAdapter);
                currentPosition = wheel.getCurrentItem();
                currentContent = currentText;
                language = currentText;
            }
        });
        dialogLanguagePickerBinding.wheelViewLanguagePicker.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) languageTextAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, languageTextAdapter);
            }
        });
        initListData();//设置选项值
        dialogLanguagePickerBinding.wheelViewLanguagePicker.setViewAdapter(languageTextAdapter);
        dialogLanguagePickerBinding.wheelViewLanguagePicker.setVisibleItems(7);
        dialogLanguagePickerBinding.wheelViewLanguagePicker.setCurrentItem(0);
        dialogLanguagePickerBinding.wheelViewLanguagePicker.setCyclic(true);//设置循环
        dialogLanguagePickerBinding.wheelViewLanguagePicker.setWheelForeground(R.mipmap.bg_wheel_divider);//设置选中时背景
        dialogLanguagePickerBinding.wheelViewLanguagePicker.setBackgroundResource(R.drawable.bg_wheel_view);
    }

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
            String currentText = (String) languageTextAdapter.getItemText(dialogLanguagePickerBinding.wheelViewLanguagePicker.getCurrentItem());
            language = currentText;
        }
    }
}
