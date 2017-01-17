package com.lyun.library.mvvm.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.lyun.library.mvvm.model.Model;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */
public class GeneralToolbarViewModel extends ViewModel {

    public static class ToolbarViewModel extends ViewModel {

        public final ObservableField<String> back = new ObservableField<>();
        public final ObservableField<String> title = new ObservableField<>();
        public final ObservableField<String> function = new ObservableField<>();
        public final ObservableField<String> functionLeft = new ObservableField<>();
        public final ObservableInt functionLeftImage = new ObservableInt();
        public final ObservableInt  functionImage = new ObservableInt();
        public final ObservableInt functionLeftVisibility = new ObservableInt();
        public final ObservableInt dividerVisibility = new ObservableInt();

        public final ObservableField<View.OnClickListener> onBackClick = new ObservableField<>();
        public final ObservableField<View.OnClickListener> onFunctionClick = new ObservableField<>();
        public final ObservableField<View.OnClickListener> onFunctionLeftClick = new ObservableField<>();

        public ToolbarViewModel() {
            title.set("标题");
            dividerVisibility.set(View.INVISIBLE);
        }

    }

    public static class ToobarModel extends Model {

    }
}
