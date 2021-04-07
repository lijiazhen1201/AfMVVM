package cn.appoa.afrefresh.fragment;

import androidx.core.widget.NestedScrollView;

/**
 * 下拉刷新的NestedScrollView
 */
public abstract class PullToRefreshNestedScrollFragment extends PullToRefreshBaseFragment<NestedScrollView> {

    @Override
    public void initRefreshLayout() {

    }

    @Override
    public void initRefreshView() {

    }

    @Override
    public NestedScrollView onCreateRefreshView() {
        return initNestedScrollView();
    }


    @Override
    public boolean setRefreshMode() {
        return true;
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMore() {
        initDataList();
    }

    @Override
    public void toScrollTop() {
        if (refreshView != null) {
            refreshView.scrollTo(0, 0);
        }
    }

    @Override
    public void smoothToPosition(int position) {

    }

    /**
     * 初始化NestedScrollView
     *
     * @return
     */
    public abstract NestedScrollView initNestedScrollView();

    /**
     * 获取列表数据
     */
    public abstract void initDataList();
}
