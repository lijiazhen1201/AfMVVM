package cn.appoa.afutils.res;

import android.content.Context;

import cn.appoa.afutils.AfUtils;

/**
 * 尺寸工具类
 */
public class SizeUtils {

    /**
     * dp转px
     *
     * @param context
     * @param dpValue
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return (int) dpValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxValue
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return (int) pxValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param context
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(Context context, float spValue) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return (int) spValue;
        }
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param context
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(Context context, float pxValue) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return (int) pxValue;
        }
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}
