package cn.appoa.afmvvm.activity.utils;

import android.os.Bundle;
import android.view.View;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityThreadUtilsBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afutils.file.ThreadUtils;
import cn.appoa.afutils.res.PhoneUtils;
import cn.appoa.afutils.res.ViewUtils;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * 主线程和子线程切换
 */
public class ThreadUtilsActivity extends BaseActivity<ActivityThreadUtilsBinding, TitleBarViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_thread_utils;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "ThreadUtils", true);
    }

    @Override
    public void initData() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        if (ViewUtils.isTextEmpty(binding.etLoginPhone)) {
            ToastUtils.showShort(mActivity, binding.etLoginPhone.getHint(), false);
            return;
        }
        String phone = ViewUtils.getText(binding.etLoginPhone);
        if (!PhoneUtils.isMobile(phone)) {
            ToastUtils.showShort(mActivity, "请输入正确的手机号", false);
            return;
        }
        if (ViewUtils.isTextEmpty(binding.etLoginPwd)) {
            ToastUtils.showShort(mActivity, binding.etLoginPwd.getHint(), false);
            return;
        }
        showLoading("正在登录...");
        //切换子线程
        ThreadUtils.runInBack(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //切换主线程
                ThreadUtils.runInMain(new Runnable() {
                    @Override
                    public void run() {
                        dismissLoading();
                        ToastUtils.showShort(mActivity, "登录成功", false);
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
        });
    }
}
