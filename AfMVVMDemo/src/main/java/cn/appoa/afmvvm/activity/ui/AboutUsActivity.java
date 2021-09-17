package cn.appoa.afmvvm.activity.ui;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.lifecycle.Observer;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.bean.AboutUs;
import cn.appoa.afmvvm.databinding.ActivityAboutUsBinding;
import cn.appoa.afmvvm.router.RouterActivityPath;
import cn.appoa.afmvvm.viewmodel.AboutUsViewModel;
import cn.appoa.afui.fragment.DefaultHintDialogFragment;
import cn.appoa.afui.listener.ConfirmHintDialogListener;
import cn.appoa.afutils.res.PhoneUtils;

/**
 * 关于我们
 */
@Route(path = RouterActivityPath.ACTIVITY_ABOUT_US)
public class AboutUsActivity extends BaseActivity<ActivityAboutUsBinding, AboutUsViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_about_us;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.getAboutUs();
    }

    @Override
    public void initViewObservable() {
        viewModel.setDefaultTitleBar(R.drawable.back_black, "关于我们", true);
        viewModel.getAboutUsEvent.observe(this, new Observer<AboutUs>() {
            @Override
            public void onChanged(AboutUs aboutUs) {
                if (aboutUs != null) {
                    binding.setAboutUs(aboutUs);
                }
            }
        });
        viewModel.callMobileEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(final String mobile) {
                if (!TextUtils.isEmpty(mobile)) {
                    //PhoneUtils.dialPhone(mActivity, mobile);
                    DefaultHintDialogFragment.getInstance2("取消", "拨打", "服务电话", mobile)
                            .setOnCallbackListener(new ConfirmHintDialogListener() {
                                @Override
                                public void clickConfirmButton() {
                                    PhoneUtils.callPhone(mActivity, mobile);
                                }
                            })
                            .show(mFragmentManager);
                }
            }
        });
    }

}
