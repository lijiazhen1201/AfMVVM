package cn.appoa.afmvvm.activity.ui;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.lifecycle.Observer;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.bean.UserInfo;
import cn.appoa.afmvvm.databinding.ActivityLoginBinding;
import cn.appoa.afmvvm.router.RouterActivityPath;
import cn.appoa.afmvvm.viewmodel.LoginViewModel;

@Route(path = RouterActivityPath.ACTIVITY_LOGIN)
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.setDefaultTitleBar(R.drawable.back_black, "登录", true);
        viewModel.loginEvent.observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo user) {
                if (user != null) {
                    if (viewModel.third_type.get() > 0 && TextUtils.isEmpty(user.getPhone())) {
                        //三方登录绑定手机号
                        viewModel.user_id.set(user.getId());
                        viewModel.startActivityForRegister(3);
                    } else {
                        viewModel.loginSuccess(user);
                    }
                }
            }
        });
    }

    @Override
    public void initData() {

    }
}
