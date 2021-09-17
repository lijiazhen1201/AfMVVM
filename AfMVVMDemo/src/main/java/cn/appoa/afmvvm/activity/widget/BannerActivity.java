package cn.appoa.afmvvm.activity.widget;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityBannerBinding;
import cn.appoa.afui.bean.BannerList;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;

/**
 * 轮播图
 */
public class BannerActivity extends BaseActivity<ActivityBannerBinding, TitleBarViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_banner;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public TitleBarViewModel initViewModel() {
        return new TitleBarViewModel(AfApplication.getApplication(), new TitleBarModel());
    }

    @Override
    public void initViewObservable() {
        viewModel.setDefaultTitleBar(R.drawable.back_black, "轮播图", true);
    }

    @Override
    public void initData() {
        //设置比例
        binding.mBannerView.setBannerRatio(2, 1);
        //TODO 模拟数据
        List<BannerList> datas = new ArrayList<>();
        datas.add(new BannerList("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568955747593&di=4c662eda1ad71673cb2a1f76c4069eee&imgtype=0&src=http%3A%2F%2Fwww.hubei.gov.cn%2Fmlhb%2Flyms%2F201507%2FW020150703539666970590.jpg"));
        datas.add(new BannerList("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568955757883&di=3a5fd7dc5728dd029aa729011cc9f278&imgtype=0&src=http%3A%2F%2Fimg.alicdn.com%2Fimgextra%2Fi1%2F3166929863%2FTB2Xiygh88lpuFjSspaXXXJKpXa_%2521%25213166929863.jpg"));
        datas.add(new BannerList("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568955768810&di=071a9335916bf7ddfab2a476c15aeb66&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171106%2F06112e1362464829a36e7734fb2c6d09.jpeg"));
        datas.add(new BannerList("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568955782087&di=4657006e40179a1afdab894c70af777e&imgtype=0&src=http%3A%2F%2Fm.tuniucdn.com%2Ffilebroker%2Fcdn%2Fsnc%2F9e%2F7d%2F9e7dafa44f55d361c1877e5c00f5d2b6_w800_h400_c1_t0.jpg"));
        binding.mBannerView.setBannerList(datas);
    }
}
