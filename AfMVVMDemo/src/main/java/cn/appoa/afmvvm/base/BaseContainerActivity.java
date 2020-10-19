package cn.appoa.afmvvm.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cn.appoa.afbase.mvvm.BaseViewModel;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.databinding.ActivityBaseContainerBinding;

/**
 * 盛装Fragment的一个容器(代理)Activity
 * 普通界面只需要编写Fragment,使用此Activity盛装,这样就不需要每个界面都在AndroidManifest中注册一遍
 */
public class BaseContainerActivity extends BaseActivity<ActivityBaseContainerBinding, BaseViewModel> {

    /**
     * 获取跳转容器页面的Intent
     *
     * @param context
     * @param canonicalName
     * @param bundle
     * @return
     */
    public static Intent getContainerIntent(Context context, String canonicalName, Bundle bundle) {
        Intent intent = new Intent(context, BaseContainerActivity.class);
        intent.putExtra(BaseContainerActivity.FRAGMENT_NAME, canonicalName);
        if (bundle != null) {
            intent.putExtra(BaseContainerActivity.FRAGMENT_BUNDLE, bundle);
        }
        return intent;
    }

    public static final String FRAGMENT_TAG = "fragment_tag";
    public static final String FRAGMENT_NAME = "fragment_name";
    public static final String FRAGMENT_BUNDLE = "fragment_bundle";

    private String fragmentName;
    private Bundle fragmentBundle;

    @Override
    public void initIntent(Intent intent) {
        fragmentName = intent.getStringExtra(BaseContainerActivity.FRAGMENT_NAME);
        if (TextUtils.isEmpty(fragmentName)) {
            finish();
        }
        fragmentBundle = intent.getBundleExtra(BaseContainerActivity.FRAGMENT_BUNDLE);
    }

    private Fragment fragment = null;

    @Override
    public int initContent(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            fragment = mFragmentManager.getFragment(savedInstanceState, BaseContainerActivity.FRAGMENT_TAG);
        }
        return R.layout.activity_base_container;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (fragment != null) {
            mFragmentManager.putFragment(outState, BaseContainerActivity.FRAGMENT_TAG, fragment);
        }
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void initData() {
        if (fragment == null) {
            try {
                Class<?> fragmentClass = Class.forName(fragmentName);
                fragment = (Fragment) fragmentClass.newInstance();
                if (fragmentBundle != null) {
                    fragment.setArguments(fragmentBundle);
                }
            } catch (Exception e) {
                e.printStackTrace();
                finish();
            }
        }
        if (fragment != null) {
            mFragmentManager.beginTransaction().replace(R.id.fl_fragment, fragment).commitAllowingStateLoss();
        }
    }

}
