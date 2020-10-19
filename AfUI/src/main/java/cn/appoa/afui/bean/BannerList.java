package cn.appoa.afui.bean;

import java.io.Serializable;

/**
 * 轮播图
 */
public class BannerList implements Serializable {

    public String id;
    public String bannerImg;//轮播图片
    public String bannerType;//轮播类型
    public String bannerTo;//轮播跳转id
    public String bannerInfo;//轮播文章详情

    public BannerList() {
    }

    public BannerList(String img) {
        this.bannerImg = img;
    }

    /**
     * 获取轮播类型
     *
     * @return
     */
    public int getBannerType() {
        try {
            return Integer.parseInt(bannerType);
        } catch (Exception e) {
            return -1;
        }
    }
}
