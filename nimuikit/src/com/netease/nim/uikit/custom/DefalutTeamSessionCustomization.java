package com.netease.nim.uikit.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.session.SessionCustomization;
import com.netease.nim.uikit.session.ToolbarCustomization;
import com.netease.nim.uikit.team.model.TeamExtras;
import com.netease.nim.uikit.team.model.TeamRequestCode;
import com.netease.nimlib.sdk.team.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * SessionCustomization 可以实现聊天界面定制项：
 * 1. 聊天背景 <br>
 * 2. 加号展开后的按钮和动作，如自定义消息 <br>
 * 3. ActionBar右侧按钮。
 * <p>
 * DefalutTeamSessionCustomization 提供默认的群聊界面定制，添加了ActionBar右侧按钮，用于跳转群信息界面
 * <p>
 * Created by hzchenkang on 2016/12/20.
 */

public class DefalutTeamSessionCustomization extends SessionCustomization {

    public DefalutTeamSessionCustomization() {

        ToolbarCustomization toolbarCustomization = new ToolbarCustomization();

        // ActionBar右侧按钮，跳转至群信息界面
        ToolbarCustomization.OptionsButton infoButton = new ToolbarCustomization.OptionsButton() {
            @Override
            public void onClick(Context context, View view, String sessionId) {
                Team team = TeamDataCache.getInstance().getTeamById(sessionId);
                if (team != null && team.isMyTeam()) {
                    NimUIKit.startTeamInfo(context, sessionId);
                } else {
                    Toast.makeText(context, R.string.team_invalid_tip, Toast.LENGTH_SHORT).show();
                }
            }
        };
        infoButton.setIconId(R.drawable.nim_ic_message_actionbar_team);
        ArrayList<ToolbarCustomization.OptionsButton> buttons = new ArrayList<>();
        buttons.add(infoButton);
        toolbarCustomization.setOptionsButtons(buttons);
        setToolbarCustomization(toolbarCustomization);
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == TeamRequestCode.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String reason = data.getStringExtra(TeamExtras.RESULT_EXTRA_REASON);
                boolean finish = reason != null && (reason.equals(TeamExtras
                        .RESULT_EXTRA_REASON_DISMISS) || reason.equals(TeamExtras.RESULT_EXTRA_REASON_QUIT));
                if (finish) {
                    activity.finish(); // 退出or解散群直接退出多人会话
                }
            }
        }
    }

}
