package com.lyun.user.model;

import com.lyun.library.mvvm.model.BaseModel;

import java.util.Random;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public class LoginModel extends BaseModel {

    public String getButtonText() {
        return new Random().nextInt() + "";
    }

}
