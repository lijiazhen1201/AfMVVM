package cn.appoa.afmvvm.activity.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.lifecycle.Observer;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityUpdatePwdBinding;
import cn.appoa.afmvvm.router.RouterActivityPath;
import cn.appoa.afmvvm.viewmodel.UpdatePwdViewModel;

/**
 * 修改登录密码
 */
@Route(path = RouterActivityPath.ACTIVITY_UPDATE_PWD)
public class UpdatePwdActivity extends BaseActivity<ActivityUpdatePwdBinding, UpdatePwdViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_update_pwd;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.setDefaultTitleBar(R.drawable.back_black, "修改登录密码", true);
        viewModel.updatePwdEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String pwd) {
                Intent data = new Intent();
                data.putExtra("pwd", pwd);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }
}
