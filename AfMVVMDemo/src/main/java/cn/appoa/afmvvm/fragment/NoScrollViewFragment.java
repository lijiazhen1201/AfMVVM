package cn.appoa.afmvvm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import cn.appoa.afbase.adapter.AfAdapter;
import cn.appoa.afbase.adapter.AfHolder;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afbase.mvvm.BaseModel;
import cn.appoa.afbase.mvvm.BaseViewModel;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseFragment;
import cn.appoa.afmvvm.databinding.FragmentNoScrollViewBinding;
import cn.appoa.afrefresh.divider.ListItemDecoration;
import cn.appoa.afui.widget.noscroll.NoScrollGridView;
import cn.appoa.afui.widget.noscroll.NoScrollImageGridView;
import cn.appoa.afui.widget.noscroll.NoScrollListView;
import cn.appoa.afui.widget.noscroll.NoScrollRecyclerView;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * ScrollView嵌套滑动布局
 */
public class NoScrollViewFragment extends BaseFragment<FragmentNoScrollViewBinding, BaseViewModel> {

    @Override
    public int initContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_no_scroll_view;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public BaseViewModel initViewModel() {
        return new BaseViewModel(AfApplication.getApplication(), new BaseModel());
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void initData() {
        //TODO 模拟数据
        List<List<String>> datas = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<String> titles = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                titles.add("标题" + (j + 1));
            }
            datas.add(titles);
        }
        binding.mListView.setAdapter(new AfAdapter<List<String>>(mActivity, datas, R.layout.item_no_scroll_view) {
            @Override
            public void init(AfHolder zmHolder, List<String> strings, int position) {
                NoScrollListView mListView = zmHolder.getView(R.id.mListView);
                NoScrollGridView mGridView = zmHolder.getView(R.id.mGridView);
                NoScrollImageGridView mImageGridView = zmHolder.getView(R.id.mImageGridView);
                NoScrollRecyclerView mRecyclerView = zmHolder.getView(R.id.mRecyclerView);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
                if (mRecyclerView.getItemDecorationCount() == 0) {
                    //防止重复添加分割线
                    mRecyclerView.addItemDecoration(new ListItemDecoration(mActivity));
                }
                //设置数据
                mListView.setAdapter(new AfAdapter<String>(mActivity, strings, R.layout.item_main) {
                    @Override
                    public void init(AfHolder zmHolder, String t, int position) {
                        zmHolder.setText(R.id.tv_main, t);
                        zmHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShort(mActivity, "跳转详情", false);
                            }
                        });
                    }
                });
                mGridView.setAdapter(new AfAdapter<String>(mActivity, strings, R.layout.item_main) {
                    @Override
                    public void init(AfHolder zmHolder, String t, int position) {
                        zmHolder.setText(R.id.tv_main, t);
                        zmHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShort(mActivity, "跳转详情", false);
                            }
                        });
                    }
                });
                mImageGridView.setAdapter(new AfAdapter<String>(mActivity, strings, R.layout.item_main) {
                    @Override
                    public void init(AfHolder zmHolder, String t, int position) {
                        zmHolder.setText(R.id.tv_main, t);
                        zmHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShort(mActivity, "跳转详情", false);
                            }
                        });
                    }
                });
                mRecyclerView.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_main, strings) {

                    @Override
                    protected void convert(BaseViewHolder zmHolder, String t) {
                        zmHolder.setText(R.id.tv_main, t);
                        zmHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShort(mActivity, "跳转详情", false);
                            }
                        });
                    }
                });
                // NoScrollGridView和NoScrollImageGridView的区别：
                // NoScrollGridView点击空白区域没有响应，
                // NoScrollImageGridView点击空白区域可以响应Item点击事件
                zmHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShort(mActivity, "跳转详情", false);
                    }
                });
            }
        });
    }
}
