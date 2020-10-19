package cn.appoa.afmvvm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.lifecycle.Observer;
import cn.appoa.afbase.constant.AfConstant;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityUpdatePayPwdBinding;
import cn.appoa.afmvvm.router.RouterActivityPath;
import cn.appoa.afmvvm.viewmodel.UpdatePayPwdViewModel;
import cn.appoa.afutils.file.SpUtils;

/**
 * 修改支付密码
 */
@Route(path = RouterActivityPath.ACTIVITY_UPDATE_PAY_PWD)
public class UpdatePayPwdActivity extends BaseActivity<ActivityUpdatePayPwdBinding, UpdatePayPwdViewModel> {

    /**
     * 1设置支付密码，2修改支付密码，3找回支付密码
     */
    private int type;

    @Override
    public void initIntent(Intent intent) {
        type = intent.getIntExtra("type", 1);
    }

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_update_pay_pwd;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.updatePayPwdEvent.observe(this, new Observer<String>() {
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
        viewModel.setUpdatePayPwdType(type);
        if (type == 3) {
            viewModel.getModel().phone.set((String) SpUtils.getData(mActivity,
                    AfConstant.USER_PHONE, ""));
        }
    }
}
