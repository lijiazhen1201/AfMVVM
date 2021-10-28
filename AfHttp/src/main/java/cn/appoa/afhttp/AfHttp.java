package cn.appoa.afhttp;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.utils.OkLogger;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

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
        //EasyConfig.with(null).into();
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

    /**
     * 设置Log打印
     *
     * @param isDebug
     */
    public void setLogDebug(boolean isDebug) {
        if (isInited) {
            OkLogger.debug(isDebug);
            OkGo mOkGo = OkGo.getInstance();
            if (mOkGo != null) {
                OkHttpClient okHttpClient = mOkGo.getOkHttpClient();
                if (okHttpClient != null) {
                    List<Interceptor> listInterceptor = okHttpClient.interceptors();
                    if (listInterceptor != null && listInterceptor.size() > 0) {
                        HttpLoggingInterceptor loggingInterceptor = (HttpLoggingInterceptor) listInterceptor.get(0);
                        if (loggingInterceptor != null) {
                            loggingInterceptor.setPrintLevel(isDebug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
                        }
                    }
                }
            }
        }
    }
}
