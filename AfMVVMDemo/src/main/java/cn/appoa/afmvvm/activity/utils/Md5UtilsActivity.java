package cn.appoa.afmvvm.activity.utils;

import android.os.Bundle;
import android.view.View;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityMd5UtilsBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afutils.encrypt.Md5Utils;
import cn.appoa.afutils.res.PhoneUtils;
import cn.appoa.afutils.res.ViewUtils;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * Md5加密工具类
 */
public class Md5UtilsActivity extends BaseActivity<ActivityMd5UtilsBinding, TitleBarViewModel>
        implements View.OnClickListener {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_md5_utils;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "Md5Utils", true);
    }

    @Override
    public void initData() {
        binding.btnEncrypt32.setOnClickListener(this);
        binding.btnEncrypt16.setOnClickListener(this);
        binding.btnEncryptPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (ViewUtils.isTextEmpty(binding.etLoginPhone)) {
            ToastUtils.showShort(mActivity, binding.etLoginPhone.getHint(), false);
            return;
        }
        String phone = ViewUtils.getText(binding.etLoginPhone);
        if (!PhoneUtils.isMobile(phone)) {
            ToastUtils.showShort(mActivity, "请输入正确的手机号", false);
            return;
        }
        String result = "";
        switch (v.getId()) {
            case R.id.btn_encrypt_32:
                result = Md5Utils.GetMD5Code32(phone);
                break;
            case R.id.btn_encrypt_16:
                result = Md5Utils.GetMD5Code16(phone);
                break;
            case R.id.btn_encrypt_phone:
                result = Md5Utils.getSmsToken(phone);
                break;
            default:
                break;
        }
        binding.tvResult.setText(result);
    }
}
