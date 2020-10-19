package cn.appoa.afmedia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;

import com.danikula.videocache.HttpProxyCacheServer;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afbase.loader.LoadingBitmapListener;
import cn.appoa.afmedia.AfMedia;
import cn.zjvd.Jzvd;
import cn.zjvd.JzvdStd;

/**
 * 视频播放
 */
public class AfVideoPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent(getIntent());
        initContent();
        initData();
    }

    protected String videoUrl;//视频地址
    protected String videoImage;//视频封面
    protected String videoTitle;//视频标题

    /**
     * intent传值
     *
     * @param intent
     */
    protected void initIntent(Intent intent) {
        videoUrl = intent.getStringExtra("videoUrl");
        if (TextUtils.isEmpty(videoUrl)) {
            finish();
        }
        videoImage = intent.getStringExtra("videoImage");
        if (videoImage == null) {
            videoImage = "";
        }
        videoTitle = intent.getStringExtra("videoTitle");
        if (videoTitle == null) {
            videoTitle = "";
        }
    }

    protected JzvdStd videoPlayer;//视频播放器

    protected void initContent() {
        videoPlayer = new JzvdStd(this);
        setContentView(videoPlayer);
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }


    protected void initData() {
        if (videoPlayer != null) {
            AfApplication.getImageLoader().downloadImage(videoImage, new LoadingBitmapListener() {
                @Override
                public void loadingBitmapSuccess(Bitmap bitmap) {
                    videoPlayer.posterImageView.setImageBitmap(bitmap);
                }

                @Override
                public void loadingBitmapFailed() {

                }
            });
            File file = new File(videoUrl);
            if (file.exists()) {
                //本地视频
                videoPlayer.setUp(videoUrl, videoTitle);
            } else {
                //网络视频
                HttpProxyCacheServer proxy = AfMedia.getInstance(getApplicationContext()).getProxy();
                String proxyUrl = proxy.getProxyUrl(videoUrl);
                videoPlayer.setUp(proxyUrl, videoTitle);
            }
        }
    }
}
