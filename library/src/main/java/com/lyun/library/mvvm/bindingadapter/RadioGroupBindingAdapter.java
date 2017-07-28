package com.lyun.library.mvvm.bindingadapter;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.adapters.ListenerUtil;
import android.support.annotation.IdRes;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lyun.library.R;

public class RadioGroupBindingAdapter {

    @BindingAdapter("checkedItem")
    public static void setCheckedItem(RadioGroup radioGroup, int index) {
        final int oldIndex = getCurrentItemIndex(radioGroup);
        if (index == oldIndex) {
            return;
        }
        ((RadioButton) radioGroup.getChildAt(index)).setChecked(true);
    }

    @InverseBindingAdapter(attribute = "checkedItem", event = "checkedItemAttrChanged")
    public static int getCheckedItemInt(RadioGroup radioGroup) {
        return getCurrentItemIndex(radioGroup);
    }

    @BindingAdapter(value = {"onCheckedChanged", "checkedItemAttrChanged"}, requireAll = false)
    public static void addOnPageChangeListener(RadioGroup radioGroup, final RadioGroup.OnCheckedChangeListener checkedChangeListener,
                                               final InverseBindingListener currentItemAttrChanged) {
        final RadioGroup.OnCheckedChangeListener newValue;
        if (checkedChangeListener == null && currentItemAttrChanged == null) {
            newValue = null;
        } else {
            newValue = new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    if (checkedChangeListener != null) {
                        checkedChangeListener.onCheckedChanged(group, checkedId);
                    }
                    if (currentItemAttrChanged != null) {
                        currentItemAttrChanged.onChange();
                    }
                }
            };
        }
        final RadioGroup.OnCheckedChangeListener oldValue = ListenerUtil.trackListener(radioGroup, newValue, R.id.onCheckedChangeListener);
        if (oldValue != null) {
            radioGroup.setOnCheckedChangeListener(null);
        }
        if (newValue != null) {
            radioGroup.setOnCheckedChangeListener(newValue);
        }
    }

    public static int getCurrentItemIndex(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            if (((RadioButton) radioGroup.getChildAt(i)).isChecked()) {
                return i;
            }
        }
        return -1;
    }

}
