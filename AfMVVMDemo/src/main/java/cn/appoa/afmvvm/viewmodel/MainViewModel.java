package cn.appoa.afmvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import cn.appoa.afbase.binding.command.BindingCommand;
import cn.appoa.afbase.binding.command.BindingConsumer;
import cn.appoa.afmvvm.bean.MainMenu;
import cn.appoa.afmvvm.model.MainModel;
import cn.appoa.afmvvm.router.RouterActivityPath;
import cn.appoa.afui.titlebar.TitleBarViewModel;

public class MainViewModel extends TitleBarViewModel<MainModel> {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MainViewModel(@NonNull Application application, MainModel model) {
        super(application, model);
    }

    @Override
    protected void initTitleBar() {
        setDefaultTitleBar(0, "首页");
        //setRightText("关于我们");
    }

    @Override
    protected void menuOnClick() {
        //startActivity(AboutUsActivity.class);
        startRouterActivity(RouterActivityPath.ACTIVITY_ABOUT_US);
    }

    public BindingCommand<MainMenu> menuItemClick = new BindingCommand<MainMenu>
            (new BindingConsumer<MainMenu>() {

                @Override
                public void call(MainMenu mainMenu) {
                    if (mainMenu != null && mainMenu.getClazz() != null) {
                        startActivity(mainMenu.getClazz());
                    }
                }
            });
}
