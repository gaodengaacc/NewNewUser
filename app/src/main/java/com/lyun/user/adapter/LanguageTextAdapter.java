package com.lyun.user.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.lyun.user.R;
import com.lyun.user.api.response.FindLanguageResponse;

import java.util.List;

import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * Created by 郑成裕 on 2017/1/11.
 */

public class LanguageTextAdapter extends AbstractWheelTextAdapter {

    List<FindLanguageResponse> mDatas;

    public LanguageTextAdapter(Context context, List<FindLanguageResponse> datas, int currentItem, int maxsize, int minsize) {
        super(context, R.layout.item_language_picker, NO_RESOURCE, currentItem, maxsize, minsize);
        this.mDatas = datas;
        setItemTextResource(R.id.textView_tempValue);
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        View view = super.getItem(index, convertView, parent);
        return view;
    }

    @Override
    public String getItemText(int index) {
        return mDatas.get(index).getName();
    }

    @Override
    public int getItemsCount() {
        return mDatas.size();
    }
}
