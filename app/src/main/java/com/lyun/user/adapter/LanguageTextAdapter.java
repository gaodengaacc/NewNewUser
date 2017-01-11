package com.lyun.user.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.lyun.user.R;

import java.util.ArrayList;

import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * Created by 郑成裕 on 2017/1/11.
 */

public class LanguageTextAdapter extends AbstractWheelTextAdapter {
    ArrayList<Object> list;

    public LanguageTextAdapter(Context context, ArrayList<Object> list, int currentItem, int maxsize, int minsize) {
        super(context, R.layout.item_language_picker, NO_RESOURCE, currentItem, maxsize, minsize);
        this.list = list;
        setItemTextResource(R.id.textView_tempValue);
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        View view = super.getItem(index, convertView, parent);
        return view;
    }

    @Override
    public CharSequence getItemText(int index) {
        return list.get(index) + "";
    }

    @Override
    public int getItemsCount() {
        return list.size();
    }
}
