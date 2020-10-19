package cn.appoa.afmvvm.share;

import android.graphics.Bitmap;

import java.io.Serializable;


/**
 * 分享数据
 *
 * @see http://www.mob.com/wiki/detailed?wiki=ShareSDK_Android_APISHARE_title_dsfptfxcssm&id=14
 * <p>
 * 有imageUrl（”网络图片链接”）、imagePath(“/sdcard/abc.jpg”)、imageData(bitmap)是三选一
 */
public class ShareData implements Serializable {

    //QQ分享
    public String title;//最多30个字符
    public String titleUrl;
    public String text;//最多40个字符
    public String imagePath;
    public String imageUrl;

    //QQ空间发表说说
    //public String text;
    //public String imagePath;
    //public String imageUrl;
    public String site;
    public String siteUrl;

    //QQ空间分享图文(分享时一定要携带title、titleUrl、site、siteUrl)
    //public String title;//最多200个字符
    //public String titleUrl;
    //public String text;//最多600个字符
    //public String imagePath;
    //public String imageUrl;
    //public String site;//分享此内容的网站名称，仅在QQ空间使用
    //public String siteUrl;//分享此内容的网站地址，仅在QQ空间使用

    //微信分享网页
    //shareType(Platform.SHARE_WEBPAGE)
    //public String title;//512Bytes以内
    //public String text;//(朋友圈不显示此字段)1KB以内
    //public String imagePath;//10M以内(传递的imagePath路径不能超过10KB)
    //public String imageUrl;//10KB以内，图片打不开不能分享
    public Bitmap imageData;//10M以内
    public String url;

    //新浪微博分享图文
    //public String text;//140字符以内，微博分享链接是将链接写到text内
    //public String imagePath;//微博客户端分享图片不能大于2M，仅支持JPEG、GIF、PNG格式
    //public String imageUrl;
    //public String imageData;

    public ShareData() {
    }

    public ShareData(String title, String text,
                     String imagePath, String imageUrl, Bitmap imageData, String url) {
        this.title = title;
        this.titleUrl = url;
        this.text = text;
        this.imagePath = imagePath;
        this.imageUrl = imageUrl;
        this.site = title;
        this.siteUrl = url;
        this.imageData = imageData;
        this.url = url;
    }

    /**
     * 设置分享内容
     */
    public void setShareData(String title, String text,
                             String imagePath, String imageUrl, Bitmap imageData, String url) {
        this.title = title;
        this.titleUrl = url;
        this.text = text;
        this.imagePath = imagePath;
        this.imageUrl = imageUrl;
        this.site = title;
        this.siteUrl = url;
        this.imageData = imageData;
        this.url = url;
    }

}
