package com.lyun.utils.filecache;

import android.content.Context;

import com.lyun.BaseApplication;

import java.io.IOException;

/**
 * @author Gordon
 * @since 2016/3/10
 * do()
 */
public class CacheUtil implements Cache {
    private static byte[] lock = new byte[0];

    private static CacheUtil instance = null;

    private static final String cachePre = "cache_";

    protected static final String TAG = "CacheImpl";

    private CacheUtil() {

    }

    public static CacheUtil getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null)
                    instance = new CacheUtil();
                return instance;
            }
        } else {
            return instance;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.iwgame.msgs.module.cache.Cache#saveData(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void saveData(int dataType, Object data) {
            try {
                    FileUtils.writeFile(BaseApplication.getInstance().getBaseContext(), cachePre + "lyun_user_"
                            + dataType, data, Context.MODE_PRIVATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.iwgame.msgs.module.cache.Cache#getData(java.lang.String)
     */
    @Override
    public void getData(int dataType, CacheCallBack callBack) {
        switch (dataType) {
            case Cache.DATA_TYPE_FIND_BY_LANGUAGE:
                getCachePoolData(dataType, callBack);
                break;
            default:
                break;
        }
    }


    /************************* 发现缓存数据 *************************/
    /**
     *
     * @param callBack
     */
    private void getCachePoolData(int type, final CacheCallBack callBack) {
        try {
                Object result = FileUtils.readFile(BaseApplication.getInstance().getBaseContext(), cachePre
                        + "lyun_user_"+ type);
                callBack.onBack(result);
        } catch (IOException e) {
            e.printStackTrace();
            callBack.onBack(null);
        }
    }
}
