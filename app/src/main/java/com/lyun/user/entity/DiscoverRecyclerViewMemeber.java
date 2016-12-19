package com.lyun.user.entity;

import com.lyun.user.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郑成裕 on 2016/12/16.
 */

public class DiscoverRecyclerViewMemeber {
    public List<String> listTitle;
    public List<String> listContent;
    public int[] imagesId;

    public void initRecyclerView() {
        imagesId = new int[]{R.mipmap.image1, R.mipmap.image2, R.mipmap.image3};
        listTitle = new ArrayList<String>();
        listContent = new ArrayList<String>();
        listTitle.add("一篇关于英国的指南");
        listContent.add("在英国可以感受西方文化,她,迷人又多面!");
        listTitle.add("一篇关于英国的指南");
        listContent.add("在英国可以感受西方文化,她,迷人又多面!");
        listTitle.add("一篇关于英国的指南");
        listContent.add("在英国可以感受西方文化,她,迷人又多面!");
    }
}
