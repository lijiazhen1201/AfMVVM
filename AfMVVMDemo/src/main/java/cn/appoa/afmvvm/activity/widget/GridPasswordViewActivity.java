package cn.appoa.afmvvm.activity.widget;

import android.os.Bundle;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityGridPasswordViewBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afui.widget.gridpassword.GridPasswordView;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * 支付密码输入框
 */
public class GridPasswordViewActivity extends BaseActivity<ActivityGridPasswordViewBinding, TitleBarViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_grid_password_view;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "GridPasswordView", true);
    }

    @Override
    public void initData() {
        //是否显示密码
        //binding.mGridPasswordView.setPasswordVisibility(true);
        //其他设置看GitHub上说明
        //事件监听
        binding.mGridPasswordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                //文字改变
            }

            @Override
            public void onInputFinish(String psw) {
                //输入完成
                ToastUtils.showShort(mActivity, psw, true);
                //清空输入框
                //binding.mGridPasswordView.clearPassword();
            }
        });
        //自动弹起键盘
        binding.mGridPasswordView.setForceInputViewGetFocus();
    }
}
