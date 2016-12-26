package com.lyun.user.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.lyun.library.mvvm.viewmodel.ViewModel;

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
