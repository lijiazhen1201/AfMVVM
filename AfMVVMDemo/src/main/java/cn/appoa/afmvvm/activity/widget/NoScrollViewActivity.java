package cn.appoa.afmvvm.activity.widget;

import android.os.Bundle;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import cn.appoa.afbase.adapter.AfPagerAdapter;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityNoScrollViewBinding;
import cn.appoa.afmvvm.fragment.NoScrollViewFragment;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * ScrollView嵌套滑动布局
 */
public class NoScrollViewActivity extends BaseActivity<ActivityNoScrollViewBinding, TitleBarViewModel>
        implements CompoundButton.OnCheckedChangeListener{

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_no_scroll_view;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "ScrollView嵌套滑动布局", true);
    }

    @Override
    public void initData() {
        binding.btnCanScroll.setChecked(true);
        binding.btnCanScroll.setOnCheckedChangeListener(this);
        binding.btnNoScroll.setOnCheckedChangeListener(this);

        //TODO 模拟数据
        List<Fragment> listFragment = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            listFragment.add(new NoScrollViewFragment());
        }
        binding.mViewPager.setAdapter(new AfPagerAdapter(mFragmentManager, listFragment));
        binding.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ToastUtils.showShort(mActivity, position + 1 + "", false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.btn_can_scroll:
                    binding.mViewPager.setNoScroll(false);
                    break;
                case R.id.btn_no_scroll:
                    binding.mViewPager.setNoScroll(true);
                    break;
                default:
                    break;
            }
        }
    }
}
