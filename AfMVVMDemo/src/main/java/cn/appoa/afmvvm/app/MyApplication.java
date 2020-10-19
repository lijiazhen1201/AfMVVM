package cn.appoa.afmvvm.app;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

import androidx.multidex.MultiDex;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afbase.loader.AfImageLoader;
import cn.appoa.afimage.loader.ImageLoaderGlide;
import cn.appoa.afutils.encrypt.AESUtils;
import cn.appoa.afutils.net.LogUtils;

/**
 * 自定义Application
 */
public class MyApplication extends AfApplication {

    @Override
    public void initApplication() {
        // 分包
        MultiDex.install(this);
        //接口加密
        AESUtils.init("bWFsbHB3ZA==WNST", "lvsh_app");
        //路由框架
        if (LogUtils.isDebug) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 分包
        MultiDex.install(base);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }

    @Override
    public AfImageLoader initImageLoader() {
        return ImageLoaderGlide.getInstance();
    }

}
