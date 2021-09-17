package cn.appoa.afmvvm.fragment;

import android.view.LayoutInflater;

import com.donkingliang.consecutivescroller.ConsecutiveScrollerLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.adapter.RefreshBeanAdapter;
import cn.appoa.afmvvm.base.BaseScrollerFragment;
import cn.appoa.afmvvm.bean.RefreshBean;
import cn.appoa.afmvvm.databinding.FragmentRefreshScrollLayoutBinding;

/**
 * 下拉刷新的ConsecutiveScrollerLayout
 */
public class SmartRefreshScrollLayoutFragment extends BaseScrollerFragment {

    private FragmentRefreshScrollLayoutBinding mBinding;

    @Override
    public ConsecutiveScrollerLayout initScrollerLayoutView() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                R.layout.fragment_refresh_scroll_layout, null, false);
        return (ConsecutiveScrollerLayout) mBinding.getRoot();
    }

    private List<RefreshBean> dataList;
    private RefreshBeanAdapter adapter;

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        dataList = new ArrayList<>();
        adapter = new RefreshBeanAdapter(getActivity(), dataList, R.layout.item_refresh_bean_list);
        mBinding.lvRefresh.setAdapter(adapter);
    }

    @Override
    public void initData() {
        getBannerData();
        getCategoryData();
        getData1();
        getData2();
        getData3();
        initDataList();
    }

    /**
     * 轮播
     */
    private void getBannerData() {
    }

    /**
     * 分类
     */
    private void getCategoryData() {
    }

    /**
     * 推荐1
     */
    private void getData1() {
    }

    /**
     * 推荐2
     */
    private void getData2() {
    }

    /**
     * 推荐3
     */
    private void getData3() {
    }

    @Override
    public void initDataList() {
        if (pageindex == 1) {
            dataList.clear();
            adapter.setNewData(dataList);
        }
        //TODO 模拟数据
        List<RefreshBean> datas = new ArrayList<>();
        if (pageindex < 4) {
            for (int i = 0; i < 20; i++) {
                RefreshBean data = new RefreshBean();
                data.ID = String.valueOf((pageindex - 1) * 20 + (i + 1));
                data.Title = "测试标题" + data.ID;
                datas.add(data);
            }
        }
        if (datas != null && datas.size() > 0) {
            isMore = true;
            dataList.addAll(datas);
            adapter.setNewData(dataList);
        }
        stopRefresh();
        stopLoadMore();
//        Map<String, String> params = new HashMap<>();
//        params.put("pageindex", pageindex + "");
//        params.put("pagesize", 10 + "");
//        AfOkGo.request(API.faq_list, params).tag(this.getRequestTag())
//                .execute(new OkGoDatasListener<RefreshBean>
//                        (this, "问题列表", RefreshBean.class) {
//                    @Override
//                    public void onDatasResponse(List<RefreshBean> datas) {
//                        if (datas != null && datas.size() > 0) {
//                            isMore = true;
//                            dataList.addAll(datas);
//                            adapter.setNewData(dataList);
//                        }
//                    }
//
//                    @Override
//                    public void onSuccess(Response<String> responses) {
//                        stopRefresh();
//                        stopLoadMore();
//                        super.onSuccess(responses);
//                    }
//
//                    @Override
//                    public void onError(Response<String> error) {
//                        stopRefresh();
//                        stopLoadMore();
//                        super.onError(error);
//                        //TODO 模拟数据
//                        List<RefreshBean> datas = new ArrayList<>();
//                        if (pageindex < 4) {
//                            for (int i = 0; i < 20; i++) {
//                                RefreshBean data = new RefreshBean();
//                                data.ID = String.valueOf((pageindex - 1) * 20 + (i + 1));
//                                data.Title = "测试标题" + data.ID;
//                                datas.add(data);
//                            }
//                        }
//                        onDatasResponse(datas);
//                    }
//                });
    }

}
