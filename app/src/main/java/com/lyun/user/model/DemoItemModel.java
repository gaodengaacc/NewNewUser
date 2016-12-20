package com.lyun.user.model;

import com.lyun.user.viewmodel.DemoItemViewModel;

/**
 * @author Gordon
 * @since 2016/12/19
 * do(Item业务处理)
 */

public class DemoItemModel {

    private DemoItemViewModel demoItemViewModel;

    public void setDemoItemViewModel(DemoItemViewModel demoItemViewModel) {
        this.demoItemViewModel = demoItemViewModel;
    }

    public DemoItemModel() {
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
