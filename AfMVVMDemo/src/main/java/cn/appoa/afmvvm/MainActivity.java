package cn.appoa.afmvvm;

import android.view.KeyEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afmvvm.activity.CommonUtilsActivity;
import cn.appoa.afmvvm.activity.CustomWidgetActivity;
import cn.appoa.afmvvm.activity.GithubProjectActivity;
import cn.appoa.afmvvm.activity.MainMenuActivity;
import cn.appoa.afmvvm.activity.RefreshBeanActivity;
import cn.appoa.afmvvm.activity.UIActivity;
import cn.appoa.afmvvm.activity.UploadMediaActivity;
import cn.appoa.afmvvm.activity.WebViewActivity;
import cn.appoa.afmvvm.activity.ZmQrCodeActivity;
import cn.appoa.afmvvm.activity.ZmShakeActivity;
import cn.appoa.afmvvm.bean.MainMenu;
import cn.appoa.afmvvm.router.RouterActivityPath;

@Route(path = RouterActivityPath.ACTIVITY_MAIN)
public class MainActivity extends MainMenuActivity {

    @Override
    protected String initTitle() {
        return "AfMVVM";
    }

    @Override
    protected List<MainMenu> initMainMenuList() {
        List<MainMenu> dataList = new ArrayList<>();
        dataList.add(new MainMenu("MVVM方式显示UI", UIActivity.class));
        dataList.add(new MainMenu("常用工具类", CommonUtilsActivity.class));
        dataList.add(new MainMenu("Github优秀开源库", GithubProjectActivity.class));
        dataList.add(new MainMenu("自定义控件", CustomWidgetActivity.class));
        dataList.add(new MainMenu("下拉刷新", RefreshBeanActivity.class));
        dataList.add(new MainMenu("多媒体上传", UploadMediaActivity.class));
        dataList.add(new MainMenu("WebView的使用", WebViewActivity.class));
        dataList.add(new MainMenu("微信扫一扫", ZmQrCodeActivity.class));
        dataList.add(new MainMenu("微信摇一摇", ZmShakeActivity.class));
        return dataList;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.setLeftImageVisible(View.GONE);
    }

    @Override
    public boolean enableSliding() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            doubleClickExit(2000, "再按一次返回键退出程序");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
