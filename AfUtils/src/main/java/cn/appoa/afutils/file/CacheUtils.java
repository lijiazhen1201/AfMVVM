package cn.appoa.afutils.file;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.downloader.OnProgressListener;
import com.downloader.PRDownloader;
import com.downloader.request.DownloadRequest;

import java.io.File;
import java.math.BigDecimal;

import cn.appoa.afutils.AfUtils;
import cn.appoa.afutils.R;
import cn.appoa.afutils.listener.CacheFileListener;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * 缓存工具类
 */
public class CacheUtils {

    /**
     * 清除全部缓存
     *
     * @param tv 显示缓存数量的TextView
     */
    public static void clearAllCache(TextView tv) {
        try {
            String totalCacheSize = getTotalCacheSize(tv.getContext());
            if (TextUtils.equals(totalCacheSize, "0.0Byte")) {
                ToastUtils.showShort(tv.getContext(), R.string.clear_all_cache_already, true);
            } else {
                clearAllCache(tv.getContext());
                totalCacheSize = getTotalCacheSize(tv.getContext());
                if (TextUtils.equals(totalCacheSize, "0.0Byte")) {
                    tv.setText(totalCacheSize);
                    Toast toast = Toast.makeText(tv.getContext(),
                            R.string.clear_all_cache_success, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    ImageView iv = new ImageView(tv.getContext());
                    iv.setImageResource(R.drawable.delete_cache_success);
                    toast.setView(iv);
                    toast.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除全部缓存
     *
     * @param context
     */
    public static void clearAllCache(Context context) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return;
        }
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    /**
     * 删除文件夹内文件
     *
     * @param dir
     * @return
     */
    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 计算缓存大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Context context) throws Exception {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return "";
        }
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        if (file != null) {
            try {
                File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    // 如果下面还有文件
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i]);
                    } else {
                        size = size + fileList[i].length();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 获取系统缓存文件夹
     *
     * @return
     */
    public static String getCacheDir(Context context) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return "";
        }
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }

    /**
     * 缓存文件
     *
     * @param context
     * @param url
     */
    public static void cacheFile(Context context, String url) {
        cacheFile(context, url, null);
    }

    /**
     * 缓存文件
     *
     * @param context
     * @param url
     * @param callbackListener
     */
    public static void cacheFile(Context context, String url, CacheFileListener callbackListener) {
        cacheFile(context, url, callbackListener, null);
    }

    /**
     * 缓存文件
     *
     * @param context
     * @param url
     * @param callbackListener
     * @param progressListener 进度回调
     */
    public static void cacheFile(Context context, String url,
                                 final CacheFileListener callbackListener,
                                 final OnProgressListener progressListener) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null || TextUtils.isEmpty(url)) {
            return;
        }
        File localFile = new File(url);
        if (localFile.exists()) {
            //本地文件
            if (callbackListener != null) {
                callbackListener.onCacheFileSuccess(1, localFile);
            }
        } else {
            //网络文件
            String dirPath = getCacheDir(context);
            String filename = url.substring(url.lastIndexOf("/") + 1);
            final File file = new File(dirPath, filename);
            if (file.exists()) {
                //已经下载
                if (callbackListener != null) {
                    callbackListener.onCacheFileSuccess(2, file);
                }
            } else {
                DownloadRequest request = PRDownloader.download(url, dirPath, filename).build();
                if (progressListener != null) {
                    request.setOnProgressListener(progressListener);
                }
                request.start(new com.downloader.OnDownloadListener() {

                    @Override
                    public void onDownloadComplete() {
                        //下载成功
                        if (callbackListener != null) {
                            callbackListener.onCacheFileSuccess(3, file);
                        }
                    }

                    @Override
                    public void onError(com.downloader.Error error) {
                        //下载失败
                        if (callbackListener != null) {
                            callbackListener.onCacheFileSuccess(0, null);
                        }
                    }

                });
            }
        }
    }

}
