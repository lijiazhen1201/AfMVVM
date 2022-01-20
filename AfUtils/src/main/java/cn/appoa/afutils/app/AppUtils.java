package cn.appoa.afutils.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.List;
import java.util.Locale;

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

    /**
     * 获取CPU架构
     *
     * @return
     */
    public static String getCPUABI() {
        String CPUABI = "";
        try {
            String os_cpuabi = new BufferedReader(new InputStreamReader(Runtime.getRuntime()
                    .exec("getprop ro.product.cpu.abi").getInputStream())).readLine();
            if (os_cpuabi.contains("x86")) {
                CPUABI = "x86";
            } else if (os_cpuabi.contains("armeabi-v7a")) {
                CPUABI = "armeabi-v7a";
            } else if (os_cpuabi.contains("arm64-v8a")) {
                CPUABI = "arm64-v8a";
            } else {
                CPUABI = "armeabi";
            }
        } catch (Exception e) {
            CPUABI = "armeabi";
        }
        return CPUABI;
    }

    /**
     * 获取当前APP证书中的sha1值
     *
     * @param context
     * @return
     */
    public static String getAppSha1(Context context) {
        String current_sha1 = "";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i]).toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            current_sha1 = result.substring(0, result.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return current_sha1;
    }

    /**
     * 返回app运行状态
     *
     * @param context     一个context
     * @param packageName 要判断应用的包名
     * @return int 1:前台 2:后台 0:不存在
     */
    public static int isAppAlive(Context context, String packageName) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> listInfos = activityManager
                .getRunningTasks(20);
        // 判断程序是否在栈顶
        if (listInfos.get(0).topActivity.getPackageName().equals(packageName)) {
            return 1;
        } else {
            // 判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : listInfos) {
                if (info.topActivity.getPackageName().equals(packageName)) {
                    return 2;
                }
            }
            // 栈里找不到，返回0
            return 0;
        }
    }

    /**
     * 重启App
     *
     * @param context
     */
    public static void restartApp(Context context) {
        restartApp(context, false);
    }

    /**
     * 重启App
     *
     * @param context
     * @param isKillProcess 是否杀死进程
     */
    public static void restartApp(Context context, boolean isKillProcess) {
        String launcherActivity = AtyUtils.getLauncherActivity(context);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClassName(context.getPackageName(), launcherActivity);
        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
        );
        context.startActivity(intent);
        if (isKillProcess) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

}
