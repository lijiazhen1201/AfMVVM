package cn.appoa.afutils.toast;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import cn.appoa.afutils.AfUtils;

/**
 * Toast工具类
 */
public class ToastUtils {

    /**
     * Toast
     */
    private static Toast toast = null;

    /**
     * 短时间Toast
     *
     * @param resId
     * @param isCenter 是否显示在中间
     */
    public static void showShort(int resId, boolean isCenter) {
        showShort(AfUtils.getInstance().getContext(), resId, isCenter);
    }


    /**
     * 短时间Toast
     *
     * @param context
     * @param resId
     * @param isCenter 是否显示在中间
     */
    public static void showShort(Context context, int resId, boolean isCenter) {
        if (context == null) {
            showShort(resId, isCenter);
        } else {
            showShort(context, context.getString(resId), isCenter);
        }
    }

    /**
     * 短时间Toast
     *
     * @param message
     * @param isCenter 是否显示在中间
     */
    public static void showShort(CharSequence message, boolean isCenter) {
        showShort(AfUtils.getInstance().getContext(), message, isCenter);
    }

    /**
     * 短时间Toast
     *
     * @param context
     * @param message
     * @param isCenter 是否显示在中间
     */
    public static void showShort(Context context, CharSequence message, boolean isCenter) {
        if (context == null) {
            showShort(message, isCenter);
        } else {
            toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
            toast.setText(message);
            toast.setGravity(isCenter ? Gravity.CENTER : 81, 0, isCenter ? 0 : 176);
            toast.show();
        }
    }

    /**
     * 长时间Toast
     *
     * @param resId
     * @param isCenter 是否显示在中间
     */
    public static void showLong(int resId, boolean isCenter) {
        showLong(AfUtils.getInstance().getContext(), resId, isCenter);
    }

    /**
     * 长时间Toast
     *
     * @param context
     * @param resId
     * @param isCenter 是否显示在中间
     */
    public static void showLong(Context context, int resId, boolean isCenter) {
        if (context == null) {
            showLong(resId, isCenter);
        } else {
            showLong(context, context.getString(resId), isCenter);
        }
    }

    /**
     * 长时间Toast
     *
     * @param message
     * @param isCenter 是否显示在中间
     */
    public static void showLong(CharSequence message, boolean isCenter) {
        showLong(AfUtils.getInstance().getContext(), message, isCenter);
    }

    /**
     * 长时间Toast
     *
     * @param context
     * @param message
     * @param isCenter 是否显示在中间
     */
    public static void showLong(Context context, CharSequence message, boolean isCenter) {
        if (context == null) {
            showLong(message, isCenter);
        } else {
            toast = Toast.makeText(context, null, Toast.LENGTH_LONG);
            toast.setText(message);
            toast.setGravity(isCenter ? Gravity.CENTER : 81, 0, isCenter ? 0 : 176);
            toast.show();
        }
    }

}
