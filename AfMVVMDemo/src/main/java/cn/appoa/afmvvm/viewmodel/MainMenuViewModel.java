package cn.appoa.afmvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import cn.appoa.afbase.binding.command.BindingCommand;
import cn.appoa.afbase.binding.command.BindingConsumer;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afmvvm.bean.MainMenu;
import cn.appoa.afmvvm.model.MainMenuModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;

public class MainMenuViewModel extends TitleBarViewModel<MainMenuModel> {

    public MainMenuViewModel(@NonNull Application application) {
        super(application);
    }

    public MainMenuViewModel(@NonNull Application application, MainMenuModel model) {
        super(application, model);
    }

    public SingleLiveEvent<Void> menuOnClickEvent = new SingleLiveEvent<>();

    @Override
    protected void menuOnClick() {
        super.menuOnClick();
        menuOnClickEvent.call();
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
