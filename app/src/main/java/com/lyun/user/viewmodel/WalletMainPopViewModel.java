package com.lyun.user.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupWindow;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.adapter.WalletMainPopAdapter;
import com.lyun.user.dialog.WalletMainPopWindow;

import net.funol.databinding.watchdog.annotations.WatchThis;

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
    @WatchThis
    public final ObservableField<PopupWindow.OnDismissListener> onDismissListener = new ObservableField();

    //设置LayoutManager
    public RelayCommand<RecyclerView> recyclerViewRelayCommand = new RelayCommand<RecyclerView>(recyclerView -> {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    });

    public WalletMainPopViewModel(Context context) {
        new WalletMainPopWindow(context, this);
        init();
    }

    private void init() {
        List<WalletMainPopDesItemViewModel> list = new ArrayList<>();
        list.add(new WalletMainPopDesItemViewModel("购买说明"));
        for (int i = 1; i < 4; i++) {
            list.add(new WalletMainPopDesItemViewModel(i + ",****************"));
        }
        list.add(new WalletMainPopDesItemViewModel("费率说明"));
        for (int i = 1; i < 5; i++) {
            list.add(new WalletMainPopDesItemViewModel(i + ",****************"));
        }
        WalletMainPopAdapter popAdapter = new WalletMainPopAdapter(list, R.layout.item_wallet_main_popwindow);
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
    public void setOnDismissListener(PopupWindow.OnDismissListener listener){
         onDismissListener.set(listener);
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
