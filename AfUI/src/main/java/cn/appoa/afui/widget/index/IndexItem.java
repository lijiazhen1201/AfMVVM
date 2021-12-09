package cn.appoa.afui.widget.index;

import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * 带底部导航的主页控件条目
 */
public class IndexItem {

    /**
     * 文字
     */
    public String label;
    /**
     * 图片（选择器）
     */
    public int icon;
    /**
     * fragment名称（XXXFragment.class.getCanonicalName()）
     */
    public String fragmentName;
    /**
     * 页面
     */
    public Fragment fragment;
    /**
     * 传递的数据
     */
    public Bundle bundle;

    public IndexItem(String label, @DrawableRes int icon, @NonNull String fragmentName) {
        this.label = label;
        this.icon = icon;
        this.fragmentName = fragmentName;
    }

    public IndexItem(String label, @DrawableRes int icon, @NonNull String fragmentName, Bundle bundle) {
        this.label = label;
        this.icon = icon;
        this.fragmentName = fragmentName;
        this.bundle = bundle;
    }
}
