package cn.appoa.afbase.loader;

import android.graphics.Bitmap;

/**
 * 加载图片监听
 */
public interface LoadingBitmapListener {

    /**
     * 加载成功
     *
     * @param bitmap
     */
    void loadingBitmapSuccess(Bitmap bitmap);

    /**
     * 加载失败
     */
    void loadingBitmapFailed();
}
