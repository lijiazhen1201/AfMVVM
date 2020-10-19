package cn.appoa.afrefresh.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/**
 * @param <T> 数据源
 *            RecyclerView下拉刷新
 */
public abstract class PullToRefreshRecyclerViewFragment<T, VH extends BaseViewHolder>
        extends PullToRefreshBaseFragment<RecyclerView>
        implements OnItemClickListener, OnItemChildClickListener {

    @Override
    public void initRefreshLayout() {
        binding.refreshLayout.setFocusableInTouchMode(true);
        binding.refreshLayout.setFocusable(true);
    }

    @Override
    public RecyclerView onCreateRefreshView() {
        return new RecyclerView(getActivity());
    }

    /**
     * 数据源
     */
    protected List<T> dataList;

    /**
     * 适配器
     */
    protected BaseQuickAdapter<T, VH> adapter;

    /**
     * 初始化适配器
     *
     * @return
     */
    public abstract BaseQuickAdapter<T, VH> initAdapter();

    @Override
    public void initRefreshView() {
        setRefreshView();
        dataList = new ArrayList<T>();
        adapter = initAdapter();
        layoutManager = initLayoutManager();
        if (layoutManager != null) {
            refreshView.setLayoutManager(layoutManager);
        }
        decor = initItemDecoration();
        if (decor != null) {
            refreshView.addItemDecoration(decor);
        }
        refreshView.setItemAnimator(new DefaultItemAnimator());
        if (adapter != null) {
            setAdapter();
            // 设置头布局和脚布局可以和空布局共存（默认不共存）
            adapter.setHeaderWithEmptyEnable(true);
            adapter.setFooterWithEmptyEnable(true);
            emptyView = setEmptyView();
            if (emptyView != null) {
                adapter.setEmptyView(emptyView);
            }
            initHeaderView(adapter);
            initFooterView(adapter);
            adapter.setNewInstance(dataList);
            adapter.notifyDataSetChanged();
            refreshView.setAdapter(adapter);
        }
    }

    /**
     * RecyclerView相关设置
     */
    public void setRefreshView() {
        //设置RecyclerView滑动到边缘时无效果模式
        refreshView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //RecyclerView调用notifyItemChanged闪烁问题
        //https://blog.csdn.net/u014537423/article/details/52777978
        ((SimpleItemAnimator) refreshView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    /**
     * 布局管理
     */
    protected RecyclerView.LayoutManager layoutManager;

    /**
     * 初始化布局管理
     *
     * @return
     */
    public abstract RecyclerView.LayoutManager initLayoutManager();

    /**
     * 设置布局管理
     *
     * @param layoutManager
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (isInit) {
            this.layoutManager = layoutManager;
            refreshView.setLayoutManager(this.layoutManager);
        }
    }

    /**
     * 分割线
     */
    protected RecyclerView.ItemDecoration decor;

    /**
     * 初始化分割线
     *
     * @return
     */
    public RecyclerView.ItemDecoration initItemDecoration() {
        return null;
    }

    /**
     * 设置分割线
     *
     * @param decor
     */
    public void setItemDecoration(RecyclerView.ItemDecoration decor) {
        if (isInit) {
            if (this.decor != null) {
                refreshView.removeItemDecoration(this.decor);
            }
            this.decor = decor;
            refreshView.addItemDecoration(this.decor);
        }
    }

    /**
     * BaseQuickAdapter相关设置
     */
    protected void setAdapter() {
        if (adapter != null) {
            adapter.setOnItemClickListener(this);
            adapter.setOnItemChildClickListener(this);
        }
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

    }

    @Override
    public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

    }

    /**
     * 数据为空的View
     */
    protected View emptyView;

    /**
     * 设置数据为空的View
     *
     * @return
     */
    public View setEmptyView() {
        return null;
    }

    /**
     * 初始化头布局
     */
    public void initHeaderView(BaseQuickAdapter<T, VH> adapter) {

    }

    /**
     * 初始化脚部局
     */
    public void initFooterView(BaseQuickAdapter<T, VH> adapter) {

    }

    @Override
    public boolean setRefreshMode() {
        return true;
    }

    @Override
    public void refresh() {
        //先清空数据，在刷新数据
        if (adapter != null) {
            dataList.clear();
            adapter.setNewInstance(dataList);
            adapter.notifyDataSetChanged();
        }
        super.refresh();
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMore() {
        initData();
    }

    @Override
    public void toScrollTop() {
        if (refreshView != null) {
            refreshView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void smoothToPosition(int position) {
        if (refreshView != null) {
            RecyclerView.LayoutManager manager = refreshView.getLayoutManager();
            if (manager != null && manager instanceof LinearLayoutManager) {
                LinearLayoutManager llm = (LinearLayoutManager) manager;
                llm.scrollToPositionWithOffset(position, 0);
                llm.setStackFromEnd(dataList.size() == 0);
            }
        }
    }

    @Override
    public void initData() {
        if (isRequest) {
            // 如果正在网络访问，就不重复访问
            if (pageindex == 1) {
                stopRefresh();
            } else {
                stopLoadMore();
            }
            return;
        }
        isRequest = true;
        viewModel.getDataList(setUrl(), setParams(), setHeaders());
    }

    /**
     * 网络访问地址
     *
     * @return
     */
    public abstract String setUrl();

    /**
     * 网络访问参数
     *
     * @return
     */
    public abstract Map<String, String> setParams();

    /**
     * 网络访问请求头
     *
     * @return
     */
    public Map<String, String> setHeaders() {
        return null;
    }

    @Override
    public void onSuccessResponse(String response) {
        super.onSuccessResponse(response);
        if (pageindex == 1) {
            dataList.clear();
        }
        List<T> datas = filterResponse(response);
        if (datas == null) {
            datas = new ArrayList<>();
        }
        if (datas.size() > 0) {
            isMore = true;
        }
        dataList.addAll(datas);
        if (adapter != null) {
            adapter.setNewInstance(dataList);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 返回的结果过滤
     *
     * @param response
     * @return
     */
    public abstract List<T> filterResponse(String response);

}
