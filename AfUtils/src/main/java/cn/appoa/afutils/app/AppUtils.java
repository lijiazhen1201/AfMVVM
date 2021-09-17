package cn.appoa.afutils.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.List;

import cn.appoa.afutils.AfUtils;
import cn.appoa.afutils.net.LogUtils;

/**
 * App工具类
 */
public class AppUtils {

    /**
     * 检测是否安装App
     *
     * @param context
     * @param packageName 包名
     * @return
     */
    public static boolean checkApkExist(Context context, String packageName) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null || TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            @SuppressWarnings("unused")
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * App是否在前台
     *
     * @param context
     * @return
     */
    @SuppressWarnings({"deprecation"})
    public static boolean isAppRunningForeground(Context context) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return false;
        }
        @SuppressLint("WrongConstant")
        ActivityManager localActivityManager = (ActivityManager)
                context.getSystemService("activity");
        try {
            List<ActivityManager.RunningTaskInfo> localList =
                    localActivityManager.getRunningTasks(1);
            if ((localList == null) || (localList.size() < 1)) {
                return false;
            }
            boolean bool = context.getPackageName().equalsIgnoreCase(
                    ((ActivityManager.RunningTaskInfo) localList.get(0)).baseActivity.getPackageName());
            LogUtils.i("isAppRunningForeground", "app running in foregroud：" + (bool));
            return bool;
        } catch (SecurityException e) {
            LogUtils.i("isAppRunningForeground", "Apk doesn't hold GET_TASKS permission");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取版本号
     *
     * @param cxt
     * @return
     */
    public static int getVersionCode(Context cxt) {
        if (cxt == null) {
            cxt = AfUtils.getInstance().getContext();
        }
        if (cxt == null) {
            return 0;
        }
        // 获取packagemanager的实例
        PackageManager packageManager = cxt.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        int version = -1;
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(cxt.getPackageName(), 0);
            version = packInfo.versionCode;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取版本名
     *
     * @param cxt
     * @return
     */
    public static String getVersionName(Context cxt) {
        if (cxt == null) {
            cxt = AfUtils.getInstance().getContext();
        }
        if (cxt == null) {
            return "";
        }
        // 获取packagemanager的实例
        PackageManager packageManager = cxt.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        String version = "";
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(cxt.getPackageName(), 0);
            version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isTablet(Context context) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return false;
        }
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断当前设备是手机还是平板，是否具备电话功能判断方法（现在部分平板也可以打电话）
     *
     * @param activity
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Activity activity) {
        if (activity == null) {
            return false;
        }
        TelephonyManager telephony = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前设备是手机还是平板，根据屏幕尺寸判断
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return false;
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        // 屏幕宽度
        float screenWidth = display.getWidth();
        // 屏幕高度
        float screenHeight = display.getHeight();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        // 屏幕尺寸
        double screenInches = Math.sqrt(x + y);
        // 大于7尺寸则为Pad
        if (screenInches >= 7.0) {
            return true;
        }
        return false;
    }

}
