package com.lyun.user.viewmodel;

import android.databinding.Bindable;

import com.lyun.user.BR;
import com.lyun.viewmodel.BaseViewModel;

/**
 * Created by 郑成裕 on 2016/12/16.
 */

public class DiscoverRecyclerItemViewModel extends BaseViewModel {

    private String listTitle;
    private String listContent;
    private int imageId;

    public DiscoverRecyclerItemViewModel() {

    }

    public DiscoverRecyclerItemViewModel(String listTitle, String listContent, int imageId) {
        this.listContent = listContent;
        this.listTitle = listTitle;
        this.imageId = imageId;
    }

    @Bindable
    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
        notifyPropertyChanged(BR.listTitle);
    }

    @Bindable
    public String getListContent() {
        return listContent;

    }

    public void setListContent(String listContent) {
        this.listContent = listContent;
        notifyPropertyChanged(BR.listContent);
    }

    @Bindable
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
        notifyPropertyChanged(BR.imageId);
    }
}
