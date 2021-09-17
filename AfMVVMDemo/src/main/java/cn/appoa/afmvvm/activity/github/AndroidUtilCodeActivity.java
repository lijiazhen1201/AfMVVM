package cn.appoa.afmvvm.activity.github;

import android.os.Bundle;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityAndroidUtilCodeBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;

/**
 * 安卓工具类库
 */
public class AndroidUtilCodeActivity extends BaseActivity<ActivityAndroidUtilCodeBinding, TitleBarViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_android_util_code;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "AndroidUtilCode", true);
    }

    @Override
    public void initData() {
    }
}
