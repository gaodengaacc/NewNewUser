package com.lyun.user.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.view.View;

import com.lyun.library.mvvm.observable.ObservableActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.activity.MainActivity;

/**
 * Created by 郑成裕 on 2017/1/5.
 */

public class ServiceCategoryActivityViewModel extends ViewModel {
    public final ObservableInt visibilityNormalService = new ObservableInt();
    public final ObservableInt visibilityTravel = new ObservableInt();
    public final ObservableInt visibilityHotel = new ObservableInt();
    public final ObservableInt visibilityShopping = new ObservableInt();
    public final ObservableInt visibilityEat = new ObservableInt();
    public final ObservableInt visibilityUrgency = new ObservableInt();
    String languageCategory;
    Intent intent = new Intent(getContext(), MainActivity.class);
    Bundle bundle = new Bundle();


    public ServiceCategoryActivityViewModel(Context context, GeneralToolbarViewModel.ToolbarViewModel toolbarViewModel, String languageCategory) {
        super(context);
        this.languageCategory = languageCategory;
        toolbarViewModel.title.set("服务类别");
        toolbarViewModel.dividerVisibility.set(View.VISIBLE);
        toolbarViewModel.onBackClick.set(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        initData();
        if (languageCategory.equals("普通服务")) {
            visibilityNormalService.set(View.VISIBLE);
        } else if (languageCategory.equals("出行")) {
            visibilityTravel.set(View.VISIBLE);
        } else if (languageCategory.equals("酒店入住")) {
            visibilityHotel.set(View.VISIBLE);
        } else if (languageCategory.equals("购物")) {
            visibilityShopping.set(View.VISIBLE);
        } else if (languageCategory.equals("吃饭")) {
            visibilityEat.set(View.VISIBLE);
        } else if (languageCategory.equals("紧急情况")) {
            visibilityUrgency.set(View.VISIBLE);
        }
    }

    public void initData() {//初始化勾选图标
        visibilityNormalService.set(View.INVISIBLE);
        visibilityTravel.set(View.INVISIBLE);
        visibilityHotel.set(View.INVISIBLE);
        visibilityShopping.set(View.INVISIBLE);
        visibilityEat.set(View.INVISIBLE);
        visibilityUrgency.set(View.INVISIBLE);
    }

    private void returnResult(String string) {
        bundle.putString("category", string);
        intent.putExtras(bundle);
        getActivity().finish(new ObservableActivity.Result(Activity.RESULT_OK, intent));
    }

    public void normalServiceRelativeLayoutClick(View view) {
        returnResult("普通服务");
    }

    public void travelRelativeLayoutClick(View view) {
        returnResult("出行");
    }

    public void hotelRelativeLayoutClick(View view) {
        returnResult("酒店入住");
    }

    public void shoppingRelativeLayoutClick(View view) {
        returnResult("购物");
    }

    public void eatRelativeLayoutClick(View view) {
        returnResult("吃饭");
    }

    public void urgencyRelativeLayoutClick(View view) {
        returnResult("紧急情况");
    }
}
