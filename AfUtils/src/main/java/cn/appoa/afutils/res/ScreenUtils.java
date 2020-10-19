package cn.appoa.afutils.res;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import cn.appoa.afutils.AfUtils;

/**
 * 屏幕工具类
 */
public class ScreenUtils {

    /**
     * 设置顶部间距(沉浸式)
     *
     * @param view
     */
    public static void setPaddingTop(View view) {
        if (Build.VERSION.SDK_INT >= 19 && view != null) {
            view.setPadding(0, getStatusHeight(view.getContext()), 0, 0);
        }
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return 0;
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return 0;
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        int statusHeight = 0;
        if (context != null) {
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
                statusHeight = context.getResources().getDimensionPixelSize(height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * 获得虚拟按键高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        int navigationBarHeight = 0;
        if (context != null) {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                navigationBarHeight = resources.getDimensionPixelSize(resourceId);
            }
        }
        return navigationBarHeight;
    }

    /**
     * 获取真实高度
     *
     * @param context
     * @return
     */
    public static int getRealHeight(Context context) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return 0;
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int screenHeight = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics dm = new DisplayMetrics();
            display.getRealMetrics(dm);
            screenHeight = dm.heightPixels;
            // 或者也可以使用getRealSize方法
            // Point size = new Point();
            // display.getRealSize(size);
            // screenHeight = size.y;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            try {
                screenHeight = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception e) {
                DisplayMetrics dm = new DisplayMetrics();
                display.getMetrics(dm);
                screenHeight = dm.heightPixels;
            }
        }
        return screenHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap captureWithStatusBar(Activity activity) {
        if (activity == null) {
            return null;
        }
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        view.destroyDrawingCache();
        return ret;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap captureWithoutStatusBar(Activity activity) {
        if (activity == null) {
            return null;
        }
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int statusBarHeight = getStatusHeight(activity);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, dm.widthPixels,
                dm.heightPixels - statusBarHeight);
        view.destroyDrawingCache();
        return ret;
    }

}
