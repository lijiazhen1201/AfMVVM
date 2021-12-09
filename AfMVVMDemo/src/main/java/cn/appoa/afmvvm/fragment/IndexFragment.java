package cn.appoa.afmvvm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseFragment;
import cn.appoa.afmvvm.databinding.FragmentIndexBinding;
import cn.appoa.afmvvm.viewmodel.IndexViewModel;
import cn.appoa.afui.BR;

/**
 * 主页Fragment
 */
public class IndexFragment extends BaseFragment<FragmentIndexBinding, IndexViewModel> {

    /**
     * 页面名称
     */
    private String pageName = "";

    @Override
    public int initContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_index;
    }

    @Override
    public void initArguments(Bundle arguments) {
        pageName = arguments.getString("pageName", "");
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.indexPage.set(pageName);
    }

    @Override
    public void initData() {

    }
}
