package cn.appoa.afmvvm.app;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

import androidx.multidex.MultiDex;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afbase.loader.AfImageLoader;
import cn.appoa.afimage.loader.ImageLoaderGlide;
import cn.appoa.afutils.encrypt.AESUtils;
import cn.appoa.afutils.file.ACache;
import cn.appoa.afutils.net.LogUtils;

/**
 * 自定义Application
 */
public class MyApplication extends AfApplication {

    //当前选中城市
    public static String local_city_id = "";
    public static String local_city_name = "";
    //AES加密的key
    public static String aes_key;

    @Override
    public void initApplication() {
        // 分包
        MultiDex.install(this);
        //AES加密
        aes_key = ACache.get(this).getAsString("aes_key");
        if (aes_key == null) {
            aes_key = "bWFsbHB3ZA==WNST";
        }
        AESUtils.init(aes_key);
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
