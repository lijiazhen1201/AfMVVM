package cn.appoa.afmvvm;

import android.os.Bundle;
import android.view.KeyEvent;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afmvvm.activity.AboutUsActivity;
import cn.appoa.afmvvm.activity.LoginActivity;
import cn.appoa.afmvvm.activity.UpdatePayPwdActivity;
import cn.appoa.afmvvm.activity.UpdatePhoneActivity;
import cn.appoa.afmvvm.activity.UpdatePwdActivity;
import cn.appoa.afmvvm.adapter.MainMenuAdapter;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.bean.MainMenu;
import cn.appoa.afmvvm.databinding.ActivityMainBinding;
import cn.appoa.afmvvm.router.RouterActivityPath;
import cn.appoa.afmvvm.viewmodel.MainViewModel;

@Route(path = RouterActivityPath.ACTIVITY_MAIN)
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void initData() {
        List<MainMenu> dataList = new ArrayList<>();
        dataList.add(new MainMenu("登录", LoginActivity.class));
        dataList.add(new MainMenu("设置支付密码", UpdatePayPwdActivity.class));
        dataList.add(new MainMenu("修改手机号", UpdatePhoneActivity.class));
        dataList.add(new MainMenu("修改登录密码", UpdatePwdActivity.class));
        dataList.add(new MainMenu("关于我们", AboutUsActivity.class));
        MainMenuAdapter adapter = new MainMenuAdapter(dataList, viewModelId, viewModel);
        binding.setAdapter(adapter);
    }

    @Override
    public boolean enableSliding() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && //
                event.getAction() == KeyEvent.ACTION_DOWN) {
            doubleClickExit(2000, "再按一次返回键退出程序");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
