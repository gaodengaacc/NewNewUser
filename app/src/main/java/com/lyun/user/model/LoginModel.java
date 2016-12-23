package com.lyun.user.model;

import com.lyun.library.mvvm.model.Model;

import java.util.Random;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public class LoginModel extends Model {

    public String getButtonText() {
        return new Random().nextInt() + "";
    }

}
