package com.lyun.user.model;

import android.content.Context;

import com.lyun.model.BaseModel;
import com.lyun.user.viewmodel.DemoItemViewModel;
import com.lyun.viewmodel.BaseViewModel;

/**
 * @author Gordon
 * @since 2016/12/19
 * do(Item业务处理)
 */

public class DemoItemModel extends BaseModel {

    private DemoItemViewModel demoItemViewModel;
    public DemoItemModel(Context context, BaseViewModel viewModel) {
        super(context);
        demoItemViewModel = (DemoItemViewModel) viewModel;
    }

    @Override
    public void init() {

    }

    //处理Item业务逻辑方法
    public void doData(){
        if(demoItemViewModel.getLabel().equals("1")){
            demoItemViewModel.setIsShow(true);
        }else {
            demoItemViewModel.setIsShow(false);
        }
    }
}
