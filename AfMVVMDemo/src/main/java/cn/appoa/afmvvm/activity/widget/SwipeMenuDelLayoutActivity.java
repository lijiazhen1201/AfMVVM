package cn.appoa.afmvvm.activity.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afbase.adapter.AfAdapter;
import cn.appoa.afbase.adapter.AfHolder;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivitySwipeMenuDelLayoutBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afui.widget.layout.SwipeMenuDelLayout;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * Item侧滑删除菜单Layout
 */
public class SwipeMenuDelLayoutActivity extends BaseActivity<ActivitySwipeMenuDelLayoutBinding, TitleBarViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_swipe_menu_del_layout;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "SwipeMenuDelLayout", true);
    }

    @Override
    public void initData() {
        //TODO 模拟数据
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            titles.add("标题" + (i + 1));
        }
        binding.mListView.setAdapter(new AfAdapter<String>(mActivity, titles, R.layout.item_swipe_menu_del) {
            @Override
            public void init(AfHolder zmHolder, String t, int position) {
                zmHolder.setText(R.id.tv_main, t);
                final SwipeMenuDelLayout delLayout = zmHolder.getView(R.id.delLayout);
                TextView tv_edit = zmHolder.getView(R.id.tv_edit);
                TextView tv_del = zmHolder.getView(R.id.tv_del);
                tv_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 编辑
                        ToastUtils.showShort(mContext, "编辑", false);
                        delLayout.quickClose();//关闭
                    }
                });
                tv_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 删除
                        ToastUtils.showShort(mContext, "删除", false);
                        delLayout.quickClose();//关闭
                    }
                });
            }
        });
    }
}
