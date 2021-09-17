package cn.appoa.afmvvm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.activity.refresh.SmartRefreshActivity;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityRefreshBeanBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;

/**
 * 下拉刷新
 */
public class RefreshBeanActivity extends BaseActivity<ActivityRefreshBeanBinding, TitleBarViewModel>
        implements View.OnClickListener {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_refresh_bean;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "下拉刷新", true);
    }

    @Override
    public void initData() {
        binding.btnRefreshScrollview.setOnClickListener(this);
        binding.btnRefreshListview.setOnClickListener(this);
        binding.btnRefreshGridview.setOnClickListener(this);
        binding.btnRefreshRecyclerview.setOnClickListener(this);
        binding.btnRefreshScrollerlayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int type = 0;
        String title = null;
        switch (v.getId()) {
            case R.id.btn_refresh_scrollview:
                type = 1;
                title = "PullToRefreshScrollView";
                break;
            case R.id.btn_refresh_listview:
                type = 2;
                title = "PullToRefreshListView";
                break;
            case R.id.btn_refresh_gridview:
                type = 3;
                title = "PullToRefreshGridView";
                break;
            case R.id.btn_refresh_recyclerview:
                type = 4;
                title = "PullToRefreshRecyclerView";
                break;
            case R.id.btn_refresh_scrollerlayout:
                type = 5;
                title = "PullToRefreshScrollerLayout";
                break;
            default:
                break;
        }
        if (type > 0) {
            startActivity(new Intent(mActivity, SmartRefreshActivity.class)
                    .putExtra("type", type).putExtra("title", title));
        }
    }
}
