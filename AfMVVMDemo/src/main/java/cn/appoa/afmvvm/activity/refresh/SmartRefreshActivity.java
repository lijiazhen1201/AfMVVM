package cn.appoa.afmvvm.activity.refresh;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivitySmartRefreshBinding;
import cn.appoa.afmvvm.fragment.SmartRefreshRecyclerViewFragment;
import cn.appoa.afmvvm.fragment.SmartRefreshScrollLayoutFragment;
import cn.appoa.afmvvm.model.MainMenuModel;
import cn.appoa.afmvvm.viewmodel.MainMenuViewModel;

/**
 * 下拉刷新列表
 */
public class SmartRefreshActivity extends BaseActivity<ActivitySmartRefreshBinding, MainMenuViewModel> {

    private int type;
    private String title;
    private boolean isGrid;

    @Override
    public void initIntent(Intent intent) {
        type = intent.getIntExtra("type", 1);
        title = intent.getStringExtra("title");
    }

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_smart_refresh;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public MainMenuViewModel initViewModel() {
        return new MainMenuViewModel(AfApplication.getApplication(), new MainMenuModel());
    }

    @Override
    public void initViewObservable() {
        viewModel.setDefaultTitleBar(R.drawable.back_white, title, false);
        if (type == 4) {
            viewModel.setRightImage(R.drawable.icon_list);
            viewModel.menuOnClickEvent.observe(this, new Observer<Void>() {
                @Override
                public void onChanged(Void v) {
                    isGrid = !isGrid;
                    viewModel.setRightImage(isGrid ? R.drawable.icon_grid : R.drawable.icon_list);
                    if (fragment != null) {
                        ((SmartRefreshRecyclerViewFragment) fragment).updataListGrid(isGrid);
                    }
                }
            });
        }
        binding.titleBar.tvBarTitle.setTextColor(Color.WHITE);
        binding.titleBar.rlBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorTheme));
    }

    private Fragment fragment = null;

    @Override
    public void initData() {
        switch (type) {
            case 1:
                //fragment = new SmartRefreshScrollViewFragment();
                break;
            case 2:
                //fragment = new SmartRefreshListViewFragment();
                break;
            case 3:
                //fragment = new SmartRefreshGridViewFragment();
                break;
            case 4:
                fragment = new SmartRefreshRecyclerViewFragment();
                break;
            case 5:
                fragment = new SmartRefreshScrollLayoutFragment();
                break;
            default:
                break;
        }
        if (fragment != null) {
            mFragmentManager.beginTransaction().replace(R.id.fl_fragment, fragment).commit();
        }
    }

    public View getTitlebar() {
        return binding.titleBar.rlBar;
    }
}
