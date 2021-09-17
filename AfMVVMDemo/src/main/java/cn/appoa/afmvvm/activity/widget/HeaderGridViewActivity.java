package cn.appoa.afmvvm.activity.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.adapter.RefreshBeanAdapter;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.bean.RefreshBean;
import cn.appoa.afmvvm.databinding.ActivityHeaderGridViewBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * 可添加头布局和脚布局的GridView
 */
public class HeaderGridViewActivity extends BaseActivity<ActivityHeaderGridViewBinding, TitleBarViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_header_grid_view;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "HeaderGridView", true);
    }

    @Override
    public void initData() {
        initHeaderView();
        initFooterView();
        initDataView();
    }

    View headerView;

    /**
     * 添加头布局
     */
    private void initHeaderView() {
        if (headerView != null) {
            binding.mGridView.removeHeaderView(headerView);
            headerView = null;
        }
        headerView = View.inflate(mActivity, R.layout.fragment_refresh_test, null);
        TextView tv_refresh_test = (TextView) headerView.findViewById(R.id.tv_refresh_test);
        tv_refresh_test.setText("Header");
        tv_refresh_test.setBackgroundColor(Color.YELLOW);
        tv_refresh_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(mActivity, "Header", true);
            }
        });
        binding.mGridView.addHeaderView(headerView);
    }

    View footerView;

    /**
     * 添加脚布局
     */
    private void initFooterView() {
        if (footerView != null) {
            binding.mGridView.removeFooterView(footerView);
            footerView = null;
        }
        footerView = View.inflate(mActivity, R.layout.fragment_refresh_test, null);
        TextView tv_refresh_test = (TextView) footerView.findViewById(R.id.tv_refresh_test);
        tv_refresh_test.setText("Footer");
        tv_refresh_test.setBackgroundColor(Color.BLUE);
        tv_refresh_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(mActivity, "Footer", true);
            }
        });
        binding.mGridView.addFooterView(footerView);
    }

    /**
     * 模拟数据
     */
    private void initDataView() {
        List<RefreshBean> datas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            RefreshBean data = new RefreshBean();
            data.ID = i + 1 + "";
            data.Title = "测试标题" + (i + 1);
            datas.add(data);
        }
        RefreshBeanAdapter adapter = new RefreshBeanAdapter(mActivity, datas, R.layout.item_refresh_bean_grid);
        binding.mGridView.setAdapter(adapter);
    }
}
