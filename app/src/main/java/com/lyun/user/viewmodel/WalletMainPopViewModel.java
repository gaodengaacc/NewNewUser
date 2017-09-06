package com.lyun.user.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupWindow;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.adapter.WalletMainPopAdapter;
import com.lyun.user.api.response.FindLanguageResponse;
import com.lyun.user.dialog.WalletMainPopWindow;
import com.lyun.user.eventbusmessage.homefragment.EventHomePobDismissMessage;
import com.lyun.user.eventbusmessage.homefragment.EventSelectMessage;

import net.funol.databinding.watchdog.annotations.WatchThis;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Gordon
 * @since 2017/1/10
 * do()
 */

public class WalletMainPopViewModel extends ViewModel {
    public final ObservableField<BaseRecyclerAdapter> adapter = new ObservableField<>();
    public final ObservableField<List<ViewModel>> notifyData = new ObservableField<>();
    @WatchThis
    public final ObservableField<ShowData> isShow = new ObservableField();
    @WatchThis
    public final ObservableBoolean isDismiss = new ObservableBoolean();
    //设置LayoutManager
    public RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(AppApplication.getInstance());
    public static final String defaultLanguageCache = "[{\"id\":9,\"code\":\"110\",\"name\":\"民间借贷\",\"description\":\"民间借贷\"}]";

    public WalletMainPopViewModel(Context context,List<FindLanguageResponse> responses) {
        new WalletMainPopWindow(context, this).setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                EventBus.getDefault().post(new EventHomePobDismissMessage(true));
            }
        });
        init(responses);
    }

    private void init(List<FindLanguageResponse> languageResponses) {
        //1,首次充值-以15分钟为最小购买单元,购买价格为:45元/15分钟;\n2,续费充值-以5分钟为充值单元,购买价格为:15元/5分钟.
//        String languageStr = ACache.get(AppApplication.getInstance()).getAsString(Constants.Cache.SUPPORT_LANGUAGES);
//        List<FindLanguageResponse> languageResponses = new Gson().fromJson(languageStr == null ? defaultLanguageCache : languageStr, new TypeToken<List<FindLanguageResponse>>() {
//        }.getType());
        List<WalletMainPopDesItemViewModel> list = new ArrayList<>();
        for(FindLanguageResponse response:languageResponses){
            list.add(new WalletMainPopDesItemViewModel(response));
        }
        WalletMainPopAdapter popAdapter = new WalletMainPopAdapter(list, R.layout.item_wallet_main_popwindow);
        popAdapter.setItemClickListener((view, viewModels, position) -> {
            EventBus.getDefault().post(new EventSelectMessage(languageResponses.get(position)));
            ObservableNotifier.alwaysNotify(isDismiss,true);
            EventBus.getDefault().post(new EventHomePobDismissMessage(true));
        });
        adapter.set(popAdapter);
    }

    public void showAsDropDown(View v) {
        isShow.set(new ShowData(v));
    }

    public void showAsDropDown(View v, int xoff, int yof) {
        isShow.set(new ShowData(v, xoff, yof));
    }

    public void showAsDropDown(View v, int xoff, int yoff, int gravity) {
        isShow.set(new ShowData(v, xoff, yoff, gravity));
    }

    public void dismiss() {
      isDismiss.notifyChange();
    }
    public class ShowData {
        public View getAnchor() {
            return anchor;
        }

        public void setAnchor(View anchor) {
            this.anchor = anchor;
        }

        public int getXoff() {
            return xoff;
        }

        public void setXoff(int xoff) {
            this.xoff = xoff;
        }

        public int getYoff() {
            return yoff;
        }

        public void setYoff(int yoff) {
            this.yoff = yoff;
        }

        public int getGravity() {
            return gravity;
        }

        public void setGravity(int gravity) {
            this.gravity = gravity;
        }
        public  final static int DEFAULT_DATA= -1000;
        private View anchor;
        private int xoff = DEFAULT_DATA;
        private int yoff = DEFAULT_DATA;
        private int gravity = DEFAULT_DATA;
        public ShowData(View v, int xoff, int yoff, int gravity) {
            this.setAnchor(v);
            this.setXoff(xoff);
            this.setYoff(yoff);
            this.setGravity(gravity);
        }

        public ShowData(View v, int xoff, int yoff) {
            this.setAnchor(v);
            this.setXoff(xoff);
            this.setYoff(yoff);
        }

        public ShowData(View v) {
            this.setAnchor(v);
        }
    }
}
