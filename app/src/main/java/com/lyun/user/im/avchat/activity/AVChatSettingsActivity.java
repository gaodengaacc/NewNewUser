package com.lyun.user.im.avchat.activity;

import android.os.Bundle;

import com.lyun.user.R;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;

/**
 * Created by liuqijun on 7/19/16.
 * 注意:全局配置,不区分用户
 */
public class AVChatSettingsActivity extends UI {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.avchat_settings_layout);

        ToolBarOptions options = new ToolBarOptions();
        options.setTitleId(R.string.nrtc_settings);
        setToolBar(R.id.toolbar, options);


    }

}
