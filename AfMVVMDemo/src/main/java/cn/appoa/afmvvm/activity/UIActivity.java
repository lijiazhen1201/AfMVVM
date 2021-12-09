package cn.appoa.afmvvm.activity;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afmvvm.activity.ui.AboutUsActivity;
import cn.appoa.afmvvm.activity.ui.IndexActivity;
import cn.appoa.afmvvm.activity.ui.LoginActivity;
import cn.appoa.afmvvm.activity.ui.UpdatePayPwdActivity;
import cn.appoa.afmvvm.activity.ui.UpdatePhoneActivity;
import cn.appoa.afmvvm.activity.ui.UpdatePwdActivity;
import cn.appoa.afmvvm.bean.MainMenu;
import cn.appoa.afmvvm.router.RouterActivityPath;

/**
 * MVVM方式显示UI
 */
@Route(path = RouterActivityPath.ACTIVITY_UI)
public class UIActivity extends MainMenuActivity {

    @Override
    protected String initTitle() {
        return "MVVM方式显示UI";
    }

    @Override
    protected List<MainMenu> initMainMenuList() {
        List<MainMenu> dataList = new ArrayList<>();
        dataList.add(new MainMenu("主页", IndexActivity.class));
        dataList.add(new MainMenu("登录", LoginActivity.class));
        dataList.add(new MainMenu("设置支付密码", UpdatePayPwdActivity.class));
        dataList.add(new MainMenu("修改手机号", UpdatePhoneActivity.class));
        dataList.add(new MainMenu("修改登录密码", UpdatePwdActivity.class));
        dataList.add(new MainMenu("关于我们", AboutUsActivity.class));
        return dataList;
    }
}
