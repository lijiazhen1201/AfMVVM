package cn.appoa.afmvvm.activity.github;

import android.os.Bundle;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityQmuiAndroidBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;

/**
 * QMUI界面封装
 */
public class QMUI_AndroidActivity extends BaseActivity<ActivityQmuiAndroidBinding, TitleBarViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_qmui_android;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "QMUI_Android", true);
    }

    @Override
    public void initData() {
    }
}
