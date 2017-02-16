package com.lyun.library.mvvm.bindingadapter.checkbox;

import android.databinding.BindingAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.lyun.library.mvvm.command.RelayCommand;

/**
 * @author Gordon
 * @since 2017/2/15
 * do()
 */

public class ViewBindAdapter {
    @BindingAdapter("checkListener")
    public static void setOnCheckedListener(CheckBox checkBox, final RelayCommand<Boolean> relayCommand){
        checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                relayCommand.execute(isChecked);
            }
        });
    }
}
