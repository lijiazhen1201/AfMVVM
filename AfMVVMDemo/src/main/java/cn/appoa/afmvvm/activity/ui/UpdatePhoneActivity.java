package cn.appoa.afmvvm.activity.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.lifecycle.Observer;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityUpdatePhoneBinding;
import cn.appoa.afmvvm.router.RouterActivityPath;
import cn.appoa.afmvvm.viewmodel.UpdatePhoneViewModel;
import cn.appoa.afui.BR;

/**
 * 修改手机号
 */
@Route(path = RouterActivityPath.ACTIVITY_UPDATE_PHONE)
public class UpdatePhoneActivity extends BaseActivity<ActivityUpdatePhoneBinding, UpdatePhoneViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_update_phone;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.setDefaultTitleBar(R.drawable.back_black, "修改手机号", true);
        viewModel.getModel().phoneHint.set("请输入新手机号");
        viewModel.updatePhoneEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String phone) {
                Intent data = new Intent();
                data.putExtra("phone", phone);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }
}
