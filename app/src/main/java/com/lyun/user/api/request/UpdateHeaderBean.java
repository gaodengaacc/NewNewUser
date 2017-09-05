package com.lyun.user.api.request;

import java.io.File;

/**
 * @author Gordon
 * @since 2017/9/4
 * do()
 */

public class UpdateHeaderBean extends BaseRequestBean {
    public File getFile() {
        return userImage;
    }

    public void setFile(File file) {
        this.userImage = file;
    }

    private  File userImage;
}
