package cn.appoa.afrefresh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import androidx.lifecycle.Observer;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afbase.fragment.AfFragment;
import cn.appoa.afrefresh.AfRefresh;
import cn.appoa.afrefresh.BR;
import cn.appoa.afrefresh.R;
import cn.appoa.afrefresh.databinding.FragmentPullToRefreshBaseBinding;
import cn.appoa.afrefresh.mvvm.PullToRefreshModel;
import cn.appoa.afrefresh.mvvm.PullToRefreshViewModel;


/**
 * 下拉刷新Fragment
 *
 * @param <V> 刷新的View
 */
public abstract class PullToRefreshBaseFragment<V extends View>
        extends AfFragment<FragmentPullToRefreshBaseBinding, PullToRefreshViewModel>
        implements OnRefreshListener, OnLoadMoreListener {

    @Override
    public PullToRefreshViewModel initViewModel() {
        return new PullToRefreshViewModel(AfApplication.getApplication(), new PullToRefreshModel());
    }

    @Override
    public int initContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_pull_to_refresh_base;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.onSuccessResponse.observe(this, new Observer<String>() {

            @Override
            public void onChanged(String response) {
                onSuccessResponse(response);
            }
        });
        // 取消自动加载更多
        binding.refreshLayout.setEnableAutoLoadMore(false);
        AfRefresh.setRefreshLayout(binding.refreshLayout);
        setRefreshLayoutHeader();
        setRefreshLayoutFooter();
        initRefreshLayout();
        refreshView = onCreateRefreshView();
        if (refreshView != null) {
            binding.refreshLayout.setRefreshContent(refreshView, -1, -1);
            initView();
        }
    }

    /**
     * 下拉刷新头相关设置
     */
    protected void setRefreshLayoutHeader() {
        ClassicsHeader header = new ClassicsHeader(getActivity());
        AfRefresh.setClassicsHeader(header);
        binding.refreshLayout.setRefreshHeader(header);
    }

    /**
     * 下拉刷新脚相关设置
     */
    protected void setRefreshLayoutFooter() {
        ClassicsFooter footer = new ClassicsFooter(getActivity());
        AfRefresh.setClassicsFooter(footer);
        binding.refreshLayout.setRefreshFooter(footer);
    }

    /**
     * 刷新布局相关设置
     */
    public abstract void initRefreshLayout();

    /**
     * 刷新控件
     */
    public V refreshView;

    /**
     * 初始化刷新控件
     *
     * @return
     */
    public abstract V onCreateRefreshView();

    /**
     * 分页
     */
    protected int pageindex = 1;

    /**
     * 是否是首次
     */
    protected boolean isFirst = true;

    /**
     * 是否能加载更多
     */
    protected boolean isMore = false;

    /**
     * 是否正在网络访问
     */
    protected boolean isRequest = false;

    /**
     * 是否初始化完毕
     */
    protected boolean isInit = false;

    /**
     * 初始化控件
     */
    protected void initView() {
        pageindex = 1;
        isFirst = true;
        isMore = false;
        isRequest = false;
        initTopView();
        initHeaderView();
        initFooterView();
        initBottomView();
        initEndView();
        initRefreshView();
        isBoth = setRefreshMode();
        initListener();
        isInit = true;
    }

    /**
     * 初始化顶布局
     */
    public void initTopView() {

    }

    /**
     * 初始化头布局
     */
    public void initHeaderView() {

    }

    /**
     * 初始化脚部局
     */
    public void initFooterView() {

    }

    /**
     * 初始化底布局
     */
    public void initBottomView() {

    }

    /**
     * 初始化置顶布局
     */
    public void initEndView() {

    }

    /**
     * 刷新控件
     */
    public abstract void initRefreshView();

    /**
     * 刷新模式(true：下拉刷新和上拉加载更多false：下拉刷新)
     */
    protected boolean isBoth = false;

    /**
     * 设置刷新模式
     *
     * @return true：下拉刷新和上拉加载更多false：下拉刷新
     */
    public abstract boolean setRefreshMode();

    /**
     * 监听
     */
    protected void initListener() {
        if (isBoth) {
            // 下拉刷新和上拉加载
            setEnableRefreshLoadMore(true, true);
            binding.refreshLayout.setOnRefreshListener(this);
            binding.refreshLayout.setOnLoadMoreListener(this);
        } else {
            // 下拉刷新
            setEnableRefreshLoadMore(true, false);
            binding.refreshLayout.setOnRefreshListener(this);
        }
    }

    /**
     * 设置是否可下拉刷新和上拉加载更多
     *
     * @param enableRefresh  是否可下拉刷新
     * @param enableLoadMore 是否可上拉加载更多
     */
    public void setEnableRefreshLoadMore(boolean enableRefresh, boolean enableLoadMore) {
        if (binding.refreshLayout != null) {
            binding.refreshLayout.setEnableRefresh(enableRefresh);
            binding.refreshLayout.setEnableLoadMore(enableLoadMore);
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        if (isInit) {
            pageindex = 1;
            isFirst = true;
            isMore = false;
            onRefresh();
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (isInit) {
            if (isMore) {
                pageindex++;
                isFirst = false;
                isMore = false;
                onLoadMore();
            } else {
                stopLoadMore();
            }
        }
    }

    /**
     * 下拉刷新
     */
    public abstract void onRefresh();

    /**
     * 上拉加载更多
     */
    public abstract void onLoadMore();

    /**
     * 开始刷新
     */
    public void startRefresh() {
        if (binding.refreshLayout != null) {
            binding.refreshLayout.autoRefresh();
        }
    }

    /**
     * 停止刷新
     */
    public void stopRefresh() {
        if (binding.refreshLayout != null) {
            binding.refreshLayout.finishRefresh(500);
        }
    }

    /**
     * 开始加载更多
     */
    public void startLoadMore() {
        if (binding.refreshLayout != null) {
            binding.refreshLayout.autoLoadMore();
        }
    }

    /**
     * 停止加载更多
     */
    public void stopLoadMore() {
        if (binding.refreshLayout != null) {
            binding.refreshLayout.finishLoadMore(500);
        }
    }

    /**
     * 刷新数据
     */
    public void refresh() {
        onRefresh(binding.refreshLayout);
    }

    /**
     * 置顶
     */
    public abstract void toScrollTop();

    /**
     * 滚动到指定位置
     *
     * @param position
     */
    public abstract void smoothToPosition(int position);

    /**
     * 访问成功
     *
     * @param response
     */
    public void onSuccessResponse(String response) {
        isRequest = false;
        if (pageindex == 1) {
            stopRefresh();
        } else {
            stopLoadMore();
        }
    }

}