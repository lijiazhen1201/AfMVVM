package cn.appoa.afmvvm.share;

import android.content.Context;
import android.text.TextUtils;

import com.mob.MobSDK;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * ShareSdk封装工具类
 *
 * @see http://www.mob.com/wiki/detailed?wiki=ShareSDK_Android_Title_ksjc&id=14
 * @see https://github.com/MobClub/ShareSDK-for-Android
 */
public final class ShareSdkUtils {

    /**
     * 平台名称：1、QQ；2、QQ空间；3、微信；4、微信朋友圈；5、新浪微博
     */
    public static String[] PlatformName = {"",
            QQ.NAME, QZone.NAME,
            Wechat.NAME, WechatMoments.NAME,
            SinaWeibo.NAME};

    /**
     * 5.初始化MobSDK
     * 如果您没有在AndroidManifest中设置appliaction的类名，
     * MobSDK会将这个设置为com.mob.MobApplication，
     * 但如果您设置了，请在您自己的Application类中调用此方法
     *
     * @param appContext
     */
    public static void initShare(Context appContext) {
        MobSDK.init(appContext);
    }

    /**
     * 第三方登录
     *
     * @param nameId   平台名称Id
     * @param listener 登录事件回调
     */
    public static void thirdLogin(int nameId, PlatformActionListener listener) {
        try {
            thirdLogin(PlatformName[nameId], listener);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    /**
     * 第三方登录
     *
     * @param name     平台名称
     * @param listener 登录事件回调
     */
    public static void thirdLogin(String name, PlatformActionListener listener) {
        if (!TextUtils.isEmpty(name)) {
            Platform platform = ShareSDK.getPlatform(name);
            if (platform.isAuthValid()) {
                platform.removeAccount(true);// 移除授权
            }
            platform.SSOSetting(false);// 设置false表示使用SSO授权方式(使用了SSO授权后，有客户端的都会优先启用客户端授权，没客户端的则任然使用网页版进行授权)
            platform.setPlatformActionListener(listener);
            // platform.authorize();//单独授权
            platform.showUser(null);// 授权并获取用户信息
        }
    }

    /**
     * 退出第三方登录
     *
     * @param nameId 平台名称Id
     */
    public static void thirdLogout(int nameId) {
        try {
            thirdLogout(PlatformName[nameId]);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出第三方登录
     *
     * @param name 平台名称
     */
    public static void thirdLogout(String name) {
        if (!TextUtils.isEmpty(name)) {
            Platform platform = ShareSDK.getPlatform(name);
            if (platform.isAuthValid()) {
                platform.removeAccount(true);// 移除授权
            }
        }
        // 如果要删除授权信息，重新授权
        ShareSDK.removeCookieOnAuthorize(true);
    }


    /**
     * 分享网页
     *
     * @param nameId   平台名称Id
     * @param data     分享数据
     * @param listener 分享事件回调
     */
    public static void shareUrl(int nameId, ShareData data, PlatformActionListener listener) {
        try {
            shareUrl(PlatformName[nameId], data, listener);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享网页
     *
     * @param name     平台名称
     * @param data     分享数据
     * @param listener 分享事件回调
     */
    public static void shareUrl(String name, ShareData data, PlatformActionListener listener) {
        if (data == null) {
            return;
        }
        Platform.ShareParams sp = new Platform.ShareParams();
        if (TextUtils.equals(name, QQ.NAME)) {
            sp.setTitle(data.title);
            sp.setTitleUrl(data.titleUrl);
            sp.setText(data.text);
            if (!TextUtils.isEmpty(data.imageUrl)) {
                sp.setImageUrl(data.imageUrl);
            } else if (!TextUtils.isEmpty(data.imagePath)) {
                sp.setImagePath(data.imagePath);
            }
        } else if (TextUtils.equals(name, QZone.NAME)) {
            sp.setTitle(data.title);
            sp.setTitleUrl(data.titleUrl);
            sp.setText(data.text);
            if (!TextUtils.isEmpty(data.imageUrl)) {
                sp.setImageUrl(data.imageUrl);
            } else if (!TextUtils.isEmpty(data.imagePath)) {
                sp.setImagePath(data.imagePath);
            }
            sp.setSite(data.site);
            sp.setSiteUrl(data.siteUrl);
        } else if (TextUtils.equals(name, Wechat.NAME) ||
                TextUtils.equals(name, WechatMoments.NAME)) {
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setTitle(data.title);
            sp.setText(data.text);
            if (!TextUtils.isEmpty(data.imageUrl)) {
                sp.setImageUrl(data.imageUrl);
            } else if (!TextUtils.isEmpty(data.imagePath)) {
                sp.setImagePath(data.imagePath);
            } else if (data.imageData != null) {
                sp.setImageData(data.imageData);
            }
            sp.setUrl(data.url);
        } else if (TextUtils.equals(name, SinaWeibo.NAME)) {
            sp.setText(data.title + "\n" + data.text + "\n" + data.url);
            if (!TextUtils.isEmpty(data.imageUrl)) {
                sp.setImageUrl(data.imageUrl);
            } else if (!TextUtils.isEmpty(data.imagePath)) {
                sp.setImagePath(data.imagePath);
            } else if (data.imageData != null) {
                sp.setImageData(data.imageData);
            }
        }
        Platform platform = ShareSDK.getPlatform(name);
        platform.setPlatformActionListener(listener);
        platform.share(sp);
    }

    /**
     * 分享图片
     *
     * @param nameId   平台名称Id
     * @param data     分享数据
     * @param listener 分享事件回调
     */
    public static void sharePic(int nameId, ShareData data, PlatformActionListener listener) {
        try {
            sharePic(PlatformName[nameId], data, listener);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享图片
     *
     * @param name     平台名称
     * @param data     分享数据
     * @param listener 分享事件回调
     */
    public static void sharePic(String name, ShareData data, PlatformActionListener listener) {
        if (data == null) {
            return;
        }
        if (TextUtils.isEmpty(data.imageUrl) &&
                TextUtils.isEmpty(data.imagePath) && data.imageData == null) {
            return;
        }
        Platform.ShareParams sp = new Platform.ShareParams();
        if (TextUtils.equals(name, Wechat.NAME) ||
                TextUtils.equals(name, WechatMoments.NAME)) {
            sp.setShareType(Platform.SHARE_IMAGE);
        }
        if (!TextUtils.isEmpty(data.imageUrl)) {
            sp.setImageUrl(data.imageUrl);
        } else if (!TextUtils.isEmpty(data.imagePath)) {
            sp.setImagePath(data.imagePath);
        } else if (data.imageData != null) {
            sp.setImageData(data.imageData);
        }
        Platform platform = ShareSDK.getPlatform(name);
        platform.setPlatformActionListener(listener);
        platform.share(sp);
    }

}
