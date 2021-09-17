package cn.appoa.afmvvm.activity.widget;

import android.graphics.Color;
import android.os.Bundle;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivitySuperImageViewBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afutils.res.SizeUtils;

/**
 * 圆形，圆角，带边框的ImageView
 */
public class SuperImageViewActivity extends BaseActivity<ActivitySuperImageViewBinding, TitleBarViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_super_image_view;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "SuperImageView", true);
    }

    @Override
    public void initData() {
        //设置类型0默认1圆形2矩形3椭圆(不同圆角)
        binding.ivAvatar.setShapeType(0);
        //设置圆角大小(仅在shapeType=2时候有效)
        binding.ivAvatar.setRadius(SizeUtils.dp2px(mActivity, 10.0f));
        //设置圆角大小(仅在shapeType=3时候有效)
        binding.ivAvatar.setRadius(
                SizeUtils.dp2px(mActivity, 0.0f),
                SizeUtils.dp2px(mActivity, 5.0f),
                SizeUtils.dp2px(mActivity, 10.0f),
                SizeUtils.dp2px(mActivity, 20.0f));
        //分别设置圆角大小(仅在shapeType=3时候有效)
        binding.ivAvatar.setRadiusTopLeft(SizeUtils.dp2px(mActivity, 0.0f));
        binding.ivAvatar.setRadiusTopRight(SizeUtils.dp2px(mActivity, 5.0f));
        binding.ivAvatar.setRadiusBottomLeft(SizeUtils.dp2px(mActivity, 10.0f));
        binding.ivAvatar.setRadiusBottomRight(SizeUtils.dp2px(mActivity, 20.0f));
        //设置边框颜色
        binding.ivAvatar.setBorderColor(Color.BLACK);
        //设置边框宽度
        binding.ivAvatar.setBorderWidth(SizeUtils.dp2px(mActivity, 2.0f));
        //设置宽高比
        binding.ivAvatar.setRatio(0.5f);
        binding.ivAvatar.setRatio(1,1);
        //设置按下颜色(暂无效果)
        binding.ivAvatar.setPressColor(Color.GRAY);
        //设置按下透明度(暂无效果)
        binding.ivAvatar.setPressAlpha(1);
    }
}
