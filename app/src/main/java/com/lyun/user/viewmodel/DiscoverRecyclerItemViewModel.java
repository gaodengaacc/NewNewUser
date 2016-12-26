package com.lyun.user.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.BR;
import com.lyun.user.fragment.DiscoverFragment;
import com.lyun.utils.ToastUtil;
import com.lyun.viewmodel.BaseViewModel;

/**
 * Created by 郑成裕 on 2016/12/16.
 */

public class DiscoverRecyclerItemViewModel extends ViewModel {
    public final ObservableField<String> listTitle = new ObservableField<>();
    public final ObservableField<String> listContent = new ObservableField<>();
    public final ObservableInt imageId = new ObservableInt();

    public DiscoverRecyclerItemViewModel(Context context) {
        super(context);
    }
   public DiscoverRecyclerItemViewModel(Context context,String listTitle,String listContent,int imageId){
       super(context);
       this.listTitle.set(listTitle);
       this.listContent.set(listContent);
       this.imageId.set(imageId);

   }
    public void init(int position){
        listTitle.set(listTitle.get()+position);
    }
}
