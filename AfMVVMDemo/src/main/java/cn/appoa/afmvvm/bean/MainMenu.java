package cn.appoa.afmvvm.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import cn.appoa.afmvvm.BR;

/**
 * 主页菜单
 */
public class MainMenu extends BaseObservable {

    private String name;
    private Class clazz;

    public MainMenu() {
    }

    public MainMenu(String name, Class clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
        notifyPropertyChanged(BR.clazz);
    }
}
