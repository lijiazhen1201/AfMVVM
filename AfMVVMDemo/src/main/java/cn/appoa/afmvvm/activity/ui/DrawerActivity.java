package cn.appoa.afmvvm.activity.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityDrawerBinding;
import cn.appoa.afmvvm.fragment.IndexFragment;
import cn.appoa.afmvvm.router.RouterActivityPath;
import cn.appoa.afmvvm.viewmodel.IndexViewModel;

/**
 * 主页Activity
 */
@Route(path = RouterActivityPath.ACTIVITY_DRAWER)
public class DrawerActivity extends BaseActivity<ActivityDrawerBinding, IndexViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        onGetInstanceState(savedInstanceState);
        return R.layout.activity_drawer;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        if (binding != null) {
            binding.mDrawerView.initManager(mFragmentManager)
                    .initFragment(
                            binding.mDrawerView.addIndexItem("我的", 0,
                                    IndexFragment.class.getCanonicalName(), getTestBundle("Mine")),
                            binding.mDrawerView.addIndexItem("首页", 0,
                                    IndexFragment.class.getCanonicalName(), getTestBundle("Home"))
                    ).create();
        }
    }

    /**
     * 测试用传递数据
     *
     * @param pageName
     * @return
     */
    private Bundle getTestBundle(String pageName) {
        Bundle bundle = new Bundle();
        bundle.putString("pageName", pageName);
        return bundle;
    }

    @Override
    public void initData() {

    }

    /**
     * 获取保存的Fragment
     *
     * @param savedInstanceState
     */
    private void onGetInstanceState(Bundle savedInstanceState) {
        if (binding != null) {
            binding.mDrawerView.onGetInstanceState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存当前Fragment
        if (binding != null) {
            binding.mDrawerView.onSaveInstanceState(outState);
        }
    }

    @Override
    public boolean enableSliding() {
        return false;
    }
}
