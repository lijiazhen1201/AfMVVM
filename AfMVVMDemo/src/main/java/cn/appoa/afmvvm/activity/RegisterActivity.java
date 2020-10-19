package cn.appoa.afmvvm.activity;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.lifecycle.Observer;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.bean.UserInfo;
import cn.appoa.afmvvm.databinding.ActivityRegisterBinding;
import cn.appoa.afmvvm.router.RouterActivityPath;
import cn.appoa.afmvvm.viewmodel.RegisterViewModel;

@Route(path = RouterActivityPath.ACTIVITY_REGISTER)
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> {

    /**
     * 1注册2找回密码3绑定手机号
     */
    private int type;
    private int third_type;
    private String open_id;
    private String name;
    private String photo;
    private String user_id;

    @Override
    public void initIntent(Intent intent) {
        type = intent.getIntExtra("type", 1);
        third_type = intent.getIntExtra("third_type", 1);
        open_id = intent.getStringExtra("open_id");
        name = intent.getStringExtra("name");
        photo = intent.getStringExtra("photo");
        user_id = intent.getStringExtra("user_id");
    }

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.getVerifyCodeEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String verifyCode) {
                //TODO 自动填写验证码
                //viewModel.getModel().code.set(verifyCode);
            }
        });
        viewModel.registerEvent.observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo user) {
                viewModel.registerSuccess(type, user);
            }
        });
        viewModel.findPwdEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void v) {
                viewModel.registerSuccess(type, null);
            }
        });
        viewModel.bindPhoneEvent.observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo user) {
                viewModel.registerSuccess(type, user);
            }
        });
    }

    @Override
    public void initData() {
        viewModel.setRegisterType(type);
        viewModel.third_type.set(third_type);
        viewModel.open_id.set(open_id);
        viewModel.name.set(name);
        viewModel.photo.set(photo);
        viewModel.user_id.set(user_id);
    }
}
