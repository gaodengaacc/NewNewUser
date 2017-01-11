package com.lyun.user.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lyun.user.R;
import com.lyun.user.adapter.LanguageTextAdapter;
import com.lyun.user.databinding.DialogLanguagePickerBinding;
import com.lyun.user.viewmodel.LanguagePickerDialogViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

/**
 * 目标语言选择Dialog
 * Created by 郑成裕 on 2017/1/6.
 */

public class LanguagePickerDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.wheelView_language_picker)
    WheelView wheelViewLanguagePicker;
    @BindView(R.id.textView_completed)
    TextView textViewCompleted;

    private Context context;

    ArrayList<Object> listLanguagePicker = new ArrayList<Object>();

    private LanguageTextAdapter languageTextAdapter;
    //设置选择时字体大小
    private int maxTextSize = 16;
    private int minTextSize = 14;

    //当前选择的位置及其内容
    private int currentPosition;
    private Object currentContent;

    //已选择的语种
    private String language;

    private PickLanguage pickLanguage;

    public LanguagePickerDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);//设置dialog显示位置
//        window.setWindowAnimations(R.style.bottom_menu_animation);

        DialogLanguagePickerBinding viewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_language_picker, null, false);
        LanguagePickerDialogViewModel viewModel = new LanguagePickerDialogViewModel(context);
        viewBinding.setMvvm(viewModel);
        // 设置LanguagePickerDialog的View
        this.setContentView(viewBinding.getRoot());

        //宽度全屏
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = display.getWidth();
        getWindow().setAttributes(layoutParams);
        //点击dialog外部消失
        setCanceledOnTouchOutside(true);

        ButterKnife.bind(this);
        initData();
        setDataOne(listLanguagePicker);


    }

    private void initData() {
        languageTextAdapter = new LanguageTextAdapter(context, listLanguagePicker, 0, maxTextSize, minTextSize);
        wheelViewLanguagePicker.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) languageTextAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, languageTextAdapter);
                currentPosition = wheel.getCurrentItem();
                currentContent = currentText;
                language = currentText;
            }
        });
        wheelViewLanguagePicker.addScrollingListener(new OnWheelScrollListener() {
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
        wheelViewLanguagePicker.setViewAdapter(languageTextAdapter);
        wheelViewLanguagePicker.setVisibleItems(7);
        wheelViewLanguagePicker.setCurrentItem(0);
        wheelViewLanguagePicker.setCyclic(true);//设置循环
        wheelViewLanguagePicker.setWheelForeground(R.mipmap.bg_wheel_divider);//设置选中时背景
        wheelViewLanguagePicker.setBackgroundResource(R.drawable.bg_wheel_view);

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

    private void setDataOne(ArrayList<Object> listLanguagePicker) {
        if (listLanguagePicker != null && listLanguagePicker.size() > 0) {
            //设置默认为第一个
            currentPosition = 0;
            currentContent = listLanguagePicker.get(0);
            String currentText = (String) languageTextAdapter.getItemText(wheelViewLanguagePicker.getCurrentItem());
            language = currentText;
        }
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

    @Override
    @OnClick(R.id.textView_completed)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView_completed:
                if (pickLanguage != null) {
                    pickLanguage.onClick(language);
                    dismiss();
                }
                break;
        }
    }

    public interface PickLanguage {
        public void onClick(String language);
    }

    public void setPickLanguage(PickLanguage pickLanguage) {
        this.pickLanguage = pickLanguage;
    }


}
