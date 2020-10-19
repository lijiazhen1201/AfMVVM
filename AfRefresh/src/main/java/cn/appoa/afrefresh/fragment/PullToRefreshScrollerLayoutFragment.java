package cn.appoa.afrefresh.fragment;

import android.view.View;

import com.donkingliang.consecutivescroller.ConsecutiveScrollerLayout;

/**
 * 下拉刷新的ConsecutiveScrollerLayout
 *
 * @author https://github.com/donkingliang/ConsecutiveScroller
 */
public abstract class PullToRefreshScrollerLayoutFragment
        extends PullToRefreshBaseFragment<ConsecutiveScrollerLayout> {

    @Override
    public void initRefreshLayout() {

    }

    @Override
    public void initRefreshView() {

    }

    @Override
    public ConsecutiveScrollerLayout onCreateRefreshView() {
        return initScrollerLayoutView();
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
            View view = refreshView.getChildAt(0);
            if (view != null && !refreshView.isScrollTop()) {
                refreshView.scrollToChild(view);
            }
        }
    }

    @Override
    public void smoothToPosition(int position) {
        if (refreshView != null) {
            try {
                refreshView.scrollToChild(refreshView.getChildAt(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 滚动到指定控件位置
     *
     * @param view
     */
    public void smoothToView(View view) {
        if (refreshView != null && view != null) {
            refreshView.scrollToChild(view);
        }
    }

    /**
     * 初始化ConsecutiveScrollerLayout
     *
     * @return
     */
    public abstract ConsecutiveScrollerLayout initScrollerLayoutView();

    /**
     * 获取列表数据
     */
    public abstract void initDataList();

}
