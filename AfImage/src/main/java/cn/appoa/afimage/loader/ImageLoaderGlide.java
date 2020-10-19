package cn.appoa.afimage.loader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afbase.loader.AfImageLoader;
import cn.appoa.afbase.loader.LoadingBitmapListener;

/**
 * Glide加载图片
 */
public class ImageLoaderGlide extends AfImageLoader {

    private static ImageLoaderGlide instance = null;

    private ImageLoaderGlide() {

    }

    public synchronized static AfImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoaderGlide();
        }
        return instance;
    }

    /**
     * 缓存方式
     */
    private DiskCacheStrategy strategy;

    /**
     * 设置缓存方式
     */
    public void setDiskCacheStrategy(DiskCacheStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void init(int loadingImg, int errorImg, int defaultImg, String imageUrl) {
        this.loadingImg = loadingImg;
        this.errorImg = errorImg;
        this.defaultImg = defaultImg;
        this.imageUrl = imageUrl;
        this.strategy = DiskCacheStrategy.ALL;
        isInit = true;
    }


    /**
     * 初始化请求
     *
     * @param url
     * @return
     */
    private RequestBuilder getRequestBuilder(String url) {
        RequestManager manager = Glide.with(AfApplication.getAppContext());
        if (!TextUtils.isEmpty(url) && (url.endsWith(".gif") || url.endsWith(".GIF"))) {
            return manager.asGif().load(url);//动图
        } else {
            return manager.asBitmap().load(url);
        }
    }

    @Override
    public String getImageUrl(String url) {
        if (url == null) {
            return "";
        } else {
            File file = new File(url);
            if (file.exists()) {
                return file.getAbsolutePath();
            } else {
                if (url.startsWith("http")) {
                    return url;
                } else {
                    return imageUrl + url;
                }
            }
        }
    }

    @Override
    public void loadImage(String url, ImageView iv) {
        if (isInit && iv != null) {
            getRequestBuilder(url).apply(new RequestOptions()
                    .diskCacheStrategy(strategy)
                    .centerCrop()
                    .diskCacheStrategy(strategy)//
                    .placeholder(loadingImg)//
                    .error(errorImg)//
                    .fallback(defaultImg))
                    .into(iv);
        }
    }

    @Override
    public void loadImage(String url, ImageView iv, int defaultImg) {
        loadImage(url, iv, getDrawable(defaultImg));
    }

    @Override
    public void loadImage(String url, ImageView iv, Drawable defaultImg) {
        if (isInit && iv != null) {
            getRequestBuilder(url).apply(new RequestOptions()
                    .diskCacheStrategy(strategy)
                    .centerCrop()
                    .diskCacheStrategy(strategy)//
                    .placeholder(defaultImg)//
                    .error(defaultImg)//
                    .fallback(defaultImg))
                    .into(iv);
        }
    }

    @Override
    public void loadImage(String url, ImageView iv, int loadingImg, int errorImg, int defaultImg) {
        loadImage(url, iv, getDrawable(loadingImg), getDrawable(errorImg), getDrawable(defaultImg));
    }

    @Override
    public void loadImage(String url, ImageView iv, Drawable loadingImg, Drawable errorImg, Drawable defaultImg) {
        if (isInit && iv != null) {
            getRequestBuilder(url).apply(new RequestOptions()
                    .diskCacheStrategy(strategy)
                    .centerCrop()
                    .diskCacheStrategy(strategy)//
                    .placeholder(loadingImg)//
                    .error(errorImg)//
                    .fallback(defaultImg))
                    .into(iv);
        }
    }

    @Override
    public void loadImage(String url, ImageView iv, final LoadingBitmapListener listener) {
        if (isInit && iv != null && listener != null) {
            getRequestBuilder(url).apply(new RequestOptions()
                    .diskCacheStrategy(strategy)
                    .centerCrop()
                    .diskCacheStrategy(strategy)//
                    .placeholder(loadingImg)//
                    .error(errorImg)//
                    .fallback(defaultImg))
                    .listener(new RequestListener<Bitmap>() {

                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Bitmap> target, boolean isFirstResource) {
                            listener.loadingBitmapFailed();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model,
                                                       Target<Bitmap> target, DataSource dataSource,
                                                       boolean isFirstResource) {
                            listener.loadingBitmapSuccess(resource);
                            return false;
                        }
                    }).into(iv);
        }
    }

    @Override
    public void loadImage(String url, ImageView iv, int defaultImg, final LoadingBitmapListener listener) {
        loadImage(url, iv, getDrawable(defaultImg), listener);
    }

    @Override
    public void loadImage(String url, ImageView iv, Drawable defaultImg, final LoadingBitmapListener listener) {
        if (isInit && iv != null && listener != null) {
            getRequestBuilder(url).apply(new RequestOptions()
                    .diskCacheStrategy(strategy)
                    .centerCrop()
                    .diskCacheStrategy(strategy)//
                    .placeholder(defaultImg)//
                    .error(defaultImg)//
                    .fallback(defaultImg))
                    .listener(new RequestListener<Bitmap>() {

                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Bitmap> target, boolean isFirstResource) {
                            listener.loadingBitmapFailed();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model,
                                                       Target<Bitmap> target, DataSource dataSource,
                                                       boolean isFirstResource) {
                            listener.loadingBitmapSuccess(resource);
                            return false;
                        }
                    }).into(iv);
        }
    }

    @Override
    public void loadImage(String url, ImageView iv, int loadingImg, int errorImg, int defaultImg,
                          final LoadingBitmapListener listener) {
        loadImage(url, iv, getDrawable(loadingImg), getDrawable(errorImg), getDrawable(defaultImg), listener);
    }

    @Override
    public void loadImage(String url, ImageView iv, Drawable loadingImg, Drawable errorImg, Drawable defaultImg,
                          final LoadingBitmapListener listener) {
        if (isInit && iv != null && listener != null) {
            getRequestBuilder(url).apply(new RequestOptions()
                    .diskCacheStrategy(strategy)
                    .centerCrop()
                    .diskCacheStrategy(strategy)//
                    .placeholder(loadingImg)//
                    .error(errorImg)//
                    .fallback(defaultImg))
                    .listener(new RequestListener<Bitmap>() {

                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Bitmap> target, boolean isFirstResource) {
                            listener.loadingBitmapFailed();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model,
                                                       Target<Bitmap> target, DataSource dataSource,
                                                       boolean isFirstResource) {
                            listener.loadingBitmapSuccess(resource);
                            return false;
                        }
                    }).into(iv);
        }
    }

    @Override
    public void downloadImage(String url, final LoadingBitmapListener listener) {
        if (isInit && listener != null) {
            getRequestBuilder(url).apply(new RequestOptions()
                    .diskCacheStrategy(strategy))
                    .into(new SimpleTarget<Bitmap>() {

                        @Override
                        public void onResourceReady(@NonNull Bitmap resource,
                                                    @Nullable Transition<? super Bitmap> transition) {
                            if (resource == null) {
                                listener.loadingBitmapFailed();
                            } else {
                                listener.loadingBitmapSuccess(resource);
                            }
                        }
                    });
        }
    }

    @Override
    public void clearCache() {
        if (isInit) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Glide.get(AfApplication.getAppContext()).clearDiskCache();
                }
            }).start();
            Glide.get(AfApplication.getAppContext()).clearMemory();
        }
    }

    /**
     * 获取drawable
     *
     * @param resId
     * @return
     */
    private Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(AfApplication.getAppContext(), resId);
    }

}
