package cn.appoa.afmvvm.activity.widget;

import android.os.Bundle;
import android.view.View;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityRatioRelativeLayoutBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afutils.app.KeyboardUtils;
import cn.appoa.afutils.res.ViewUtils;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * 可设置宽高比的RelativeLayout
 */
public class RatioRelativeLayoutActivity extends BaseActivity<ActivityRatioRelativeLayoutBinding, TitleBarViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_ratio_relative_layout;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "RatioRelativeLayout", true);
    }

    @Override
    public void initData() {
        binding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewUtils.isTextEmpty(binding.etWidth)) {
                    ToastUtils.showShort(mActivity, binding.etWidth.getHint(), false);
                    return;
                }
                if (ViewUtils.isTextEmpty(binding.etHeight)) {
                    ToastUtils.showShort(mActivity, binding.etHeight.getHint(), false);
                    return;
                }
                int width = Integer.parseInt(ViewUtils.getText(binding.etWidth));
                int height = Integer.parseInt(ViewUtils.getText(binding.etHeight));
                if (width == 0) {
                    ToastUtils.showShort(mActivity, "宽度必须大于0", false);
                    return;
                }
                binding.mRatioRelativeLayout.setRatio(width, height);
                KeyboardUtils.closeSoftInput(mActivity);
            }
        });
    }
}
