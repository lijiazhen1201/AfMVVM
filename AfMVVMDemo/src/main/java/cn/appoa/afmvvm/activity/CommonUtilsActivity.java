package cn.appoa.afmvvm.activity;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afmvvm.activity.utils.AESUtilsActivity;
import cn.appoa.afmvvm.activity.utils.AtyUtilsActivity;
import cn.appoa.afmvvm.activity.utils.DateUtilsActivity;
import cn.appoa.afmvvm.activity.utils.JsonUtilsActivity;
import cn.appoa.afmvvm.activity.utils.MapUtilsActivity;
import cn.appoa.afmvvm.activity.utils.Md5UtilsActivity;
import cn.appoa.afmvvm.activity.utils.SpannableStringUtilsActivity;
import cn.appoa.afmvvm.activity.utils.ThreadUtilsActivity;
import cn.appoa.afmvvm.bean.MainMenu;
import cn.appoa.afmvvm.router.RouterActivityPath;

/**
 * 常用工具类
 */
@Route(path = RouterActivityPath.ACTIVITY_COMMON_UTILS)
public class CommonUtilsActivity extends MainMenuActivity{

    @Override
    protected String initTitle() {
        return "常用工具类";
    }

    @Override
    protected List<MainMenu> initMainMenuList() {
        List<MainMenu> dataList = new ArrayList<>();
        dataList.add(new MainMenu("AESUtils\n【AES加密工具类】", AESUtilsActivity.class));
        dataList.add(new MainMenu("ThreadUtils\n【主线程和子线程切换】", ThreadUtilsActivity.class));
        dataList.add(new MainMenu("AtyUtils\n【Activity常用方法封装】", AtyUtilsActivity.class));
        dataList.add(new MainMenu("DateUtils\n【时间格式化】", DateUtilsActivity.class));
        dataList.add(new MainMenu("JsonUtils\n【Json解析工具类】", JsonUtilsActivity.class));
        dataList.add(new MainMenu("MapUtils\n【地图导航工具类】", MapUtilsActivity.class));
        dataList.add(new MainMenu("Md5Utils\n【Md5加密工具类】", Md5UtilsActivity.class));
        dataList.add(new MainMenu("SpannableStringUtils\n【SpannableString】", SpannableStringUtilsActivity.class));
        return dataList;
    }
}
