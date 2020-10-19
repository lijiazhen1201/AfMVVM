package cn.appoa.afmvvm.base;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;

import androidx.databinding.ViewDataBinding;
import cn.appoa.afbase.activity.AfActivity;
import cn.appoa.afbase.mvvm.BaseViewModel;

/**
 * Activity基类
 *
 * @param <V>
 * @param <VM>
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel>
        extends AfActivity<V, VM> {

    @Override
    public boolean initDarkStatusBar() {
        //深色状态栏
        return true;
    }

    @Override
    public boolean enableSliding() {
        //开启侧滑返回
        return true;
    }

    @Override
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        //super.startContainerActivity(canonicalName, bundle);
        startActivity(BaseContainerActivity.getContainerIntent(mActivity, canonicalName, bundle));
    }

    @Override
    public void startContainerActivityForResult(String canonicalName, Bundle bundle, int requestCode) {
        //super.startContainerActivityForResult(canonicalName, bundle, requestCode);
        startActivityForResult(BaseContainerActivity.getContainerIntent(mActivity, canonicalName, bundle), requestCode);
    }

    @Override
    public void startRouterActivity(String activityPath, Bundle bundle) {
        //super.startRouterActivity(activityPath, bundle);
        if (TextUtils.isEmpty(activityPath)) {
            return;
        }
        if (bundle == null) {
            ARouter.getInstance().build(activityPath).navigation(mActivity);
        } else {
            ARouter.getInstance().build(activityPath).with(bundle).navigation(mActivity);
        }
    }

    @Override
    public void startRouterActivityForResult(String activityPath, Bundle bundle, int requestCode) {
        //super.startRouterActivityForResult(activityPath, bundle, requestCode);
        if (TextUtils.isEmpty(activityPath)) {
            return;
        }
        if (bundle == null) {
            ARouter.getInstance().build(activityPath).navigation(mActivity, requestCode);
        } else {
            ARouter.getInstance().build(activityPath).with(bundle).navigation(mActivity, requestCode);
        }
    }

    @Override
    public Object getRouterFragment(String fragmentPath) {
        if (TextUtils.isEmpty(fragmentPath)) {
            return null;
        }
        return ARouter.getInstance().build(fragmentPath).navigation();
    }
}
