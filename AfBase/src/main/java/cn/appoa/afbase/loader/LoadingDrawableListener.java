package cn.appoa.afbase.loader;

import android.graphics.drawable.Drawable;

/**
 * 加载图片监听
 */
public interface LoadingDrawableListener {

    /**
     * 加载成功
     *
     * @param drawable
     */
    void loadingDrawableSuccess(Drawable drawable);

    /**
     * 加载失败
     */
    void loadingDrawableFailed();
}
