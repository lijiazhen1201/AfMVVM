package cn.appoa.afui.widget.banner;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afui.R;
import cn.appoa.afui.activity.ShowBigImageListActivity;
import cn.appoa.afui.bean.BannerList;
import cn.appoa.afui.widget.layout.RatioRelativeLayout;

/**
 * 轮播图
 */
public class BannerView extends LinearLayout {

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private int layoutResId;
    private RatioRelativeLayout mBannerRatio;
    private com.youth.banner.Banner mBanner;

    private void init(Context context, AttributeSet attrs) {
        layoutResId = R.layout.item_banner;
        View view = View.inflate(context, R.layout.layout_banner_view, this);
        mBannerRatio = (RatioRelativeLayout) view.findViewById(R.id.mBannerRatio);
        mBanner = (com.youth.banner.Banner) view.findViewById(R.id.mBanner);
    }

    /**
     * 设置轮播图布局
     *
     * @param layoutResId
     */
    public void setLayoutResId(int layoutResId) {
        this.layoutResId = layoutResId;
    }

    /**
     * 获取轮播图布局
     *
     * @return
     */
    public RatioRelativeLayout getBannerRatioLayout() {
        return mBannerRatio;
    }

    /**
     * 获取轮播图
     *
     * @return
     */
    public com.youth.banner.Banner getBanner() {
        return mBanner;
    }

    /**
     * 设置banner样式
     *
     * @param bannerStyle
     */
    public void setBannerStyle(int bannerStyle) {
        mBanner.setBannerStyle(bannerStyle);
    }

    /**
     * 设置轮播图宽高比
     *
     * @param ratio
     */
    public void setBannerRatio(int ratio) {
        mBannerRatio.setRatio(ratio);
    }

    /**
     * 设置轮播图宽高比
     *
     * @param widthRatio
     * @param heightRatio
     */
    public void setBannerRatio(int widthRatio, int heightRatio) {
        mBannerRatio.setRatio(widthRatio, heightRatio);
    }

    /**
     * 设置轮播图
     *
     * @param datas
     */
    public void setBannerList(final List<BannerList> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        mBanner.setImages(datas)
                .setImageLoader(new ImageLoaderInterface<View>() {
                    @Override
                    public void displayImage(Context context, Object obj, View view) {
                        BannerList banner = (BannerList) obj;
                        ImageView iv_banner = (ImageView) view.findViewById(R.id.iv_banner);
                        AfApplication.getImageLoader().loadImage(banner.bannerImg, iv_banner);
                    }

                    @Override
                    public View createImageView(Context context) {
                        return View.inflate(context, layoutResId, null);
                    }
                })
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        //点击轮播图
                        BannerList banner = datas.get(position);
                        if (onBannerClickListener != null) {
                            onBannerClickListener.onBannerClick(banner);
                            return;
                        }
                        ArrayList<String> images = new ArrayList<>();
                        for (int i = 0; i < datas.size(); i++) {
                            images.add(datas.get(i).bannerImg);
                        }
                        getContext().startActivity(new Intent(getContext(), ShowBigImageListActivity.class)
                                .putExtra("page", position)
                                .putStringArrayListExtra("images", images));
                    }
                })
                .start();
    }

    private OnBannerClickListener onBannerClickListener;

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }

    public interface OnBannerClickListener {
        void onBannerClick(BannerList banner);
    }

}
