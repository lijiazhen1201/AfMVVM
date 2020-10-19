package cn.appoa.afutils.app;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Environment;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;

import com.downloader.PRDownloader;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.appoa.afutils.AfUtils;
import cn.appoa.afutils.R;
import cn.appoa.afpermission.grant.PermissionsManager;
import cn.appoa.afpermission.grant.PermissionsResultAction;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * Activity工具类
 */
public class AtyUtils {

    /**
     * base64解密字符串
     *
     * @param base64
     * @param isFromHtml 是否过滤html标签
     * @return
     */
    public static String base64ToText(String base64, boolean isFromHtml) {
        Spanned text = null;
        if (!TextUtils.isEmpty(base64)) {
            String source = new String(Base64.decode(base64.getBytes(), Base64.DEFAULT));
            if (isFromHtml) {
                text = Html.fromHtml(source);
            } else {
                text = new SpannableString(source);
            }
        }
        if (text == null) {
            return base64;
        } else {
            return text.toString();
        }
    }

    /**
     * 指定关键词变色
     *
     * @param color   关键词颜色
     * @param text    文本
     * @param keyword 关键词
     * @return
     */
    public static SpannableString matcherSearchKey(int color, String text, String keyword) {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(keyword)) {
            return null;
        }
        String string = text.toLowerCase();
        String key = keyword.toLowerCase();

        Pattern pattern = Pattern.compile(key);
        Matcher matcher = pattern.matcher(string);

        SpannableString ss = new SpannableString(text);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    /**
     * 复制文本到剪贴板
     *
     * @param mActivity
     * @param text
     */
    public static void copyText(Activity mActivity, String text) {
        if (mActivity == null || TextUtils.isEmpty(text)) {
            return;
        }
        ClipboardManager clipboard = (ClipboardManager) mActivity
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(null, text));
        ToastUtils.showShort(mActivity, R.string.copy_text_success, false);
    }

    /**
     * 获取剪贴板的文本
     *
     * @return 剪贴板的文本
     */
    public static CharSequence getText(Activity mActivity) {
        if (mActivity == null) {
            return null;
        }
        ClipboardManager clipboard = (ClipboardManager) mActivity
                .getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).coerceToText(mActivity);
        }
        return null;
    }

    /**
     * 保存图片到相册
     *
     * @param activity
     * @param url
     */
    public static void saveImage(final Activity activity, final String url) {
        if (activity == null || TextUtils.isEmpty(url)) {
            return;
        }
        String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(
                activity, permissions, new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        //相册路径
                        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()//
                                + File.separator + "DCIM/Camera";
                        String filename = url.substring(url.lastIndexOf("/") + 1);
                        final File file = new File(dirPath, filename);
                        PRDownloader.download(url, dirPath, filename).build()
                                .start(new com.downloader.OnDownloadListener() {

                                    @Override
                                    public void onError(com.downloader.Error error) {
                                        ToastUtils.showShort(activity,
                                                R.string.file_download_failed, false);
                                    }

                                    @Override
                                    public void onDownloadComplete() {
                                        IntentUtils.scanFile(activity, file.getAbsolutePath());
                                        ToastUtils.showLong(activity, String.format(
                                                activity.getString(R.string.file_download_success),
                                                file.getAbsolutePath()), false);
                                    }
                                });
                    }

                    @Override
                    public void onDenied(String permission) {
                        ToastUtils.showShort(activity, R.string.open_save_file_permission, false);
                    }
                });
    }

    /**
     * 判断是否存在Activity
     *
     * @param packageName 包名
     * @param className   activity全路径类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isActivityExists(Context context, String packageName, String className) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return false;
        }
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(context.getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(context.getPackageManager()) == null ||
                context.getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * 获取launcher activity
     *
     * @param packageName 包名
     * @return launcher activity
     */
    public static String getLauncherActivity(Context context, String packageName) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return "no " + packageName;
        }
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo aInfo : info) {
            if (aInfo.activityInfo.packageName.equals(packageName)) {
                return aInfo.activityInfo.name;
            }
        }
        return "no " + packageName;
    }

}
