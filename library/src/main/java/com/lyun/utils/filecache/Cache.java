package com.lyun.utils.filecache;

/**
 * @author Gordon
 * @since 2016/3/10
 * do()
 */
public interface Cache {
    /**
     * 保存数据
     *
     * @param dataType
     * @param data
     */
    public void saveData(int dataType, Object data);

    /**
     * 获取数据
     *
     * @param dataType
     * @param callBack
     * @return
     */
    public void getData(int dataType, CacheCallBack callBack);

}

