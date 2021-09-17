package cn.appoa.afmvvm.activity;

import android.content.Intent;

import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseContainerActivity;
import cn.appoa.afmvvm.fragment.MyShakeFragment;

/**
 * 微信摇一摇
 */
public class ZmShakeActivity extends BaseContainerActivity {

    @Override
    public void initIntent(Intent intent) {
        //super.initIntent(intent);
    }

    @Override
    public void initData() {
        //super.initData();
        fragment = new MyShakeFragment();
        mFragmentManager.beginTransaction().replace(R.id.fl_fragment, fragment).commit();
    }
}
