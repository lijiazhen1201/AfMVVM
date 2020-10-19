package cn.appoa.afhttp;

import android.app.Application;

import com.lzy.okgo.OkGo;

/**
 * 网络访问相关
 */
public class AfHttp {

    private static AfHttp instance = null;

    private AfHttp() {
    }

    public synchronized static AfHttp getInstance() {
        if (instance == null) {
            instance = new AfHttp();
        }
        return instance;
    }

    /**
     * 是否初始化
     */
    private boolean isInited;

    /**
     * 初始化
     *
     * @param app
     */
    public void init(Application app) {
        OkGo.getInstance().init(app);
        isInited = true;
    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public void cancelTag(String tag) {
        if (isInited) {
            OkGo.getInstance().cancelTag(tag);
        }
    }
}
