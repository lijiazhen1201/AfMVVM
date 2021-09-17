package cn.appoa.afbase.loader;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * 加载网络图片
 */
public abstract class AfImageLoader {

    /**
     * 是否初始化
     */
    protected boolean isInit;

    /**
     * 加载中图片
     */
    protected int loadingImg;

    /**
     * 加载失败图片
     */
    protected int errorImg;

    /**
     * 默认图片
     */
    protected int defaultImg;

    /**
     * 图片前缀
     */
    protected String imageUrl;

    /**
     * 初始化
     *
     * @param loadingImg 加载中图片
     * @param errorImg   加载失败图片
     * @param defaultImg 默认图片
     * @param imageUrl   图片前缀
     */
    public abstract void init(int loadingImg, int errorImg, int defaultImg, String imageUrl);

    /**
     * 获取图片地址
     *
     * @param url 图片地址
     * @return 图片全路径地址
     */
    public abstract String getImageUrl(String url);

    /**
     * 清除缓存
     */
    public abstract void clearCache();

    /**
     * @param url 图片地址
     * @param iv  ImageView
     */
    public abstract void loadImage(String url, ImageView iv);

    /**
     * @param url        图片地址
     * @param iv         ImageView
     * @param defaultImg 默认图片
     */
    public abstract void loadImage(String url, ImageView iv, int defaultImg);

    /**
     * @param url        图片地址
     * @param iv         ImageView
     * @param defaultImg 默认图片
     */
    public abstract void loadImage(String url, ImageView iv, Drawable defaultImg);

    /**
     * @param url        图片地址
     * @param iv         ImageView
     * @param loadingImg 加载中图片
     * @param errorImg   加载失败图片
     * @param defaultImg 默认图片
     */
    public abstract void loadImage(String url, ImageView iv, int loadingImg, int errorImg, int defaultImg);

    /**
     * @param url        图片地址
     * @param iv         ImageView
     * @param loadingImg 加载中图片
     * @param errorImg   加载失败图片
     * @param defaultImg 默认图片
     */
    public abstract void loadImage(String url, ImageView iv, Drawable loadingImg, Drawable errorImg, Drawable defaultImg);

    /**
     * @param url      图片地址
     * @param iv       ImageView
     * @param listener 加载图片监听
     */
    public abstract void loadImage(String url, ImageView iv, final LoadingBitmapListener listener);

    /**
     * @param url        图片地址
     * @param iv         ImageView
     * @param defaultImg 默认图片
     * @param listener   加载图片监听
     */
    public abstract void loadImage(String url, ImageView iv, int defaultImg, final LoadingBitmapListener listener);

    /**
     * @param url        图片地址
     * @param iv         ImageView
     * @param defaultImg 默认图片
     * @param listener   加载图片监听
     */
    public abstract void loadImage(String url, ImageView iv, Drawable defaultImg, final LoadingBitmapListener listener);

    /**
     * @param url        图片地址
     * @param iv         ImageView
     * @param loadingImg 加载中图片
     * @param errorImg   加载失败图片
     * @param defaultImg 默认图片
     * @param listener   加载图片监听
     */
    public abstract void loadImage(String url, ImageView iv, int loadingImg, int errorImg, int defaultImg,
                                   final LoadingBitmapListener listener);

    /**
     * @param url        图片地址
     * @param iv         ImageView
     * @param loadingImg 加载中图片
     * @param errorImg   加载失败图片
     * @param defaultImg 默认图片
     * @param listener   加载图片监听
     */
    public abstract void loadImage(String url, ImageView iv, Drawable loadingImg, Drawable errorImg, Drawable defaultImg,
                                   final LoadingBitmapListener listener);

    /**
     * 下载图片
     *
     * @param url      图片地址
     * @param listener 加载图片监听
     */
    public abstract void downloadImage(String url, final LoadingBitmapListener listener);

    /**
     * 下载图片
     *
     * @param url      图片地址
     * @param listener 加载图片监听
     */
    public abstract void downloadDrawable(String url, final LoadingDrawableListener listener);
}
