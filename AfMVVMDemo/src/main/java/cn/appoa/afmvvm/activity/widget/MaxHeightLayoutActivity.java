package cn.appoa.afmvvm.activity.widget;

import android.os.Bundle;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afbase.adapter.AfAdapter;
import cn.appoa.afbase.adapter.AfHolder;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityMaxHeightLayoutBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;

/**
 * 可设置最大高度的FrameLayout
 */
public class MaxHeightLayoutActivity extends BaseActivity<ActivityMaxHeightLayoutBinding, TitleBarViewModel>
        implements CompoundButton.OnCheckedChangeListener {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_max_height_layout;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "MaxHeightLayout", true);
    }

    @Override
    public void initData() {
        binding.btnData3.setOnCheckedChangeListener(this);
        binding.btnData30.setOnCheckedChangeListener(this);

        binding.btnData3.setChecked(true);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.btn_data3:
                    setData(3);
                    break;
                case R.id.btn_data30:
                    setData(30);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 模拟数据
     */
    private void setData(int size) {
        if (size == 0) {
            return;
        }
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            titles.add("标题" + (i + 1));
        }
        binding.mListView.setAdapter(new AfAdapter<String>(mActivity, titles, R.layout.item_main) {
            @Override
            public void init(AfHolder zmHolder, String t, int position) {
                zmHolder.setText(R.id.tv_main, t);
            }
        });
    }
}
