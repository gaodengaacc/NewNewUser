package com.lyun.library.mvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.lyun.library.mvvm.model.Model;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */
public class GeneralToolbarViewModel extends ViewModel {

    public GeneralToolbarViewModel(Context context) {
        super(context);
    }

    public static class ToolbarViewModel extends ViewModel {

        public final ObservableField<String> back = new ObservableField<>();
        public final ObservableField<String> title = new ObservableField<>();
        public final ObservableField<String> fuction = new ObservableField<>();
        public final ObservableInt dividerVisibility = new ObservableInt();

        public final ObservableField<View.OnClickListener> onBackClick = new ObservableField<>();

        public ToolbarViewModel(Context context) {
            super(context);
            title.set("标题");
            dividerVisibility.set(View.VISIBLE);
        }

    }

    public static class ToobarModel extends Model {

    }
}
