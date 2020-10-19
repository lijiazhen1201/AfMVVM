package cn.appoa.afmedia;

import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;

/**
 * 多媒体相关
 */
public class AfMedia {

    private static AfMedia instance = null;
    private Context context;

    private AfMedia(Context context) {
        this.context = context;
    }

    public synchronized static AfMedia getInstance(Context context) {
        if (instance == null) {
            instance = new AfMedia(context);
        }
        return instance;
    }

    private HttpProxyCacheServer proxy;

    public HttpProxyCacheServer getProxy() {
        return proxy == null ? proxy = newProxy() : proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(context).build();
    }

}
