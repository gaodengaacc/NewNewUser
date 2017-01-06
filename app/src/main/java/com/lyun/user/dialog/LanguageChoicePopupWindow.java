package com.lyun.user.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lyun.user.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * Created by 郑成裕 on 2016/12/22.
 */

public class LanguageChoicePopupWindow extends PopupWindow implements View.OnClickListener {
    @BindView(R.id.wheelView_language_choice1)
    WheelView wheelViewLanguageChoice1;
    @BindView(R.id.wheelView_language_choice2)
    WheelView wheelViewLanguageChoice2;
    @BindView(R.id.textView_done)
    TextView textViewDone;
    @BindView(R.id.linearLayout_languageChoice)
    View linearLayoutLanguageChoice;

    private Context context;
    ArrayList<Object> listLanguageChoice = new ArrayList<Object>();
    private LanguageTextAdapter languageTextAdapter;
    private int maxTextSize = 25;
    private int minTextSize = 15;

    //当前选择的位置及其内容
    private int currentPosition;
    private Object currentContent;
    //已选择的语种
    private String language1;
    private String language2;
    private ChooseListener chooseListener;

    private View view;//popupwindow的view
    private LayoutInflater layoutInflater;

    private int width;//获取控件的宽度

    public LanguageChoicePopupWindow(Context context, int width) {
        super(context);
        this.context = context;
        this.width = width;

        layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.popupwindow_languages_choice, null);
        ButterKnife.bind(this, view);
        initData();
        setDataOne(listLanguageChoice);
    }


    private void initData() {
        languageTextAdapter = new LanguageTextAdapter(context, listLanguageChoice, 0, maxTextSize, minTextSize);
        wheelViewLanguageChoice1.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) languageTextAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, languageTextAdapter);
                currentPosition = wheel.getCurrentItem();
                currentContent = currentText;
                language1 = currentText;
            }
        });
        wheelViewLanguageChoice1.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) languageTextAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, languageTextAdapter);
            }
        });


        wheelViewLanguageChoice2.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) languageTextAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, languageTextAdapter);
                currentPosition = wheel.getCurrentItem();
                currentContent = currentText;
                language2 = currentText;
            }
        });
        wheelViewLanguageChoice2.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) languageTextAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, languageTextAdapter);
            }
        });


        listLanguageChoice.add("中文");
        listLanguageChoice.add("英文");
        listLanguageChoice.add("日文");
        listLanguageChoice.add("法文");
        listLanguageChoice.add("德文");
        listLanguageChoice.add("俄文");
        listLanguageChoice.add("韩文");
        listLanguageChoice.add("意大利语");
        listLanguageChoice.add("西班牙文");
        listLanguageChoice.add("葡萄牙语");
        listLanguageChoice.add("泰语");


        wheelViewLanguageChoice1.setVisibleItems(5);
        wheelViewLanguageChoice1.setViewAdapter(languageTextAdapter);
        wheelViewLanguageChoice1.setCurrentItem(0);
        wheelViewLanguageChoice1.setCyclic(true);//设置循环


        wheelViewLanguageChoice2.setVisibleItems(5);
        wheelViewLanguageChoice2.setViewAdapter(languageTextAdapter);
        wheelViewLanguageChoice2.setCurrentItem(0);
        wheelViewLanguageChoice2.setCyclic(true);

        textViewDone.setOnClickListener(this);
        linearLayoutLanguageChoice.setOnClickListener(this);

        setContentView(view);

        setWidth(width);//设置宽度
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        setBackgroundDrawable(dw);
        setFocusable(true);
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

    public void setDataOne(ArrayList<Object> listLanguageChoice) {
        this.listLanguageChoice = listLanguageChoice;
        if (listLanguageChoice != null && listLanguageChoice.size() > 0) {
            //设置默认为第一个
            currentPosition = 0;
            currentContent = listLanguageChoice.get(0);

            String currentText1 = (String) languageTextAdapter.getItemText(wheelViewLanguageChoice1.getCurrentItem());
            String currentText2 = (String) languageTextAdapter.getItemText(wheelViewLanguageChoice2.getCurrentItem());

            language1 = currentText1;
            language2 = currentText2;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView_done:
                if (chooseListener != null) {
                    if (language1.equals(language2)) {//判断母语和目标语言不能相同
                        chooseListener.onClick(language1, language2);
                    } else {
                        chooseListener.onClick(language1, language2);
                        dismiss();
                    }
                }
                break;
        }
//        dismiss();

    }

    public interface ChooseListener {
        public void onClick(String language1, String language2);
    }

    public void setChooseListener(ChooseListener chooseListener) {
        this.chooseListener = chooseListener;
    }

    //选择场景
    private class LanguageTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<Object> list;

        protected LanguageTextAdapter(Context context, ArrayList<Object> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_language_choice, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View convertView, ViewGroup parent) {
            View view = super.getItem(index, convertView, parent);
            return view;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }
    }

}
