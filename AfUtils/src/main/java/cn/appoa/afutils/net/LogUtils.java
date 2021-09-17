package cn.appoa.afutils.net;

import android.text.TextUtils;
import android.util.Log;

/**
 * 日志打印工具类
 */
public class LogUtils {

    /**
     * 日志打印开关
     */
    public static boolean isDebug = true;

    /**
     * 日志打印
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        log(1, tag, msg);
    }

    /**
     * 日志打印
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        log(2, tag, msg);
    }

    /**
     * 日志打印
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        log(3, tag, msg);
    }

    /**
     * 日志打印
     *
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        log(4, tag, msg);
    }

    /**
     * 日志打印
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        log(5, tag, msg);
    }

    /**
     * 分段打印日志
     *
     * @param level
     * @param tag
     * @param msg
     */
    public static void log(int level, String tag, String msg) {
        if (isDebug) {
            if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) {
                return;
            }
            int segmentSize = 3 * 1024;
            long length = msg.length();
            if (length <= segmentSize) {
                // 长度小于等于限制直接打印
                print(level, tag, msg);
            } else {
                while (msg.length() > segmentSize) {
                    // 循环分段打印日志
                    String logContent = msg.substring(0, segmentSize);
                    msg = msg.replace(logContent, "");
                    print(level, tag, logContent);
                }
                // 打印剩余日志
                print(level, tag, msg);
            }
        }
    }

    /**
     * 打印日志
     *
     * @param level
     * @param tag
     * @param msg
     */
    public static void print(int level, String tag, String msg) {
        if (isDebug) {
            if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) {
                return;
            }
            switch (level) {
                case 1:
                    Log.v(tag, msg);
                    break;
                case 2:
                    Log.d(tag, msg);
                    break;
                case 3:
                    Log.i(tag, msg);
                    break;
                case 4:
                    Log.w(tag, msg);
                    break;
                case 5:
                    Log.e(tag, msg);
                    break;
                default:
                    break;
            }
        }
    }

}
