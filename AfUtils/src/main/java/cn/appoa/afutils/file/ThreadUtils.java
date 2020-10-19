package cn.appoa.afutils.file;

import android.os.Handler;
import android.os.Looper;

/**
 * 线程工具类
 */
public final class ThreadUtils {

    /**
     * 运行在主线程
     *
     * @param r
     */
    public static void runInMain(Runnable r) {
        Handler h = new Handler(Looper.getMainLooper());
        h.post(r);
    }

    /**
     * 运行在子线程
     *
     * @param r
     */
    public static void runInBack(Runnable r) {
        new Thread(r).start();
    }
}
