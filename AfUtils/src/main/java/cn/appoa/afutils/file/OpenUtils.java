package cn.appoa.afutils.file;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

import androidx.core.content.FileProvider;


public final class OpenUtils {

    /**
     * 下载文件
     *
     * @param url
     * @return
     */
    public static long downloadFile(Context context, String url) {
        if (context == null || TextUtils.isEmpty(url)) {
            return -1L;
        }
        try {
            //获取下载管理器
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            //创建下载任务,downloadUrl就是下载链接
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            //指定较大的文件只能在WiFi下进行下载
            //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
            //指定下载路径和下载文件名
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, url.substring(url.lastIndexOf("/") + 1));
            request.setTitle(url.substring(url.lastIndexOf("/") + 1));
            request.setDescription(url.substring(url.lastIndexOf("/") + 1));
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            //将下载任务加入下载队列，否则不会进行下载
            return downloadManager.enqueue(request);
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    /**
     * 打开文件
     *
     * @param context
     * @param filePath
     * @return
     */
    public static Intent openFile(Context context, String filePath) {

        File file = new File(filePath);
        /*if(!file.exists()){
            return null;
        }*/
        /* 取得扩展名 */
        String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
        /* 依扩展名的类型决定MimeType */
        if ("m4a".equals(end) || "mp3".equals(end) || "mid".equals(end) ||
                "xmf".equals(end) || "ogg".equals(end) || "wav".equals(end)) {
            return getAudioFileIntent(filePath);
        } else if ("3gp".equals(end) || "mp4".equals(end)) {
            return getAudioFileIntent(filePath);
        } else if ("jpg".equals(end) || "gif".equals(end) || "png".equals(end) ||
                "jpeg".equals(end) || "bmp".equals(end) || "heic".equals(end)) {
            return getImageFileIntent(filePath);
        } else if ("apk".equals(end)) {
            return getApkFileIntent(context, filePath);
        } else if ("ppt".equals(end)) {
            return getPptFileIntent(context, filePath);
        } else if ("xls".equals(end) || "xlsx".equals(end)) {
            return getExcelFileIntent(context, filePath);
        } else if ("doc".equals(end) || "docx".equals(end)) {
            return getWordFileIntent(context, filePath);
        } else if ("pdf".equals(end)) {
            return getPdfFileIntent(context, filePath);
        } else if ("chm".equals(end)) {
            return getChmFileIntent(filePath);
        } else if ("txt".equals(end)) {
            return getTextFileIntent(filePath, false);
        } else {
            return getAllIntent(filePath);
        }
    }

    //Android获取一个用于打开APK文件的intent
    public static Intent getAllIntent(String param) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    //Android获取一个用于打开APK文件的intent
    public static Intent getApkFileIntent(Context context, String param) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS, param.substring(param.lastIndexOf("/") + 1)));
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contentUri = Uri.fromFile(new File(param));
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        }
        List<ResolveInfo> resolveLists = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        // 然后全部授权
        for (ResolveInfo resolveInfo : resolveLists) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        return intent;
    }

    //Android获取一个用于打开VIDEO文件的intent
    public static Intent getVideoFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    //Android获取一个用于打开AUDIO文件的intent
    public static Intent getAudioFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    //Android获取一个用于打开Html文件的intent
    public static Intent getHtmlFileIntent(String param) {

        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    //Android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    //Android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent(Context context, String param) {

        /*Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;*/
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS, param.substring(param.lastIndexOf("/") + 1)));
            intent.setDataAndType(contentUri, "application/vnd.ms-powerpoint");
        } else {
            contentUri = Uri.fromFile(new File(param));
            intent.setDataAndType(contentUri, "application/vnd.ms-powerpoint");
        }
        List<ResolveInfo> resolveLists = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        // 然后全部授权
        for (ResolveInfo resolveInfo : resolveLists) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        return intent;
    }

    //Android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(Context context, String param) {

        /*Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;*/
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS, param.substring(param.lastIndexOf("/") + 1)));
            intent.setDataAndType(contentUri, "application/vnd.ms-excel");
        } else {
            contentUri = Uri.fromFile(new File(param));
            intent.setDataAndType(contentUri, "application/vnd.ms-excel");
        }
        List<ResolveInfo> resolveLists = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        // 然后全部授权
        for (ResolveInfo resolveInfo : resolveLists) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        return intent;
    }

    //Android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(Context context, String param) {

        /*Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "application/msword");
        return intent;*/
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS, param.substring(param.lastIndexOf("/") + 1)));
            intent.setDataAndType(contentUri, "application/msword");
        } else {
            contentUri = Uri.fromFile(new File(param));
            intent.setDataAndType(contentUri, "application/msword");
        }
        List<ResolveInfo> resolveLists = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        // 然后全部授权
        for (ResolveInfo resolveInfo : resolveLists) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        return intent;
    }

    //Android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    //Android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(String param, boolean paramBoolean) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(param));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }

    //Android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(Context context, String param) {

        /*Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOWNLOADS,param.substring(param.lastIndexOf("/") + 1)));
        //Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "application/pdf");*/

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS, param.substring(param.lastIndexOf("/") + 1)));
            intent.setDataAndType(contentUri, "application/pdf");
        } else {
            contentUri = Uri.fromFile(new File(param));
            intent.setDataAndType(contentUri, "application/pdf");
        }
        List<ResolveInfo> resolveLists = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        // 然后全部授权
        for (ResolveInfo resolveInfo : resolveLists) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        return intent;
    }
}
