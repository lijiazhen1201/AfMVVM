package cn.appoa.afutils;

import android.content.Context;

import com.tencent.mmkv.MMKV;

/**
 * 工具相关
 */
public class AfUtils {

    private static AfUtils instance = null;

    private AfUtils() {
    }

    public synchronized static AfUtils getInstance() {
        if (instance == null) {
            instance = new AfUtils();
        }
        return instance;
    }

    private Context appContext;

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        this.appContext = context;
        MMKV.initialize(this.appContext);
    }

    public Context getContext() {
        return appContext;
    }
}
