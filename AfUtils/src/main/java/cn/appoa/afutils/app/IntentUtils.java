package cn.appoa.afutils.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;

import cn.appoa.afutils.R;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * Intent工具类
 */
public class IntentUtils {

    /**
     * 打开浏览器
     *
     * @param mActivity
     * @param url
     */
    public static void openBrowser(Activity mActivity, String url) {
        if (mActivity == null) {
            return;
        }
        if (!TextUtils.isEmpty(url) && url.startsWith("http")) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            mActivity.startActivity(intent);
        }
    }

    /**
     * 查询物流信息
     *
     * @param mActivity
     * @param Courier   快递公司编号
     * @param CourierNO 快递单号
     */
    public static void searchCourier(Activity mActivity, String Courier, String CourierNO) {
        if (mActivity != null && !TextUtils.isEmpty(Courier) && !TextUtils.isEmpty(CourierNO)) {
            openBrowser(mActivity, "https://m.kuaidi100.com/index_all.html?type="
                    + Courier + "&postid=" + CourierNO);
        }
    }

    /**
     * 查询物流信息
     *
     * @param mActivity
     * @param CourierNO 快递单号
     */
    public static void searchCourier(Activity mActivity, String CourierNO) {
        if (mActivity != null && !TextUtils.isEmpty(CourierNO)) {
            openBrowser(mActivity, "https://page.cainiao.com/guoguo/app-myexpress-taobao/express-detail.html?mailNo=" + CourierNO);
        }
    }

    /**
     * 开启qq聊天
     *
     * @param mActivity
     * @param qq
     */
    public static void openQQ(Activity mActivity, String qq) {
        if (mActivity == null || TextUtils.isEmpty(qq)) {
            return;
        }
        if (AppUtils.checkApkExist(mActivity, "com.tencent.mobileqq")) {
            mActivity.startActivity(new Intent(Intent.ACTION_VIEW, //
                    Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + qq + "&version=1")));
        } else {
            ToastUtils.showShort(mActivity, R.string.no_installed_qq, false);
        }
    }

    /**
     * 分享图片到微信
     *
     * @param context
     * @param isTimeLine 是否分享到朋友圈
     * @param imageUris  图片uri
     * @param text       文字
     */
    public static void sendImageToWx(Context context, boolean isTimeLine,
                                     ArrayList<Uri> imageUris, String text) {
        if (context != null) {
            String packageName = "com.tencent.mm";
            if (AppUtils.checkApkExist(context, packageName)) {
                Intent intent = new Intent();
                ComponentName comp = null;
                if (isTimeLine) {
                    comp = new ComponentName(packageName, "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                } else {
                    comp = new ComponentName(packageName, "com.tencent.mm.ui.tools.ShareImgUI");
                }
                if (comp != null) {
                    intent.setComponent(comp);
                }
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("image/*");
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                intent.putExtra("Kdescription", text);
                context.startActivity(intent);
            } else {
                ToastUtils.showShort(context, R.string.no_installed_we_chat, true);
            }
        }
    }

    /**
     * 通知媒体库更新文件
     *
     * @param context
     * @param filePath 文件全路径
     */
    public static void scanFile(Context context, String filePath) {
        if (context == null || TextUtils.isEmpty(filePath) || //
                new File(filePath) == null || !new File(filePath).exists()) {
            return;
        }
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }
}
